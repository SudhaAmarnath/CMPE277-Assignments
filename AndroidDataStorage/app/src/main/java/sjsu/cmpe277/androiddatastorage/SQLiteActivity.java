package sjsu.cmpe277.androiddatastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteActivity extends AppCompatActivity {

    public int slNo=0;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM/dd/YYYY-HH:MM:ss a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        slNo =sp.getInt("SQL_COUNTER", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        slNo =sp.getInt("SQL_COUNTER", 0);

    }


    public void SQLiteSave (View view) {

        DatabaseUtility databaseUtility = new DatabaseUtility(getBaseContext());


        EditText varEnterBlogmessage = (EditText)findViewById(R.id.enterBlogmessage);
        String strBlogmessage = varEnterBlogmessage.getText().toString();


        databaseUtility.open();
        long returnValue = databaseUtility.insert(strBlogmessage);
        databaseUtility.close();

        if(returnValue != -1) {

            Context context = getApplicationContext();
            CharSequence text="Message saved successfully";
            int duration= Toast.LENGTH_LONG;
            Toast.makeText(context, text, duration).show();

            try
            {
                slNo++;
                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("SQL_COUNTER", slNo);
                editor.apply();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(PreferenceActivity.PREFERENCE_FILENAME,MODE_APPEND));
                String outmessage = "\nSQLite Saved     (" + slNo + ") " + simpleDateFormat.format(new Date());
                outputStreamWriter.write(outmessage);
                outputStreamWriter.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    public void SQLiteCancel (View view) {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        SQLiteActivity.this.finish();

    }


}
