/*
 * Written by Chaonan Ye
 */
package com.zobtech.scheduler;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ScheduleCursorAdapter extends CursorAdapter {
    public int Count = 0;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ScheduleCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        Count = 0;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        /*
         * when the view is created for the first time,
         * we need to tell the adapters how each item will look
         */
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.list_item, parent, false);

        return retView;
    }



    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textViewSchedule = (TextView) view.findViewById(R.id.list_item_schedule_textview);
        TextView textViewDate = (TextView) view.findViewById(R.id.list_item_date_textview);
        TextView textViewTime = (TextView) view.findViewById(R.id.list_item_time_textview);
        TextView textViewCount = (TextView) view.findViewById(R.id.list_item_countdown_textview);

        // take the data from the cursor
        String inputDateString = cursor.getString(cursor
                .getColumnIndex(DataBaseHelper.COLUMN_DATE));
        Calendar calCurr = Calendar.getInstance();
        Calendar day = Calendar.getInstance();

        //put the cursor data in views
        textViewTime.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_TIME)));
        textViewDate.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_DATE)));
        textViewSchedule.setText(cursor.getString(cursor
                .getColumnIndex(DataBaseHelper.COLUMN_SCHEDULE_TITLE)));

        try {
            day.setTime(new SimpleDateFormat("MMMM dd, yyyy").parse(inputDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textViewCount.setText("Days Left: " + (daysBetween(calCurr, day)));
    }

    public static int daysBetween(Calendar sDate, Calendar eDate) {
        int daysBetween = 0;
        if (eDate.before(sDate)) {
            return 0;
        }
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }
}