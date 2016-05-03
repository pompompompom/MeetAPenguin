package com.penguin.meetapenguin.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.util.entitiesHelper.ContactHelper;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.location.LocationManager.GPS_PROVIDER;

public class ShareActivity extends AppCompatActivity {

    private static final int WHITE = 0xFF44B4D9;
    private static final int BLACK = 0xFF000000;
    private ImageView qrCodeImageView;
    private TextView name;
    private TextView description;
    private CircularImageView imageProfile;
    private Button editShare;
    private ViewGroup view;
    private RelativeLayout toolbar;

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    private String getZipCode() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(GPS_PROVIDER);

        // Get zipcode
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        if (geocoder != null && location != null) {
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                for (Address address : addresses) {
                    if (address.getLocality() != null && address.getPostalCode() != null) {
                        android.util.Log.d("MITA", "zip: " + address.getPostalCode());
                        return address.getPostalCode();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "94043";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Contact contact = (Contact) getIntent().getSerializableExtra("Contact");
        contact.setZipCode(getZipCode());

        android.util.Log.d("MITA", "contact name: " + contact.getName());
        Log.d("MITA", "!!!!!!!!!Sharing contact: " + ContactHelper.toJson(contact));

        qrCodeImageView = (ImageView) findViewById(R.id.qrcode);
        view = (ViewGroup) findViewById(R.id.activity_container);

        String contactJson = ContactHelper.toJson(contact);
        try {
            Bitmap bitmap = encodeAsBitmap(contactJson, BarcodeFormat.QR_CODE, 1350, 900);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        toolbar = (RelativeLayout) getLayoutInflater().inflate(R.layout.share_activity_toolbar, view);
        name = (TextView) toolbar.findViewById(R.id.name);
        description = (TextView) toolbar.findViewById(R.id.description);

        name.setText(contact.getName());
        description.setText(contact.getDescription());
        imageProfile = (CircularImageView) toolbar.findViewById(R.id.profile_picture);

        int photoID = 0;
        try {
            photoID = Integer.parseInt(contact.getPhotoUrl());
        } catch (Exception e) {
            photoID = 0;
        }

        if (photoID != 0) {
            imageProfile.setImageDrawable(this
                    .getResources()
                    .getDrawable(photoID));
        } else {
            Picasso.with(this)
                    .load(contact.getPhotoUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(imageProfile);
        }

        editShare = (Button) findViewById(R.id.edit_share_button);
        editShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
