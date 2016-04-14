package com.penguin.meetapenguin.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.penguin.meetapenguin.exceptions.ShareException;
import com.squareup.picasso.Picasso;

import java.util.EnumMap;
import java.util.Map;

public class ShareActivity extends AppCompatActivity {

    private static final int WHITE = 0xFF44B4D9;
    private static final int BLACK = 0xFF000000;
    private ImageView qrCodeImageView;
    private TextView name;
    private TextView description;
    private CircularImageView imageProfile;
    private Button editShare;

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        ShareActivityIntent shareActivityIntent = null;
        if (savedInstanceState != null) {
            shareActivityIntent = savedInstanceState.getParcelable(ShareActivityIntent.ShareActivityIntentBundle);
        } else {
            shareActivityIntent = getIntent().getExtras().getParcelable(ShareActivityIntent.ShareActivityIntentBundle);
        }

        if (shareActivityIntent == null) {
            try {
                throw new ShareException("You should give a contact object to share");
            } catch (ShareException e) {
                Log.d("MeetAPenguin", e.getMessage());
            }
        }
        Contact contact = shareActivityIntent.getContact();

        qrCodeImageView = (ImageView) findViewById(R.id.qrcode);
        ViewGroup view = (ViewGroup) findViewById(R.id.activity_container);
        try {
            Bitmap bitmap = encodeAsBitmap("testando", BarcodeFormat.QR_CODE, 900, 600);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        RelativeLayout toolbar = (RelativeLayout) getLayoutInflater().inflate(R.layout.share_activity_toolbar, view);
        name = (TextView) toolbar.findViewById(R.id.name);
        description = (TextView) toolbar.findViewById(R.id.description);

        name.setText(contact.getName());
        description.setText(contact.getDescription());
        imageProfile = (CircularImageView) toolbar.findViewById(R.id.profile_picture);
        Picasso.with(this)
                .load(contact.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(imageProfile);

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
