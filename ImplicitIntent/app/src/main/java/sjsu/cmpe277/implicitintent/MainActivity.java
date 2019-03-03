package sjsu.cmpe277.implicitintent;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code for WebLink
        final Button urlButton = findViewById(R.id.urlButton);
        final EditText urlText =  findViewById(R.id.urlText);

        urlButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url =  urlText.getText().toString();
                if (url.length() > 0) {
                    try {
                        Log.v(TAG,"Url typed is:" + url);
                        if(! (url.startsWith("http://") || url.startsWith("https://"))) {
                            url = "http://" + url;
                        }
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Please install a Web Browser", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a valid URL", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Code for PhoneCall
        final Button ringButton = findViewById(R.id.ringButton);
        final EditText ringText =  findViewById(R.id.ringText);

        ringButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNumber =  ringText.getText().toString();
                if (phoneNumber.length() > 0 &&
                        ((!phoneNumber.startsWith("+1") && phoneNumber.length() == 10) ||
                                (phoneNumber.startsWith("+1") && phoneNumber.length() == 12) )) {
                    Log.v(TAG,"PhoneNumber typed is:" + phoneNumber);
                    if (!phoneNumber.startsWith("+1") )
                        phoneNumber = "+1" + phoneNumber;
                    phoneNumber="tel:"+phoneNumber;
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Please install a Phone App",  Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a valid Phone Number", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void finishApp(View v) {
        MainActivity.this.finish();
    }
}
