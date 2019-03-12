package sjsu.cmpe277.androiddatastorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreferenceActivity extends AppCompatActivity {

    EditText varEnterBookname;
    EditText varEnterBookauthor;
    EditText varEnterDescription;

    public final static String PREFERENCE_FILENAME="preferenceFile.txt";
    public int slNo=0;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM/dd/YYYY-HH:MM:ss a");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        slNo =sp.getInt("PREF_SLNO", 0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        slNo =sp.getInt("PREF_SLNO", 0);

    }

    public void PreferenceSave(View view) {

        varEnterBookname = (EditText)findViewById(R.id.enterBookname);
        varEnterBookauthor = (EditText)findViewById(R.id.enterBookauthor);
        varEnterDescription = (EditText)findViewById(R.id.enterDescription);

        String strBookname = varEnterBookname.getText().toString();
        String strBookauthor = varEnterBookauthor.getText().toString();
        String strDescription = varEnterDescription.getText().toString();

        if(strBookname == "" || strBookauthor == "" || strDescription == "") {
            System.out.println("Bookname, Bookauthor, Description should be entered");
        } else {
            try {
                slNo++;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("PREF_SLNO",slNo);
                editor.putString("BookName",strBookname);
                editor.putString("BookAuthor",strBookauthor);
                editor.putString("Description",strDescription);
                editor.apply();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(PREFERENCE_FILENAME,MODE_APPEND));
                String outmessage = "\nPreference Saved (" + slNo + ") " + simpleDateFormat.format(new Date());
                outputStreamWriter.write(outmessage);
                outputStreamWriter.close();
            } catch (FileNotFoundException f) {
                f.printStackTrace();
            } catch (IOException i) {
                i.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        }

    }

    public void PreferenceCancel(View view) {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        PreferenceActivity.this.finish();

    }

}
