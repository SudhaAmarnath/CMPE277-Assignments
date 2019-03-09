package sjsu.cmpe277.broadcastsender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConvertedValueReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String convertedAmount = intent.getStringExtra("convertedAmount");
        String receiverToSenderAmount = intent.getStringExtra("receiverToSenderAmount");
        String receiverToSenderCurrency = intent.getStringExtra("receiverToSenderCurrency");

        Intent activityIntent = new Intent(context, MainActivity.class);
        activityIntent.putExtra("convertedAmount", convertedAmount);
        activityIntent.putExtra("receiverToSenderAmount", receiverToSenderAmount);
        activityIntent.putExtra("receiverToSenderCurrency", receiverToSenderCurrency);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityIntent);
    }
}
