package sjsu.cmpe277.broadcastreceiver;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private String convertedValue;
    private String receiverToSenderAmount;
    private String receiverToSenderCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (null != intent) {
            renderUI(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        if (null != intent) {
            renderUI(intent);
        }
    }

    public void renderUI(Intent intent) {

        String convertTo = intent.getStringExtra("currencyConvertTo");
        String amountStr = intent.getStringExtra("amount");

        TextView textView = findViewById(R.id.textView);
        textView.setText("Dollar Amount : $" + amountStr);

        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText("Convert To : " + convertTo);

        convert(convertTo, amountStr);
        System.out.println("render UI called ... convertTo:amountStr " + convertTo + ":"+ amountStr);
    }

    public Double getCurrentUSDExchangeRate(String currency) {

        String currencyApiResult = null;
        String currentDate = null;
        Double usdExchangeRate = 0.0;

        try{
            String urlString = "https://ratesapi.io/api/latest?base=USD";
            URL url = new URL(urlString);
            HttpsURLConnection urlConnection =(HttpsURLConnection)url.openConnection();
            if(urlConnection.getResponseCode() == 200)
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
                currencyApiResult = sb.toString();
                urlConnection.disconnect();
            }
            else
            {
                System.out.println("HTTPS response code is not 200");
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            System.out.println("HTTPS returned value : " + currencyApiResult);
            JSONObject jsonObject = new JSONObject(currencyApiResult);
            JSONObject rates = (JSONObject) jsonObject.get("rates");
            usdExchangeRate = (Double) rates.get(currency);
            currentDate = (String) jsonObject.get("date");
            System.out.println("BaseCurrency 1 USD on " + currentDate + " = " + usdExchangeRate + " " + currency);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usdExchangeRate;

    }

    public void convert(String convertTo, String amountStr) {

        Double usdExchangeRate = 0.0;
        String currency = null;

        if (null != convertTo && null != amountStr) {
            double amountToConvert = Double.parseDouble(amountStr);
            double convertedAmount = 0.0;
            switch (convertTo) {
                case "Euro":
                    currency = "EUR";
                    break;
                case "Indian Rupee":
                    currency = "INR";
                    break;
                case "British Pound":
                    currency = "GBP";
                    break;
                default:
                    break;
            }
            usdExchangeRate = getCurrentUSDExchangeRate(currency);
            convertedAmount = amountToConvert * usdExchangeRate;
            convertedValue = String.format( "%.2f", convertedAmount );
            receiverToSenderAmount = amountStr;
            receiverToSenderCurrency = convertTo;
            System.out.println(amountToConvert + " USD = " + convertedValue + " " + currency);
        }
    }

    public void currencyConvert(View view) {

        String actionString = "sjsu.cmpe277.broadcastreceiver.sender";
        Intent broadcastIntent = new Intent(actionString);

        broadcastIntent.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        broadcastIntent.setComponent(new ComponentName("sjsu.cmpe277.broadcastsender", "sjsu.cmpe277.broadcastsender.ConvertedValueReceiver"));
        broadcastIntent.putExtra("convertedAmount", convertedValue);
        broadcastIntent.putExtra("receiverToSenderAmount", receiverToSenderAmount);
        broadcastIntent.putExtra("receiverToSenderCurrency", receiverToSenderCurrency);

        MainActivity.this.finish();
        sendBroadcast(broadcastIntent);
    }

    public void closeApp(View view) {
        MainActivity.this.finish();
    }

}
