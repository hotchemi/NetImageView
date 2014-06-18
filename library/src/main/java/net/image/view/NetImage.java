package net.image.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class NetImage implements Image {

    private static final String TAG = NetImage.class.getSimpleName();

    private static final int CONNECT_TIMEOUT = 5000;

    private static final int READ_TIMEOUT = 10000;

    private static ImageCache imageCache;

    private String url;

    public NetImage(final Context context, final String url) {
        this.url = url;
        // Don't leak context
        if (imageCache == null) {
            imageCache = new ImageCache(context);
        }
    }

    @Override
    public Bitmap getBitmap() {
        // Try getting bitmap from cache first
        Bitmap bitmap = null;
        if (url != null) {
            bitmap = imageCache.get(url);
            if (bitmap == null) {
                bitmap = getBitmapFromUrl(url);
                if (bitmap != null) {
                    imageCache.put(url, bitmap);
                }
            }
        }
        return bitmap;
    }

    private Bitmap getBitmapFromUrl(final String url) {
        Bitmap bitmap = null;
        try {
            final URLConnection connection = new URL(url).openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            bitmap = BitmapFactory.decodeStream((InputStream) connection.getContent());
        } catch (final Exception e) {
            Log.d(TAG, e.toString());
        }
        return bitmap;
    }

}