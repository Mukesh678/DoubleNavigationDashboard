package com.app.doublenavigationdashboard.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.doublenavigationdashboard.R;
import com.app.doublenavigationdashboard.activities.ImageViewFlipper;
import com.app.doublenavigationdashboard.utils.FileUtilsNew;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by d on 04/07/2016.
 */
public class ImageGalleryFragment extends Fragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadApps(); // do this in onresume?


    }

    GridView mGrid;
    private static final String DIRECTORY = "/sdcard/";
    private static final String DATA_DIRECTORY = "/sdcard/.ImageViewFlipper/";
    private static final String DATA_FILE = "/sdcard/.ImageViewFlipper/imagelist.dat";
    List<String> ImageList;
    public boolean mBusy = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.grid, container, false);
        mGrid = (GridView) v.findViewById(R.id.myGrid);
        mGrid.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        mBusy = false;
                        mAdapter.notifyDataSetChanged();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        mBusy = true;
                        // mStatus.setText("Touch scroll");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        mBusy = true;
                        // mStatus.setText("Fling");
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });


        File data_directory = new File(DATA_DIRECTORY);
        if (!data_directory.exists()) {
            if (data_directory.mkdir()) {
                FileUtilsNew savedata = new FileUtilsNew();
                Toast toast = Toast.makeText(getActivity(),
                        "Please wait while we search your SD Card for images...", Toast.LENGTH_SHORT);
                toast.show();
                SystemClock.sleep(100);
                ImageList = FindFiles();
                savedata.saveArray(DATA_FILE, ImageList);

            } else {
                ImageList = FindFiles();
            }

        }
        else {
            File data_file= new File(DATA_FILE);
            if (!data_file.exists()) {
                FileUtilsNew savedata = new FileUtilsNew();
                Toast toast = Toast.makeText(getActivity(),
                        "Please wait while we search your SD Card for images...", Toast.LENGTH_SHORT);
                toast.show();
                SystemClock.sleep(100);
                ImageList = FindFiles();
                savedata.saveArray(DATA_FILE, ImageList);
            } else {
                FileUtilsNew readdata = new FileUtilsNew();
                ImageList = readdata.loadArray(DATA_FILE);
            }
        }
        mAdapter = new AppsAdapter();
        mGrid.setAdapter(mAdapter);
        mThumbnails = new HashMap<Integer,SoftReference<ImageView>>();
        mThumbnailImages = new HashMap<Integer,SoftReference<Bitmap>>();

        return v;
    }
    private List<String> FindFiles() {
        final List<String> tFileList = new ArrayList<String>();
        Resources resources = getResources();
        // array of valid image file extensions
        String[] imageTypes = resources.getStringArray(R.array.image);
        FilenameFilter[] filter = new FilenameFilter[imageTypes.length];

        int i = 0;
        for (final String type : imageTypes) {
            filter[i] = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith("." + type);
                }
            };
            i++;
        }

        FileUtilsNew fileUtilsNew = new FileUtilsNew();
        File[] allMatchingFiles = fileUtilsNew.listFilesAsArray(
                new File(DIRECTORY), filter, -1);
        for (File f : allMatchingFiles) {
            tFileList.add(f.getAbsolutePath());
        }
        return tFileList;
    }

    private List<ResolveInfo> mApps;

    private void loadApps() {
        Intent mainIntent = new Intent(getActivity().getIntent().ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_DEFAULT);

        mApps = getActivity().getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    public AppsAdapter mAdapter;
    public class AppsAdapter extends BaseAdapter {
        public AppsAdapter() {
            map = new HashMap();
        }

        public Map<Integer,SoftReference<Bitmap>> map;
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView i;

            if (convertView == null) {
                i = new ImageView(getActivity());
                i.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i.setLayoutParams(new GridView.LayoutParams(80, 80));
            } else {
                i = (ImageView) convertView;
            }

            if(!mBusy && mThumbnailImages.containsKey(position)
                    && mThumbnailImages.get(position).get()!=null) {
                i.setImageBitmap(mThumbnailImages.get(position).get());
            }
            else  {
                i.setImageBitmap(null);
                if(!mBusy)loadThumbnail(i,position);
            }

            i.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Opening Image...", Toast.LENGTH_LONG).show();

                    // TODO Auto-generated method stub
                    SharedPreferences indexPrefs = getActivity().getSharedPreferences("currentIndex",
                            getActivity().MODE_PRIVATE);

                    SharedPreferences.Editor indexEditor = indexPrefs.edit();
                    indexEditor.putInt("currentIndex", position);
                    indexEditor.commit();
                    final Intent intent = new Intent(getActivity(), ImageViewFlipper.class);
                    startActivity(intent);

                }
            });


            return i;
        }


        public final int getCount() {
            return ImageList.size();
        }

        public final Object getItem(int position) {
            return ImageList.get(position);
        }

        public final long getItemId(int position) {
            return position;
        }
    }

    private Map<Integer,SoftReference<ImageView>> mThumbnails;
    private Map<Integer,SoftReference<Bitmap>> mThumbnailImages;

    private void loadThumbnail( ImageView iv, int position ){
        mThumbnails.put(position,new SoftReference<ImageView>(iv));
        try{new LoadThumbnailTask().execute(position);}catch(Exception e){}
    }
    public void onThumbnailLoaded( int position, Bitmap bm, LoadThumbnailTask t ){
        Bitmap tn = bm;
        if( mThumbnails.get(position).get() != null && tn!=null)
            mThumbnails.get(position).get().setImageBitmap(tn);

        t.cancel(true);
    }

    public class LoadThumbnailTask extends AsyncTask<Integer, Void, Bitmap> {
        private int position;
        @Override
        protected Bitmap doInBackground(Integer... params) {
            try{
                position = params[0];
                Bitmap bitmapOrg = BitmapFactory.decodeFile(ImageList.get(position));

                int width = bitmapOrg.getWidth();
                int height = bitmapOrg.getHeight();

                //new width / height
                int newWidth = 80;
                int newHeight = 80;

                // calculate the scale
                float scaleWidth = (float) newWidth / width;
                float scaleHeight = (float) newHeight/ (height * scaleWidth) ;
                // create a matrix for the manipulation
                Matrix matrix = new Matrix();

                // resize the bit map
                matrix.postScale(scaleWidth, scaleWidth);
                matrix.postScale(scaleHeight, scaleHeight);

                // recreate the new Bitmap and set it back
                Bitmap bm = Bitmap.createBitmap(bitmapOrg, 0, 0,width, height, matrix, true);

                mThumbnailImages.put(position, new SoftReference<Bitmap>(bm));
                System.gc();
                return bm;
            }catch(Exception e){

            }


            return null;
        }
        protected void onPostExecute(Bitmap bm) {

            onThumbnailLoaded(position, bm, this);
        }

    }

}
