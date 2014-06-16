package net.image.view;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class NetImageTask implements Runnable {

    private static final int BITMAP_READY = 0;

    private boolean mCancelled;

    private OnCompleteHandler mOnCompleteHandler;

    private Image mImage;

    public abstract static class OnCompleteListener {
        public abstract void onComplete();
    }

    public NetImageTask(final Image image) {
        mImage = image;
    }

    @Override
    public void run() {
        if (mImage != null) complete(mImage.getBitmap());
    }

    public void setmOnCompleteHandler(OnCompleteHandler handler) {
        mOnCompleteHandler = handler;
    }

    public void cancel() {
        mCancelled = true;
    }

    public void complete(final Bitmap bitmap) {
        if (mOnCompleteHandler != null && !mCancelled) {
            Message message = mOnCompleteHandler.obtainMessage(BITMAP_READY, bitmap);
            mOnCompleteHandler.sendMessage(message);
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