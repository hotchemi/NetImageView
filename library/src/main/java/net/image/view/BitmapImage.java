package net.image.view;

import android.graphics.Bitmap;

public class BitmapImage implements Image {

    private final Bitmap bitmap;

    public BitmapImage(final Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

}