package sjsu.cmpe277.android.activitylifecycle.util;

//To track the status of the activity and store in a list activity name and all status of that activity and to store current status of activity in map

import java.util.*;

public class StatusTracker {
    // to hold the activity name and status of activity
    private Map<String, String > mStatusMap;

    //to hold the list of all methods of all activities
    private List<String> mMethodList;

    public static StatusTracker ourInstance=new StatusTracker();
    public static final String STATUS_SUFFIX="ed";

    private StatusTracker()
    {
        mStatusMap = new LinkedHashMap<String, String>();
        mMethodList = new ArrayList<String>();
    }

    public static StatusTracker getInstance()
    {
        return ourInstance;
    }


    public void setStatus(String activityName,String status)
    {
        mMethodList.add(activityName+"."+status+"()");
        if(mStatusMap.containsKey(activityName))
        {
            mStatusMap.remove(activityName);
        }
        mStatusMap.put(activityName, status);
    }

    public String getStatus(String activityName)
    {
        String status = mStatusMap.get(activityName);
        //to remove on from status like onCreate,onStart
        status = status.substring(2, status.length());

        // String manipulation to ensure the status value is spelled correctly.
        if (status.endsWith("e")) {
            status = status.substring(0, status.length() - 1);
        }
        if (status.endsWith("p")) {
            status = status + "p";
        }
        status = status + STATUS_SUFFIX;
        return status;

    }

    public void clear() {
        mMethodList.clear();
        mStatusMap.clear();
    }

    public List<String> getMethodList() {
        return mMethodList;
    }

    public Set<String> keySet() {
        return mStatusMap.keySet();
    }


}
