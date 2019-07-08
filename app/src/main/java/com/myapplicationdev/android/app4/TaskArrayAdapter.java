package com.myapplicationdev.android.app4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskArrayAdapter extends ArrayAdapter<Task> {
	Context context;
	ArrayList<Task> notes;
	int resource;
	ImageView iv1, iv2, iv3, iv4, iv5;
	TextView tvResultName, tvResultDesc, tvResultDateTime;

	public TaskArrayAdapter(Context context, int resource, ArrayList<Task> notes) {
		super(context, resource, notes);
		this.context = context;
		this.notes = notes;
		this.resource = resource;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(resource, parent, false);
		tvResultName = rowView.findViewById(R.id.textViewName);
		tvResultDesc = rowView.findViewById(R.id.textViewDesc);
		tvResultDateTime = rowView.findViewById(R.id.tvDateNTime);


		Task note = notes.get(position);

		tvResultName.setText(note.getName());
		tvResultDesc.setText(note.getDescription());
		tvResultDateTime.setText(note.getDate() + "\n" + note.getTime());


		return rowView;
	}



}
