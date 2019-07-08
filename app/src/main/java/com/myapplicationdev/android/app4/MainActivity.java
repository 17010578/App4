package com.myapplicationdev.android.app4;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText etName, etDes,etSec, etDate, etTime;
    Button insert;
    Button show;
    TextView tvCount;

    Calendar calander = Calendar.getInstance();
    Date currentTime = Calendar.getInstance().getTime();

    int Year = calander.get(Calendar.YEAR);
    int Day = calander.get(Calendar.DAY_OF_MONTH);;
    int Month = calander.get(Calendar.MONTH);;


    int Hour = calander.get(Calendar.HOUR_OF_DAY);
    int Minute = calander.get(Calendar.MINUTE);

    ArrayList<String> al;
    ArrayAdapter aa;

    int reqCode = 12345;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDes = findViewById(R.id.editTextDes2);
        etName = findViewById(R.id.editTextTaskName);
        etSec = findViewById(R.id.editTextSec2);
        etDate = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);
        insert = findViewById(R.id.buttonInsertNote);
        show = findViewById(R.id.buttonShowList);
        tvCount = findViewById(R.id.tvCount);

        setTitle("Task Manager");

        al = new ArrayList<String>();

        Intent i = getIntent();


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDate.setText("Date: " + dayOfMonth +"/"+(month+1)+"/"+year);
                        Year = year;
                        Month = month;
                        Day = dayOfMonth;
                    }
                };

                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, Year, Month, Day);
                myDateDialog.show();
            }
        });


        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String currMin = "";
                        if (minute < 10){
                            currMin += "0"+minute;
                        } else {
                            currMin += minute+"";
                        }
                        etTime.setText("Time: "+ hourOfDay+ ":" + currMin);
                        Hour = hourOfDay;
                        Minute = minute;
                    }
                };
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, Hour, Minute,true);
                myTimeDialog.show();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String desc = etDes.getText().toString();
                String sec = etSec.getText().toString();
                String date = etDate.getText().toString();
                String time = etTime.getText().toString();

                DBHelper db = new DBHelper(MainActivity.this);
                ArrayList<String> stored = new ArrayList<>();
                stored = db.getTaskContent();

                boolean store = false;

                for (int i =0; i<stored.size(); i++){
                    if (name.equalsIgnoreCase(stored.get(i))) {
                        store = true;
                    }
                }
                if (name.length() == 0) {
                    Toast.makeText(getBaseContext(), "Name cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (desc.length() == 0) {
                    Toast.makeText(getBaseContext(), "Description cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (sec.length() == 0){
                    Toast.makeText(getBaseContext(), "Seconds cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (store == false && (name.length() > 0 || desc.length() > 0 || sec.length() > 0 )){
                        int seconds = Integer.parseInt(sec);
                        long row_affected = db.insertTask(name,desc,date, time);
                        db.close();

                        if (row_affected != -1){
                            Toast.makeText(MainActivity.this, "Insert successful",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Insert Failed",
                                    Toast.LENGTH_LONG).show();
                        }
                        db.getWritableDatabase();

                        Intent i = new Intent(MainActivity.this, BroadcastTaskReceiver.class);
                        i.putExtra("name", name);

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, seconds);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                }
                    else{
                        Toast.makeText(MainActivity.this, "Data exist", Toast.LENGTH_LONG).show();
                    }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(i);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DBHelper db = new DBHelper(MainActivity.this);
        tvCount.setText(db.noOfTask() + "");
        }
}
