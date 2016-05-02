package com.penguin.meetapenguin.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.util.ProfileManager;
import com.penguin.meetapenguin.util.ServerConstants;
import com.penguin.meetapenguin.util.entitiesHelper.AttributesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a page that allows the user to login.
 */
public class RegistrationActivity extends AppCompatActivity {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private Pattern mPattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private TextInputLayout mWrapperName;
    private TextInputLayout mWrapperEmail;
    private RequestQueue mRequestQueue;
    private Contact mContact;
    private String TAG = RegistrationActivity.class.getSimpleName();
    private ProgressDialog mProgressDialog;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ProfileManager.getInstance().getContact() != null && ProfileManager.getInstance().getUserId() != 1) {
            launchMainActivity();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mRegister = (Button) findViewById(R.id.registration);
        mWrapperName = (TextInputLayout) findViewById(R.id.name);
        mWrapperEmail = (TextInputLayout) findViewById(R.id.login_email);
        mEditTextName = (EditText) findViewById(R.id.input_name);
        mEditTextEmail = (EditText) findViewById(R.id.input_email);
        mRequestQueue = Volley.newRequestQueue(RegistrationActivity.this);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateLoginInput()) return;
                mContact = getUser();

                mProgressDialog = new ProgressDialog(RegistrationActivity.this, ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setTitle(getResources().getString(R.string.loading));
                mProgressDialog.setMessage(getResources().getString(R.string.please_wait));
                mProgressDialog.show();

                //REMOVE THIS HARDCODE ID FOR PRESENTATION PURPOSE.
                mContact.setId(31);

                final Gson gson = new Gson();
                String json = gson.toJson(mContact);
                JSONObject object = null;
                try {
                    object = new JSONObject(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*Json Request*/
                String url = ServerConstants.SERVER_URL + "/contacts";
                JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                mContact = gson.fromJson(response.toString(), Contact.class);
                                saveUserProfile();
                                mProgressDialog.dismiss();
                                mProgressDialog = null;
                                launchMainActivity();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mProgressDialog.dismiss();
                                mProgressDialog = null;
                                Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                            }
                        });

                loginRequest.setTag(TAG);
                mRequestQueue.add(loginRequest);
            }
        });
    }

    public boolean validateEmail(String email) {
        matcher = mPattern.matcher(email);
        return matcher.matches();
    }

    public boolean validateName(String password) {
        return password.length() > 5;
    }

    private boolean validateLoginInput() {
        String email = mEditTextEmail.getText().toString();
        String name = mEditTextName.getText().toString();
        boolean validate = true;

        if (!validateName(name)) {
            mWrapperName.setError("Not a valid name!");
            validate = false;
        }
        if (!validateEmail(email)) {
            mWrapperEmail.setError("Not a valid email address!");
            validate = false;
        }
        if (validate) {
            mWrapperName.setErrorEnabled(false);
            mWrapperEmail.setErrorEnabled(false);
        }
        return validate;
    }

    private Contact getUser() {
        Contact contact = new Contact();
        contact.setName(mEditTextName.getText().toString());
        contact.setDescription("");
        Set<ContactInfo> contactInfoArrayList = new LinkedHashSet<>();
        ContactInfo contactInfo = new ContactInfo(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Email), "", mEditTextEmail.getText().toString());
        contactInfoArrayList.add(contactInfo);
        contact.setContactInfoArrayList(contactInfoArrayList);
        return contact;
    }

    private void saveUserProfile() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.saveContact(mContact);
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onPause() {
        super.onPause();
        mRequestQueue.cancelAll(TAG);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
