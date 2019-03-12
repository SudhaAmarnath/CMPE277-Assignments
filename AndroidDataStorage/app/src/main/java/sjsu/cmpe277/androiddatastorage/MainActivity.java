package sjsu.cmpe277.androiddatastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextView varScrollTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String line;
        try {

            InputStream inputStream =openFileInput(PreferenceActivity.PREFERENCE_FILENAME);

            if(inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line+"\n");
                }
                inputStreamReader.close();
                varScrollTextView = (TextView)findViewById(R.id.scrollTextView);
                varScrollTextView.setText(stringBuilder.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void PreferenceButton (View view) {
        Intent intent = new Intent(this,PreferenceActivity.class);
        startActivity(intent);
    }

    public void SqliteButton (View view) {
        Intent intent = new Intent(this,SQLiteActivity.class);
        startActivity(intent);
    }

    public void MainActivityClose(View view) {
        MainActivity.this.finish();
    }

}
