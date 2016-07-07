package com.app.doublenavigationdashboard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.doublenavigationdashboard.R;
import com.app.doublenavigationdashboard.adapter.CardViewDataAdapter;
import com.app.doublenavigationdashboard.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d on 04/07/2016.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Student> studentList;

    private Button btnSelection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_fragment, container, false);
        btnSelection = (Button) v.findViewById(R.id.btnShow);
        studentList = new ArrayList<Student>();

        for (int i = 1; i <= 15; i++) {
            Student st = new Student("Student " + i, "androidstudent" + i
                    + "@gmail.com", false);

            studentList.add(st);
        }
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter(studentList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        btnSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = "";
                List<Student> stList = ((CardViewDataAdapter) mAdapter)
                        .getStudentist();

                for (int i = 0; i < stList.size(); i++) {
                    Student singleStudent = stList.get(i);
                    if (singleStudent.isSelected() == true) {

                        data = data + "\n" + singleStudent.getName().toString();
      /*
       * Toast.makeText( CardViewActivity.this, " " +
       * singleStudent.getName() + " " +
       * singleStudent.getEmailId() + " " +
       * singleStudent.isSelected(),
       * Toast.LENGTH_SHORT).show();
       */
                    }

                }

                Toast.makeText(getActivity(),
                        "Selected Students: \n" + data, Toast.LENGTH_LONG)
                        .show();
            }
        });



        return v;

    }
}
