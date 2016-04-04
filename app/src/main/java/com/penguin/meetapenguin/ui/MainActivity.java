package com.penguin.meetapenguin.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.model.Contact;
import com.penguin.meetapenguin.model.ContactInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ContactListFragment.OnListFragmentInteractionListener, PrepareShareFragment.OnShareFragmentInteraction {

    private Fragment homeFragment = new HomeFragment();
    private Fragment contactFragment = new ContactListFragment();
    private Fragment shareFragment = new PrepareShareFragment();
    private Fragment inboxFragment = new InboxFragment();
    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayFragment(homeFragment, "Main Page");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        } else if (id == R.id.nav_contacts) {
            displayFragment(contactFragment, title);
        } else if (id == R.id.share) {
            displayFragment(shareFragment, title);
        } else if (id == R.id.nav_mail) {
        } else if (id == R.id.nav_inbox){
            displayFragment(inboxFragment, title);
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragment(Fragment fragment, String title) {
        getFragmentManager().beginTransaction().replace(R.id.main_content_frame, fragment).commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onListFragmentInteraction(Contact item) {

    }

    @Override
    public void onContactInfoSelected(ContactInfo contactInfo) {

    }

    @Override
    public void onShare(Contact item, ArrayList<ContactInfo> selectedContactInfo) {

    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    public DrawerLayout getDrawereLayout() {
        return mDrawer;
    }
}
