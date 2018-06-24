//package com.philiday.projectapplication;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import android.util.Log;
//import android.widget.ArrayAdapter;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.widget.TextView;
//import android.view.View;
//import android.view.ViewGroup;
//import com.google.android.gms.location.DetectedActivity;
//class ActivitiesAdapter extends ArrayAdapter<DetectedActivity> {
//
//    ActivitiesAdapter(Context context,
//                      ArrayList<DetectedActivity> detectedActivities) {
//        super(context, 0, detectedActivities);
//    }
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
//
////Retrieve the data item//
//        DetectedActivity detectedActivity = getItem(position);
//        if (view == null) {
//            view = LayoutInflater.from(getContext()).inflate(
//                    R.layout.activity_recording, parent, false);
//        }
////Retrieve the TextViews where we’ll display the activity type, and percentage//
//
//        TextView activityName = (TextView) view.findViewById(R.id.activity_type);
//
////If an activity is detected...//
//        if (detectedActivity != null) {
//            Log.i("myTag", "DETECTED ACTIVITY" );
//
//            activityName.setText(ActivityIntentService.getActivityString(getContext(),
//
////...get the activity type...//
//                    detectedActivity.getType()));
//
//        }
//        return view;
//    }
//    //Process the list of detected activities//
////    void updateActivities(ArrayList<DetectedActivity> detectedActivities) {
////        HashMap<Integer, Integer> detectedActivitiesMap = new HashMap<>();
////        for (DetectedActivity activity : detectedActivities) {
////            detectedActivitiesMap.put(activity.getType(), activity.getConfidence());
////        }
////
////        ArrayList<DetectedActivity> temporaryList = new ArrayList<>();
////        for (int i = 0; i < ActivityIntentService.POSSIBLE_ACTIVITIES.length; i++) {
////            int confidence = detectedActivitiesMap.containsKey(ActivityIntentService.POSSIBLE_ACTIVITIES[i]) ?
////                    detectedActivitiesMap.get(ActivityIntentService.POSSIBLE_ACTIVITIES[i]) : 0;
////
//////Add the object to a temporaryList//
////            temporaryList.add(new
////                    DetectedActivity(ActivityIntentService.POSSIBLE_ACTIVITIES[i],
////                    confidence));
////        }
//////Remove all elements from the temporaryList//
////        this.clear();
//////Refresh the View//
////
////        for (DetectedActivity detectedActivity: temporaryList) {
////            this.add(detectedActivity);
////        }
// //   }
//}