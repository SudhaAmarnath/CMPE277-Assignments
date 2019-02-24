package sjsu.cmpe277.android.activitylifecycle.util;


import android.os.Handler;

import android.widget.TextView;

import java.util.List;

public class Utils
{
    //Handler is used to delay the output due to overlaps in lifecycle state changes as one Activity launches another.
    public static StatusTracker mStatusTracker = StatusTracker.getInstance();

    public static void printStatus(final TextView viewMethods, final TextView viewStatus)
    {

        Handler handler = new Handler();
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                StringBuilder sbMethods= new StringBuilder();
                List<String> listMethods = mStatusTracker.getMethodList();

                for(String method:listMethods)
                {
                    sbMethods.insert(0,method + "\r\n");
                }
                if (viewMethods != null)
                {
                    viewMethods.setText(sbMethods.toString());
                }

                // Get the status of all Activity classes and print to TextView
                StringBuilder sbStatus = new StringBuilder();
                for (String key:mStatusTracker.keySet())
                {
                    sbStatus.insert(0,key+ ":"+mStatusTracker.getStatus(key)+"\n");
                }
                if(viewStatus != null)
                {
                    viewStatus.setText(sbStatus.toString());
                }

            }
        };
        handler.postDelayed(r,750);
    }
}
