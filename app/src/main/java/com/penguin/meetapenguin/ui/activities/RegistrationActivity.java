package com.penguin.meetapenguin.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.util.ProfileManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ProfileManager.getInstance().getUserId() != 1) {
            launchMainActivity();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button register = (Button) findViewById(R.id.registration);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
                launchMainActivity();
            }
        });
    }

    private void saveUserProfile() {
        //Save User on Profile Manager.
        //ProfileManager.getInstance()
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
