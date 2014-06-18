package net.image.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetImageView extends ImageView {

    private static final int LOADING_THREADS = 4;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(LOADING_THREADS);

    private NetImageTask currentTask;

    public NetImageView(Context context) {
        super(context);
    }

    public NetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageUrl(String url) {
        setImage(new NetImage(getContext(), url));
    }

    public void setImageUrl(String url, NetImageTask.OnCompleteListener completeListener) {
        setImage(new NetImage(getContext(), url), completeListener);
    }

    public void setImageUrl(String url, final int fallbackResource) {
        setImage(new NetImage(getContext(), url), fallbackResource);
    }

    public void setImageUrl(String url, final int fallbackResource, NetImageTask.OnCompleteListener completeListener) {
        setImage(new NetImage(getContext(), url), fallbackResource, completeListener);
    }

    public void setImageUrl(String url, final int fallbackResource, final int loadingResource) {
        setImage(new NetImage(getContext(), url), fallbackResource, loadingResource);
    }

    public void setImageUrl(String url, final int fallbackResource, final int loadingResource, NetImageTask.OnCompleteListener completeListener) {
        setImage(new NetImage(getContext(), url), fallbackResource, loadingResource, completeListener);
    }

    public void setImage(final Image image) {
        setImage(image, null, null, null);
    }

    public void setImage(final Image image, final NetImageTask.OnCompleteListener completeListener) {
        setImage(image, null, null, completeListener);
    }

    public void setImage(final Image image, final int fallbackResource) {
        setImage(image, fallbackResource, fallbackResource, null);
    }

    public void setImage(final Image image, final int fallbackResource, NetImageTask.OnCompleteListener completeListener) {
        setImage(image, fallbackResource, fallbackResource, completeListener);
    }

    public void setImage(final Image image, final int fallbackResource, final int loadingResource) {
        setImage(image, fallbackResource, loadingResource, null);
    }

    public void setImage(final Image image, final Integer fallbackResource, final Integer loadingResource, final NetImageTask.OnCompleteListener completeListener) {
        // Set a loading resource
        if (loadingResource != null) {
            setImageResource(loadingResource);
        }

        // Cancel any existing tasks for this image view
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }

        // Set up the new task
        currentTask = new NetImageTask(image);
        currentTask.setOnCompleteHandler(new NetImageTask.OnCompleteHandler() {
            @Override
            public void onComplete(Bitmap bitmap) {
                if (bitmap != null) {
                    setImageBitmap(bitmap);
                } else {
                    // Set fallback resource
                    if (fallbackResource != null) {
                        setImageResource(fallbackResource);
                    }
                }

                if (completeListener != null) {
                    completeListener.onComplete();
                }
            }
        });

        // Run the task in a threadpool
        threadPool.execute(currentTask);
    }

    public static void cancelAllTasks() {
        threadPool.shutdownNow();
        threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
    }

}