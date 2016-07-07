package com.app.doublenavigationdashboard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.doublenavigationdashboard.R;

/**
 * Created by d on 07/07/2016.
 */
public class DashBoardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, null);


        Button btn_newsfeed = (Button) view.findViewById(R.id.btn_news_feed);

        // Dashboard Friends button
        Button btn_friends = (Button) view.findViewById(R.id.btn_friends);

        // Dashboard Messages button
        //    Button btn_messages = (Button) view.findViewById(R.id.btn_messages);

        // Dashboard Places button
        Button btn_places = (Button) view.findViewById(R.id.btn_places);

        // Dashboard Events button
        //     Button btn_events = (Button) view.findViewById(R.id.btn_events);

        // Dashboard Photos button
        Button btn_photos = (Button) view.findViewById(R.id.btn_photos);

        /**
         * Handling all button click events
         * */

        // Listening to News Feed button click
        assert btn_newsfeed != null;
        btn_newsfeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                SlidingTabsColorsFragment fragment = new SlidingTabsColorsFragment();
                transaction.replace(R.id.sample_content_fragment, fragment);
                transaction.addToBackStack(null).commit();
            }
        });

        // Listening Friends button click
        assert btn_friends != null;
        btn_friends.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                RecyclerViewFragment fragment=new RecyclerViewFragment();
                transaction.replace(R.id.sample_content_fragment, fragment);
                transaction.addToBackStack(null).commit();
            }
        });

        // Listening Messages button click
       /* assert btn_messages != null;
        btn_messages.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen

            }
        });*/

        // Listening to Places button click
        assert btn_places != null;
        btn_places.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                MapFragment fragment=new MapFragment();
                transaction.replace(R.id.sample_content_fragment, fragment);
                transaction.addToBackStack(null).commit();
            }
        });

        // Listening to Events button click
      /*  btn_events.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen

            }
        });
*/
        // Listening to Photos button click
        btn_photos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen

            }
        });

        //===============
        return view;
    }
}
