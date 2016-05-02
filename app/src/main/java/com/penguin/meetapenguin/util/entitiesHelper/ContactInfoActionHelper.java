package com.penguin.meetapenguin.util.entitiesHelper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.penguin.meetapenguin.entities.ContactInfo;

/**
 * Created by mitayun on 5/1/16.
 */
public class ContactInfoActionHelper {

    public static Intent getLaunchIntent(Context context, ContactInfo contactInfo) {
        String attributeName = contactInfo.getAttribute().getName();
        if (attributeName.equals("Facebook")) {
            String url = "https://www.facebook.com/" + contactInfo.getAttributeValue();
            Uri uri = Uri.parse(url);
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
                if (applicationInfo.enabled) {
                    uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                }
            } catch (PackageManager.NameNotFoundException ignored) {
            }
            android.util.Log.d("MITA", uri.toString());
            return new Intent(Intent.ACTION_VIEW, uri);
        } else if (attributeName.equals("Email")) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", contactInfo.getAttributeValue(), null));
            return Intent.createChooser(emailIntent, "Send email...");
        } else if (attributeName.equals("Website")) {
            Uri uri = Uri.parse(contactInfo.getAttributeValue());
            return new Intent(Intent.ACTION_VIEW, uri);
        } else if (attributeName.equals("Phone Number")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contactInfo.getAttributeValue().trim(), null));
            return intent;
        }
        return null;
    }
}
