package com.example.jimssgym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



import android.graphics.RectF;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public abstract class CalenderActivity extends AppCompatActivity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {

    private static final String TAG = "CalendarActivity";
    private TextView name;

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.title_tv);
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            String t = extra.getString("EXTRA_NAME");
            name.setText(t);
        }


        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        mWeekView.invalidate();
//
//
//        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final String current_user_id = mAuth.getUid();
//
//
//        DocumentReference docRef = db.collection("users").document(current_user_id + "/calendar/personal_calendar");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                String day = null;
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//
//                        Intent intent = new Intent(CalenderActivity.this, FillCalenderActivity.class);
//                        String monday = ";;";
//                        String tuesday = ";;";
//                        String wednesday = ";;";
//                        String thursday = ";;";
//                        String friday = ";;";
//                        String saturday = ";;";
//                        String sunday = ";;";
//
//                        if (document.getString("monday") != null)
//                            monday = document.getData().get("monday").toString();
//                        if (document.getString("tuesday") != null)
//                            tuesday = document.getData().get("tuesday").toString();
//                        if (document.getString("wednesday") != null)
//                            wednesday = document.getData().get("wednesday").toString();
//                        if (document.getString("thursday") != null)
//                            thursday = document.getData().get("thursday").toString();
//                        if (document.getString("friday") != null)
//                            friday = document.getData().get("friday").toString();
//                        if (document.getString("saturday") != null)
//                            saturday = document.getData().get("saturday").toString();
//                        if (document.getString("sunday") != null)
//                            sunday = document.getData().get("sunday").toString();
//
//                        intent.putExtra("EXTRA_NAME", "Your Calender");
//                        intent.putExtra("EXTRA_MONDAY", monday);
//                        intent.putExtra("EXTRA_TUESDAY", tuesday);
//                        intent.putExtra("EXTRA_WEDNESDAY", wednesday);
//                        intent.putExtra("EXTRA_THURSDAY", thursday);
//                        intent.putExtra("EXTRA_FRIDAY", friday);
//                        intent.putExtra("EXTRA_SATURDAY", saturday);
//                        intent.putExtra("EXTRA_SUNDAY", sunday);
//
//                        startActivity(intent);
//
//
//                    }else{
//                        Log.d(TAG, "No such document");
//                    }
//
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//
//
//        System.out.println("hi");
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
//        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
        if (event.getName().equals("WOD")){
            startActivity(new Intent(CalenderActivity.this, PopUpWorkoutActivity.class));
        }

//        Intent intent = new Intent(CalenderActivity.this, PopUpWorkoutActivity.class);
//        intent.putExtra("POPTYPE", event.getName());
//        startActivity(intent);

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Bundle extra = getIntent().getExtras();
        if ("Your Calender".equals(extra.getString("EXTRA_NAME"))){
//            Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
            Integer day = event.getStartTime().get(Calendar.DAY_OF_WEEK);
            String[] days_of_week = new String[] {"", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
            String daySelected = days_of_week[day];

            Intent intent = new Intent(CalenderActivity.this, PopUpDelete.class);
//            intent.putExtra("POPTYPE", event.getName());
            intent.putExtra("POPTYPE", daySelected);
            startActivity(intent);
        }
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Bundle extra = getIntent().getExtras();
        if ("Your Calender".equals(extra.getString("EXTRA_NAME"))){
//            Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CalenderActivity.this, PopUpAddTime.class);
            intent.putExtra("POPTYPE", getEventTitle(time));
            startActivity(intent);
        }
    }

    public WeekView getWeekView() {
        return mWeekView;
    }
}


