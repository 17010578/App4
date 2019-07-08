package com.myapplicationdev.android.app4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

	ListView lv2;
	ArrayAdapter aa;
	ArrayList<Task> note,dataUpdate;
	ArrayList<String>al2;
	TextView tvCount;
	Button btnadd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO implement the Custom ListView
		setContentView(R.layout.activity_second);

		setTitle("Tasks Added");
		Intent intentReceived = getIntent();

		lv2 = findViewById(R.id.lv);
		tvCount = findViewById(R.id.tvCount);
		btnadd = findViewById(R.id.buttonAddPage);

		DBHelper db = new DBHelper(SecondActivity.this);
		int count = db.noOfTask();
		db.close();
		tvCount.setText("Number Of Tasks: " + count + "");

		dataUpdate = db.getTasks();
		aa = new TaskArrayAdapter(this,R.layout.row,dataUpdate);
		lv2.setAdapter(aa);

		btnadd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SecondActivity.this,MainActivity.class);
				startActivity(i);
			}
		});


		lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int
					position, long identity) {
				Intent i = new Intent(SecondActivity.this,ActivityEdit.class);

				Task tasks = dataUpdate.get(position);
				i.putExtra("data", tasks);

				startActivityForResult(i, 9);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 9){
			dataUpdate.clear();
			DBHelper db = new DBHelper(SecondActivity.this);
			dataUpdate = db.getTasks();

			aa = new TaskArrayAdapter(this,R.layout.row,dataUpdate);
			lv2.setAdapter(aa);
		}
	}

}
