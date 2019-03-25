package sjsu.cmpe277.servicesapp;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    EditText editTextPDF1, editTextPDF2, editTextPDF3, editTextPDF4, editTextPDF5;
    Button buttonDownload;
    String url1, url2, url3, url4, url5;
    public static String filename1,filename2,filename3,filename4,filename5,currFileName;

    /* FLAGS
    0   => not started
    1   => downloading
    -1  => failed
    2   => downloaded
    */

    public static int file1Flag = 0;
    public static int file2Flag = 0;
    public static int file3Flag = 0;
    public static int file4Flag = 0;
    public static int file5Flag = 0;
    static int currFileFlag;

    static String downloadPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPDF1 = (EditText) findViewById(R.id.enterpdf1);
        editTextPDF2 = (EditText) findViewById(R.id.enterpdf2);
        editTextPDF3 = (EditText) findViewById(R.id.enterpdf3);
        editTextPDF4 = (EditText) findViewById(R.id.enterpdf4);
        editTextPDF5 = (EditText) findViewById(R.id.enterpdf5);

        buttonDownload = (Button) findViewById(R.id.StartDownload);

        downloadPath = Environment.DIRECTORY_DOWNLOADS;
        registerReceiver(receiver, new IntentFilter(
                PdfDownloadService.NOTIFICATION));


        buttonDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                url1 = editTextPDF1.getText().toString();
                url2 = editTextPDF2.getText().toString();
                url3 = editTextPDF3.getText().toString();
                url4 = editTextPDF4.getText().toString();
                url5 = editTextPDF5.getText().toString();


                filename1 = url1.substring(url1.lastIndexOf('/')+1);
                filename2 = url2.substring(url2.lastIndexOf('/')+1);
                filename3 = url3.substring(url3.lastIndexOf('/')+1);
                filename4 = url4.substring(url4.lastIndexOf('/')+1);
                filename5 = url5.substring(url5.lastIndexOf('/')+1);

                if (TextUtils.isEmpty(url1) && TextUtils.isEmpty(url2) && TextUtils.isEmpty(url3) && TextUtils.isEmpty(url4) && TextUtils.isEmpty(url5)) {
                    Toast.makeText(MainActivity.this, "Enter at least in one fields", Toast.LENGTH_LONG).show();
                } else
                    startDownload(view);
            }
        });


    }


    public void startDownload(View view) {

        Toast.makeText(this, "Starting all downloads !", Toast.LENGTH_SHORT).show();

        startDownloadService(url1,filename1);
        file1Flag = 1;

        startDownloadService(url2,filename2);
        file2Flag = 1;

        startDownloadService(url3,filename3);
        file3Flag = 1;

        startDownloadService(url4,filename4);
        file4Flag = 1;

        startDownloadService(url5,filename5);
        file5Flag = 1;
    }


    private void startDownloadService(String link, String filename) {
        Intent intent = new Intent(getBaseContext(), PdfDownloadService.class);
        intent.putExtra("urlpath", link);
        intent.putExtra("filename",filename);
        startService(intent);
    }

    private void refreshTextView (int x) {
        System.out.print("In refreshview");
        if (x > 5 || x < 1) {
            System.out.println("refreshTextView wrong parameter, returning");
            return;
        }

        if (x == 1) {
            currFileFlag = file1Flag;
            currFileName = filename1;
        } else if (x == 2) {
            currFileFlag = file2Flag;
            currFileName = filename2;
        } else if (x == 3) {
            currFileFlag = file3Flag;
            currFileName = filename3;
        } else if (x == 4) {
            currFileFlag = file4Flag;
            currFileName = filename4;
        } else {
            currFileFlag = file5Flag;
            currFileName = filename5;
        }

        switch (currFileFlag) {
            case 0:
                Toast.makeText(this, "Starting the download !", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, currFileName+" is downloading...", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, currFileName + " downloaded...", Toast.LENGTH_SHORT).show();
                break;
            case -1:
                Toast.makeText(this, currFileName + " download failed!", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void refreshAllTextViews () {
        refreshTextView(1);
        refreshTextView(2);
        refreshTextView(3);
        refreshTextView(4);
        refreshTextView(5);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int x;
            if (bundle != null) {
                String filename = bundle.getString("file");
                System.out.println("BroadcastReceiver -> onReceive -> Filename -> " + filename);
                if (filename.equals(filename1)) x=1;
                else if (filename.equals(filename2)) x=2;
                else if (filename.equals(filename3)) x=3;
                else if (filename.equals(filename4)) x=4;
                else x=5;
                refreshTextView(x);
            }

        }


    };
}


