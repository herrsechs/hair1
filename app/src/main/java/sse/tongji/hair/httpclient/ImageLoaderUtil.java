package sse.tongji.hair.httpclient;
import android.content.Context;
import android.graphics.Bitmap;

import android.util.Log;
import android.util.LruCache;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;


/**
 * Created by LLLLLyj on 2015/9/9.
 */
public class ImageLoaderUtil {
    protected ImageLoader imageLoader;
    protected LruCache<String,Bitmap> mMemoryCache;

    private final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
    private final int cacheSize = maxMemory/4;

    public ImageLoaderUtil(Context context){
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .build();
        imageLoader.init(config);
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    protected void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    protected void loadImageToCache(final int imageID, final String gender, final String haircutType){
//        try {
        //String imageUri = "http://123.56.148.119/upload/download/" + gender + "/" + haircutType  + "/" + imageID + "/";
        String imageUri = "http://123.56.148.119/upload/download/" + imageID + "/";
        ImageSize targetSize = new ImageSize(80, 50);
        if(getBitmapFromMemCache(imageID+"") == null) {
            imageLoader.loadImage(imageUri, targetSize, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Log.v("Loading Complete! " + imageID, "load");
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    addBitmapToMemoryCache("" + (imageID - 1), loadedImage);
                }

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    super.onLoadingStarted(imageUri, view);
                }
            });
        }
//        }catch (Exception e){
//            Log.v("get Failed!","Fail!!!!!!!!!");
//        }
    }


    public void loadImage(int[] imageIDs, String gender, String haircutType){
        for(int i : imageIDs){
            this.loadImageToCache(i, gender, haircutType);
        }
    }


}
