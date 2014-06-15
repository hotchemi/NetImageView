package net.image.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class NetImageTask implements Runnable {

    private static final int BITMAP_READY = 0;

    private boolean cancelled = false;

    private OnCompleteHandler onCompleteHandler;

    private Image image;

    public static class OnCompleteHandler extends Handler {

        @Override
        public void handleMessage(final Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            onComplete(bitmap);
        }

        public void onComplete(Bitmap bitmap) {
        }

    }

    public abstract static class OnCompleteListener {
        public abstract void onComplete();
    }

    public NetImageTask(final Context context, final Image image) {
        this.image = image;
    }

    @Override
    public void run() {
        if (image != null) {
            complete(image.getBitmap());
        }
    }

    public void setOnCompleteHandler(OnCompleteHandler handler) {
        this.onCompleteHandler = handler;
    }

    public void cancel() {
        cancelled = true;
    }

    public void complete(Bitmap bitmap) {
        if (onCompleteHandler != null && !cancelled) {
            onCompleteHandler.sendMessage(onCompleteHandler.obtainMessage(BITMAP_READY, bitmap));
        }
    }

}