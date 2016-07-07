package com.app.doublenavigationdashboard.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.app.doublenavigationdashboard.R;
import com.app.doublenavigationdashboard.adapter.CustomGalleryAdapter;
import com.app.doublenavigationdashboard.model.CustomGallery;
import com.app.doublenavigationdashboard.utils.Action;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by d on 05/07/2016.
 */
public class PickGalleryImageFragment extends Fragment {

    GridView gridGallery;
    Handler handler;
    CustomGalleryAdapter adapter;
    ArrayList<String> imagePaths;
    ImageView imgSinglePick;

    ImageLoader imageLoader;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_image_grid, container, false);
        init(v);
        return v;
    }

    private void initImageLoader() {
        // for universal image loader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                getActivity()).defaultDisplayImageOptions(defaultOptions).memoryCache(
                new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }


    private void init(View v) {

        handler = new Handler();
        gridGallery = (GridView) v.findViewById(R.id.gridGallery);
        gridGallery.setFastScrollEnabled(true);
        adapter = new CustomGalleryAdapter(getActivity(), imageLoader);
        adapter.setMultiplePick(false);
        gridGallery.setAdapter(adapter);

        gridGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),""+imagePaths.get(i),Toast.LENGTH_LONG).show();
            }
        });

               Intent i = new Intent(Action.ACTION_PICK);
               startActivityForResult(i, 100);

            }


        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePaths = new ArrayList<String>();
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            adapter.clear();

            String single_path = data.getStringExtra("single_path");
            imagePaths.add(single_path);
            imageLoader.displayImage("file://" + single_path, imgSinglePick);

        } else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            String[] all_path = data.getStringArrayExtra("all_path");

            ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

            for (String string : all_path) {
                CustomGallery item = new CustomGallery();
                item.sdcardPath = string;
                imagePaths.add(string);
                dataT.add(item);
            }
            adapter.addAll(dataT);
        }
    }
}
