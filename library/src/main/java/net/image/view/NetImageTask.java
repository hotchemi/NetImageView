package net.image.view;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class NetImageTask implements Runnable {

    private static final int BITMAP_READY = 0;

    private boolean cancelled;

    private OnCompleteHandler onCompleteHandler;

    private Image image;

    public abstract static class OnCompleteListener {
        public abstract void onComplete();
    }

    public NetImageTask(final Image image) {
        this.image = image;
    }

    @Override
    public void run() {
        if (image != null) complete(image.getBitmap());
    }

    public void setOnCompleteHandler(OnCompleteHandler handler) {
        onCompleteHandler = handler;
    }

    public void cancel() {
        cancelled = true;
    }

    public void complete(final Bitmap bitmap) {
        if (onCompleteHandler != null && !cancelled) {
            Message message = onCompleteHandler.obtainMessage(BITMAP_READY, bitmap);
            onCompleteHandler.sendMessage(message);
        }
    }

    public static abstract class OnCompleteHandler extends Handler {

        @Override
        public void handleMessage(final Message message) {
            Bitmap bitmap = (Bitmap) message.obj;
            onComplete(bitmap);
        }

        public abstract void onComplete(final Bitmap bitmap);

    }

}