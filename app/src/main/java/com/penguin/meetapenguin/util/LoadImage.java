package com.penguin.meetapenguin.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.penguin.meetapenguin.exceptions.BitmapException;

/**
 * Created by urbano on 4/2/16.
 */
public class LoadImage {

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        if (res == null || resId == 0 || reqWidth <= 0 || reqHeight <= 0) {
            try {
                throw new BitmapException("Bitmap input invalid");
            } catch (BitmapException e) {
                Log.d("MeetAPenguin", e.getMessage());
            }
        }

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        if (options == null || reqWidth <= 0 || reqHeight <= 0) {
            try {
                throw new BitmapException("Bitmap input invalid");
            } catch (BitmapException e) {
                Log.d("MeetAPenguin", e.getMessage());
            }
        }

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
