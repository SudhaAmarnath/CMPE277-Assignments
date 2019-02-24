package sjsu.cmpe277.android.activitylifecycle;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.TextView;

        import sjsu.cmpe277.android.activitylifecycle.util.StatusTracker;


        import static android.content.ContentValues.TAG;


/**
 * Example Activity to demonstrate the lifecycle callback methods.
 */
public class ActivityB extends Activity {

    private String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    public TextView threadCounterView;

    Intent returnIntent = new Intent();
    private StatusTracker mStatusTracker = StatusTracker.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        String msg = "Total thread counter in B onCreate is: "+ String.valueOf(ActivityA.threadCounter);
        Log.v(TAG,msg);
    }

    @Override
    protected void onStart() {

        super.onStart();
        String msg = "Total thread counter in B onStart is: "+ String.valueOf(ActivityA.threadCounter);
        Log.v(TAG,msg);
    }

    @Override
    protected void onRestart() {

        super.onRestart();

        ActivityA.threadCounter++;
        String msg = "Total thread counter in B onRestart is: "+ String.valueOf(ActivityA.threadCounter);
        Log.v(TAG,msg);

    }

    @Override
    protected void onResume() {

        super.onResume();
        String msg = "Total thread counter in B onResume is: "+ String.valueOf(ActivityA.threadCounter);
        Log.v(TAG,msg);

    }

    @Override
    protected void onPause() {

        super.onPause();
        String msg = "Total thread counter in B onPause is: "+ String.valueOf(ActivityA.threadCounter);
        Log.v(TAG,msg);
    }

    @Override
    protected void onStop() {

        super.onStop();
        String msg = "Total thread counter in B onStop is: "+ String.valueOf(ActivityA.threadCounter);
        Log.v(TAG,msg);
    }

    @Override
    protected void onDestroy() {
        super.  onDestroy();
        String msg = "Total thread counter in B onDestroy is: "+ String.valueOf(ActivityA.threadCounter);
        Log.v(TAG,msg);
    }

    public void finishActivityB(View v) {
        ActivityB.this.finish();
        String msg = "Total thread counter in B Finish is: "+ String.valueOf(ActivityA.threadCounter);
        Log.v(TAG,msg);
    }
}
