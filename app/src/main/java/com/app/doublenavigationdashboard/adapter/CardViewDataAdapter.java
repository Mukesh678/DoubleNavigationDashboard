package com.app.doublenavigationdashboard.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.app.doublenavigationdashboard.R;
import com.app.doublenavigationdashboard.model.Student;

public class CardViewDataAdapter extends
		RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {

	private List<Student> stList;

	public CardViewDataAdapter(List<Student> students) {
		this.stList = students;

	}

	// Create new views
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		// create a new view
		View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.cradview_row, null);

		// create ViewHolder

		ViewHolder viewHolder = new ViewHolder(itemLayoutView);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {

		final int pos = position;

		viewHolder.tvName.setText(stList.get(position).getName());

		viewHolder.tvEmailId.setText(stList.get(position).getEmailId());

		viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

		viewHolder.chkSelected.setTag(stList.get(position));

		
		viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				Student contact = (Student) cb.getTag();

				contact.setSelected(cb.isChecked());
				stList.get(pos).setSelected(cb.isChecked());


			}
		});

	}

	// Return the size arraylist
	@Override
	public int getItemCount() {
		return stList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView tvName;
		public TextView tvEmailId;

		public CheckBox chkSelected;

		public Student singlestudent;

		public ViewHolder(View itemLayoutView) {
			super(itemLayoutView);

			tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);

			tvEmailId = (TextView) itemLayoutView.findViewById(R.id.tvEmailId);
			chkSelected = (CheckBox) itemLayoutView
					.findViewById(R.id.chkSelected);

		}

	}

	// method to access in activity after updating selection
	public List<Student> getStudentist() {
		return stList;
	}

}
