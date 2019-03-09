package sjsu.cmpe277.broadcastsender;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String convertTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> categories = new ArrayList<String>();
        categories.add("Euro");
        categories.add("Indian Rupee");
        categories.add("British Pound");

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        convertTo = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        if (null != intent) {
            String convertAmount = intent.getStringExtra("convertedAmount");
            String receiverToSenderAmount = intent.getStringExtra("receiverToSenderAmount");
            String receiverToSenderCurrency = intent.getStringExtra("receiverToSenderCurrency");


            if (null != convertAmount) {
                TextView textView = findViewById(R.id.convertedText);
                textView.setText("Dollar amount $" + receiverToSenderAmount + " converted to " + convertAmount + " " + receiverToSenderCurrency);
            }
        }
    }

    public void convertCurrency(View view) {
        String actionString = "sjsu.cmpe277.broadcastsender.sender";
        Intent broadcastIntent = new Intent(actionString);

        EditText editText = findViewById(R.id.editText2);
        String amount = editText.getText().toString();

        broadcastIntent.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        broadcastIntent.setComponent(new ComponentName("sjsu.cmpe277.broadcastreceiver", "sjsu.cmpe277.broadcastreceiver.CurrencyConverterReceiver"));
        broadcastIntent.putExtra("amount", amount);
        broadcastIntent.putExtra("currencyConvertTo", convertTo);

        sendBroadcast(broadcastIntent);
    }

    public void closeApp(View view) {
        MainActivity.this.finish();
    }
}
