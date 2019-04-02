package sjsu.cmpe277.torchapplication;

import android.app.Service;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView enterLumValue;
    TextView enterTorchValue;
    SensorManager sensorManager;
    Sensor sensor;
    CameraManager cameraManager;

    private static String cameraID = null;
    private static boolean isFlashAvailable = false;
    private static boolean flashLightStatus = false;
    private static float trigger = 200;
    private static String flashStatus = "OFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterLumValue = (TextView) findViewById(R.id.lumValue);
        enterTorchValue = (TextView) findViewById(R.id.torchLightValue);
        isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            System.out.println("sudha: Flash not available");
        }

        // for lumen
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public  void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lumValue = event.values[0];
            //System.out.println("sudha: cameraID = " + cameraID + " isFlashAvailable = " + isFlashAvailable + " lumValue = " + lumValue);

            if (isFlashAvailable == true) {
                AsyncTaskRunner runner = new AsyncTaskRunner();

                if (lumValue < trigger) {
                    if (flashLightStatus == false) {
                        System.out.println("sudha: lumValue < " + trigger + " : TURNING ON FLASH");
                        runner.execute(String.valueOf(lumValue), String.valueOf(true));
                        flashLightStatus = true;
                        flashStatus = "ON";
                    }
                } else {
                    if (flashLightStatus == true) {
                        System.out.println("sudha: lumValue > " + trigger + " : TURNING OFF FLASH");
                        runner.execute(String.valueOf(lumValue), String.valueOf(false));
                        flashLightStatus = false;
                        flashStatus = "OFF";
                    }
                }

            }
            enterLumValue.setText("" + lumValue);
            enterTorchValue.setText("" + flashStatus);

        }

    }

    public void setFlashLight(boolean status) {
        try {
            cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            try {
                cameraID = cameraManager.getCameraIdList()[0];
            } catch (CameraAccessException c) {
                c.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cameraManager.setTorchMode(cameraID, status);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            float lv = Float.parseFloat(params[0]);
            boolean b = Boolean.parseBoolean(params[1]);
            System.out.println("sudha : Async task print lv = " + lv + " Boolean = " + b);
            resp = "Trigger for lv " + lv + " intensity";
            setFlashLight(b);
            return resp;
        }

    }

}
