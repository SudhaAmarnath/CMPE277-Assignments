package sjsu.cmpe277.asynctaskthsensordriver;

import android.os.Bundle;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import java.util.Random;


public class MainActivity extends Activity {

    public TextView varEnterTemperature;
    public TextView varEnterHumidity;
    public TextView varEnterActivity;
    public TextView varScrollViewOutput;
    public EditText varEnterSensors;
    public Button varGenerate;
    public Button varCancel;

    public int intEnterTemperature;
    public int intEnterHumidity;
    public int intEnterActivity;
    public int intEnterSensors;
    public String strEnterSensors = null;

    Random random = new Random();
    THWrapperClass th = new THWrapperClass();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        varEnterSensors = (EditText)findViewById(R.id.enterSensors);
        varGenerate = (Button)findViewById(R.id.buttonGenerate);
        varCancel = (Button)findViewById(R.id.buttonCancel);

        varGenerate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    strEnterSensors = varEnterSensors.getText().toString();
                    System.out.println(strEnterSensors);
                    intEnterSensors = Integer.parseInt(strEnterSensors);
                    if(strEnterSensors == null || (intEnterSensors < 1 || intEnterSensors > 20)) {
                        Toast.makeText(MainActivity.this, "Enter number of Sensors 1-20", Toast.LENGTH_LONG).show();
                    } else {
                        generate();
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

        varCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                varEnterTemperature = (TextView) findViewById(R.id.enterTemperature);
                varEnterHumidity = (TextView) findViewById(R.id.enterHumidity);
                varEnterActivity = (TextView) findViewById(R.id.enterActivity);
                varEnterSensors = (EditText) findViewById(R.id.enterSensors);
                varScrollViewOutput = (TextView) findViewById(R.id.scrollViewOutput);
                varEnterTemperature.setText("");
                varEnterHumidity.setText("");
                varEnterActivity.setText("");
                varEnterSensors.setText("");
                varScrollViewOutput.setText("");

            }
        });


    }


    private class Generate_Random_TH_Sensor_Parameters extends AsyncTask<Integer, Void, Void> {


        int tempMin = 25;
        int tempMax = 100;
        int humidMin = 40;
        int humidMax = 100;
        int activityMin = 1;
        int activityMax = 500;


        @Override
        protected Void doInBackground(Integer... IntNumOfSensors) {

            int noOfSensors = (int) IntNumOfSensors[0];

            th.sensor = noOfSensors;
            th.temp = new String[noOfSensors];
            th.humidity = new String[noOfSensors];
            th.activity = new String[noOfSensors];

            for (int i = 0; i < noOfSensors; i++) {


                intEnterTemperature = random.nextInt(tempMax + 1 - tempMin) + tempMin;
                intEnterHumidity =  random.nextInt(humidMax + 1 - humidMin) + humidMin;
                intEnterActivity = random.nextInt(activityMax + 1 - activityMin) + activityMin;

                th.temp[i] = String.valueOf(intEnterTemperature) + "F";
                th.humidity[i] = String.valueOf(intEnterHumidity) + "%";
                th.activity[i] = String.valueOf(intEnterActivity);

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress();

                if (isCancelled()) {
                    break;
                }

            }

            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            varEnterTemperature = (TextView) findViewById(R.id.enterTemperature);
            varEnterHumidity = (TextView) findViewById(R.id.enterHumidity);
            varEnterActivity = (TextView) findViewById(R.id.enterActivity);
            varScrollViewOutput = (TextView) findViewById(R.id.scrollViewOutput);

            for (int i = 0; i < th.temp.length; i++) {
                varEnterTemperature.setText(th.temp[i]);
                varEnterHumidity.setText(th.humidity[i]);
                varEnterActivity.setText(th.activity[i]);
            }

        }

        @Override
        protected void onPostExecute(Void v) {

            String output = "";
            super.onPostExecute(v);

            for (int i = 0; i < th.sensor; i++) {
                output = output + ("Output " + (i+1) + ":\nTemperature : " +
                        th.temp[i] + "\nHumidity : " +
                        th.humidity[i] + "\nActivity :" +
                        th.activity[i] + "\n" +
                        "---------------------------------\n");
            }
            varScrollViewOutput.setText(output);

        }

    }

    public void generate() {
        Generate_Random_TH_Sensor_Parameters genparams = new Generate_Random_TH_Sensor_Parameters();
        genparams.execute(new Integer[]{intEnterSensors});
    }

    public void Close(View view)
    {
        MainActivity.this.finish();

    }


}
