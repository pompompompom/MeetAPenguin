package com.penguin.meetapenguin.ui.activities;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.ui.fragments.ContactListFragment;
import com.penguin.meetapenguin.ui.fragments.HomeFragment;
import com.penguin.meetapenguin.ui.fragments.InboxFragment;
import com.penguin.meetapenguin.ui.fragments.PrepareShareFragment;
import com.penguin.meetapenguin.ui.fragments.SettingsFragment;
import com.penguin.meetapenguin.ui.fragments.SingleContactFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ContactListFragment.OnListFragmentInteractionListener,
        PrepareShareFragment.OnShareFragmentInteraction {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 300;
    private Fragment homeFragment;
    private Fragment settingsFragment = new SettingsFragment();
    private Fragment contactFragment = new ContactListFragment();
    private Fragment shareFragment = new PrepareShareFragment();
    private Fragment inboxFragment = new InboxFragment();
    private SingleContactFragment singleContactFragment;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        homeFragment = new HomeFragment(toolbar);
        singleContactFragment = new SingleContactFragment(toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayFragment(homeFragment, "Main Page");
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = item.getTitle().toString();

        if (id == R.id.nav_home) {
            displayFragment(homeFragment, title);
        } else if (id == R.id.nav_settings) {
            displayFragment(settingsFragment, title);
        } else if (id == R.id.nav_contacts) {
            displayFragment(contactFragment, title);
        } else if (id == R.id.share) {
            displayFragment(shareFragment, title);
        } else if (id == R.id.nav_inbox) {
            displayFragment(inboxFragment, title);
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragment(Fragment fragment, String title) {
        getFragmentManager().beginTransaction().replace(R.id
                .main_content_frame, fragment).commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onListFragmentInteraction(Contact item) {
        singleContactFragment.setContact(item);
        displayFragment(singleContactFragment, item.getName());
    }

    @Override
    public void onContactInfoSelected(ContactInfo contactInfo) {

    }

    @Override
    public void onShare(Contact item, ArrayList<ContactInfo> selectedContactInfo) {
    }

    public boolean handleCameraPermission(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Snackbar snack = Snackbar.make(view, "Location access is required to show coffee shops nearby.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.READ_CONTACTS},
                                        MY_PERMISSIONS_REQUEST_CAMERA);
                            }
                        });
                snack.show();
                return false;

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getResources().getString(R.string.can_access_camera), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, getResources().getString(R.string.cant_access_camera), Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }


    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    public DrawerLayout getDrawereLayout() {
        return mDrawer;
    }
}
