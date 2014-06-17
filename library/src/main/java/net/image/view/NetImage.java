package net.image.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class NetImage implements Image {

    private static final int CONNECT_TIMEOUT = 5000;

    private static final int READ_TIMEOUT = 10000;

    private static NetImageCache imageCache;

    private String url;

    private Context context;

    public NetImage(final Context context, final String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    public Bitmap getBitmap() {
        // Don't leak context
        if (imageCache == null) {
            imageCache = new NetImageCache(context);
        }

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

    private Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            bitmap = BitmapFactory.decodeStream((InputStream) conn.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void removeFromCache(String url) {
        if (imageCache != null) {
            imageCache.remove(url);
        }
    }

}