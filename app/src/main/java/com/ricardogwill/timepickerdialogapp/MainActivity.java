package com.ricardogwill.timepickerdialogapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePickerDialogButtonOnClick();
    }

    Button timePickerDialogButton;
    static final int DIALOG_ID = 0;
    int hourX;
    int minuteX;

    public void timePickerDialogButtonOnClick() {
        timePickerDialogButton = findViewById(R.id.timepicker_dialog_button);

        timePickerDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new TimePickerDialog(MainActivity.this, onTimeSetListener, hourX, minuteX, false);
        } else {
            return null;
        }
    }

    protected TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // The reason for much of the lunacy before the Toast is to convert
            // 24 hours into 12, and to make sure all minutes are 2 digits long, as well as to add
            // AM if it is before 12 noon, and PM if it is 12 noon or later.
            hourX = hourOfDay;
            minuteX = minute;
            // If the hour (out of 24) is less than 12, "amPm" is AM, otherwise PM.
            String amPm;
            if (hourX < 12) {
                amPm = " AM";
            } else {
                amPm = " PM";
            }
            // Turns 24-hour time into 12-hour time (but 12:00am is 0:0am).
            if (hourX > 12) {
                hourX = hourX - 12;
            }
            // If the converted 12-hour time is NOT "0", keep it as is. Change the hour "0" to "12".
            String hourString;
            if (hourX != 0) {
                hourString = Integer.toString(hourX);
            } else {
                hourString = Integer.toString(12);
            }
            // Minutes (ints) may be single-digit (if less than 10), so make sure all minutes
            // are two-digits long, and convert them from "ints" to "Strings".
            String minuteString = String.format("%02d", minuteX);
            // Make the Toast come together via concatenation of Strings with objects.
            Toast.makeText(MainActivity.this, "The time picked is:  " + hourString
                    + ":" + minuteString + amPm, Toast.LENGTH_LONG).show();
        }
    };
}
