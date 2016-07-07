package com.app.doublenavigationdashboard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.doublenavigationdashboard.R;
import com.app.doublenavigationdashboard.fragment.CustomerDetailsFragment;
import com.app.doublenavigationdashboard.fragment.DashBoardFragment;
import com.app.doublenavigationdashboard.fragment.GalleryFragment;
import com.app.doublenavigationdashboard.fragment.ImageGalleryFragment;
import com.app.doublenavigationdashboard.fragment.MapFragment;
import com.app.doublenavigationdashboard.fragment.PickGalleryImageFragment;
import com.app.doublenavigationdashboard.fragment.RecyclerViewFragment;
import com.app.doublenavigationdashboard.fragment.SlidingTabsColorsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends ActionBarActivity implements OnMapReadyCallback{
    DrawerLayout drawer;
    private GoogleMap mMap;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //=================
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DashBoardFragment fragment = new DashBoardFragment();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.addToBackStack(null).commit();
        //===================================
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        fragmentManager = getSupportFragmentManager();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView leftNavigationView = (NavigationView) findViewById(R.id.nav_left_view);
        if (leftNavigationView != null) {
            leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    // Handle Left navigation view item clicks here.
                    int id = item.getItemId();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    if (id == R.id.nav_camera) {

                            SlidingTabsColorsFragment fragment = new SlidingTabsColorsFragment();
                            transaction.replace(R.id.sample_content_fragment, fragment);
                            transaction.addToBackStack(null).commit();

                        Toast.makeText(MainActivity.this, "Left Drawer - Import", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_gallery) {

                        MapFragment fragment=new MapFragment();
                        transaction.replace(R.id.sample_content_fragment, fragment);
                        transaction.addToBackStack(null).commit();

                        Toast.makeText(MainActivity.this, "Left Drawer - Gallery", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_slideshow) {
                        RecyclerViewFragment fragment=new RecyclerViewFragment();
                        transaction.replace(R.id.sample_content_fragment, fragment);
                        transaction.addToBackStack(null).commit();

                        Toast.makeText(MainActivity.this, "Left Drawer - Slideshow", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_manage) {

                      /* ImageGalleryFragment fragment=new ImageGalleryFragment();
                        transaction.replace(R.id.sample_content_fragment, fragment);
                        transaction.commit();*/
                        /*GalleryFragment fragment=new GalleryFragment();
                        transaction.replace(R.id.sample_content_fragment, fragment);
                        transaction.commit();
*/
                       /* PickGalleryImageFragment fragment=new PickGalleryImageFragment();
                        transaction.replace(R.id.sample_content_fragment, fragment);
                        transaction.commit();*/

                        Toast.makeText(MainActivity.this, "Left Drawer - Tools", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_share) {

                       /* CustomerDetailsFragment fragment=new CustomerDetailsFragment();
                        transaction.replace(R.id.sample_content_fragment, fragment);
                        transaction.commit();*/
                        CustomerDetailsFragment customerFrag = CustomerDetailsFragment.newInstance(1, 2);
                        fragmentManager.beginTransaction().replace(R.id.sample_content_fragment, customerFrag)
                                .addToBackStack(null).commit();

                        Toast.makeText(MainActivity.this, "Left Drawer - Share", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_send) {
                        Toast.makeText(MainActivity.this, "Left Drawer - Send", Toast.LENGTH_SHORT).show();
                        DashBoardFragment fragment = new DashBoardFragment();
                        transaction.replace(R.id.sample_content_fragment, fragment);
                        transaction.addToBackStack(null).commit();
                    }

                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
        }

        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        if (rightNavigationView != null) {
            rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    // Handle Right navigation view item clicks here.
                    int id = item.getItemId();

                    if (id == R.id.nav_settings) {
                        Toast.makeText(MainActivity.this, "Right Drawer - Settings", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_logout) {
                        Toast.makeText(MainActivity.this, "Right Drawer - Logout", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_help) {
                        Toast.makeText(MainActivity.this, "Right Drawer - Help", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_about) {
                        Toast.makeText(MainActivity.this, "Right Drawer - About", Toast.LENGTH_SHORT).show();
                    }

                    drawer.closeDrawer(GravityCompat.END); /*Important Line*/
                    return true;
                }
            });
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
           // super.onBackPressed();
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            }
           // System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(Menu.NONE, R.id.action_openRight, Menu.NONE,"slider");
        menu.add(Menu.NONE, R.id.profile, Menu.NONE, "profile");

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_openRight) {
            drawer.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
            return true;
        }
        if (id == R.id.profile) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CustomerDetailsFragment fragment=new CustomerDetailsFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.addToBackStack(null).commit();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}