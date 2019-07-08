package com.myapplicationdev.android.app4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class ActivityEdit extends AppCompatActivity {

    TextView tvID, tvCount;
    EditText etContent, etDesc, etDate;
    Button btnUpdate, btnDelete, btnBack;
    Task data;
    Calendar calander = Calendar.getInstance();
    Date currentTime = Calendar.getInstance().getTime();

    int Year = calander.get(Calendar.YEAR);
    int Day = calander.get(Calendar.DAY_OF_MONTH);;
    int Month = calander.get(Calendar.MONTH);;


    int Hour = calander.get(Calendar.HOUR_OF_DAY);
    int Minute = calander.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvID = findViewById(R.id.tvID);
        etContent = findViewById(R.id.etContent);
        etDesc = findViewById(R.id.editTextDESC);
        etDate = findViewById(R.id.etDate);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);

        setTitle("Edit Tasks");

        Intent i = getIntent();
        data = (Task) i.getSerializableExtra("data");

        tvID.setText("Task Number : " + data.getId());
        etContent.setText(data.getName());
        etDesc.setText(data.getDescription());
        etDate.setText(data.getDate());

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

                DatePickerDialog myDateDialog = new DatePickerDialog(ActivityEdit.this, myDateListener, Year, Month, Day);
                myDateDialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityEdit.this, SecondActivity.class);
                startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ActivityEdit.this);
                data.setName(etContent.getText().toString());
                data.setDescription(etDesc.getText().toString());
                data.setDate(etDate.getText().toString());
                dbh.updateTask(data);
                dbh.close();

                Intent intent = new Intent();
                setResult(RESULT_OK,intent);

                finish();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ActivityEdit.this);
                dbh.deleteTask(data.getId());
                dbh.close();

                Intent intent = new Intent();
                setResult(RESULT_OK,intent);

                Toast.makeText(getApplicationContext(),"Task deleted successfully",Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }
}
