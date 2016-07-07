package com.app.doublenavigationdashboard.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.app.doublenavigationdashboard.R;
import com.app.doublenavigationdashboard.adapter.GridViewAdapter;
import com.app.doublenavigationdashboard.utils.AppConstant;
import com.app.doublenavigationdashboard.utils.Utils;

import java.util.ArrayList;

/**
 * Created by d on 05/07/2016.
 */
public class GalleryFragment extends Fragment{
    GridView gridViewImage;
    private Utils utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private GridViewAdapter adapter;
    private GridView gridView;
    private int columnWidth;
    Context context;

    public GalleryFragment() {
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_more, null);

        // Create UI components here.
        gridViewImage = (GridView)view.findViewById(R.id.grid_view);
        utils = new Utils(getActivity());

        // Initilizing Grid View
        InitilizeGridLayout();

        // loading all image paths from SD card
        imagePaths = utils.getFilePaths();

        // Gridview adapter
        adapter = new GridViewAdapter(imagePaths,
                columnWidth,getActivity());

        // setting grid view adapter
        try {
            gridViewImage.setAdapter(adapter);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //gridViewImage.setAdapter(new GridViewImageAdapter(activity, filePaths, imageWidth));

        return view;
    }

    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstant.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

        gridViewImage.setNumColumns(AppConstant.NUM_OF_COLUMNS);
        gridViewImage.setColumnWidth(columnWidth);
        gridViewImage.setStretchMode(GridView.NO_STRETCH);
        gridViewImage.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridViewImage.setHorizontalSpacing((int) padding);
        gridViewImage.setVerticalSpacing((int) padding);
    }
}


