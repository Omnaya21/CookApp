package com.ktchen.cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import java.util.GregorianCalendar;

/**
 * This class runs the calendar functionality.  Sends an intent with selected date to phone's calendar app.
 */
public class ViewCalendar extends AppCompatActivity {
    CalendarView calendarView;
    private static final String TAG = "calendar view";
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        // Get a reference for the week view in the layout.
        calendarView= (CalendarView) findViewById(R.id.calendarView);

        /**
         * Date change listener. Takes clicked on date and sends it through an intent.
         */
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date= month+1 + "/"+dayOfMonth + "/"+ year;
                Log.i(TAG, "date selected is "+ date);

                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setDataAndType(CalendarContract.Events.CONTENT_URI,"vnd.android.cursor.item/event" );
                calIntent.putExtra(CalendarContract.Events.TITLE, "My Dinner Plan");


                GregorianCalendar calDate = new GregorianCalendar(year, month, dayOfMonth);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        calDate.getTimeInMillis());
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        calDate.getTimeInMillis());

                startActivity(calIntent);
            }
        });


    }
}
