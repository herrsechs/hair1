package sse.tongji.hair.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import sse.tongji.hair.R;
import sse.tongji.hair.adapter.HaircutItemAdapter;
import sse.tongji.hair.adapter.WelcomeAdapter;
import sse.tongji.hair.httpclient.ImageUploadTask;
import sse.tongji.hair.listener.RecyclerItemClickListener;

/**
 * Created by lenovo on 2015/8/25.
 */
public class PersonCenterActivity extends AppCompatActivity{
    private int itemPos = 0;

    public static final int CODE_GALLERY_REQUEST = 0xa0;
    public static final int CODE_CAMERA_REQUEST = 0xa1;
    public static final int CODE_RESULT_REQUEST = 0xa2;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public RecyclerView welcomeRecyclerView;
    public RecyclerView haircutRecyclerView;
    public FloatingActionButton plusFAB;
    public Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private String[] lvs = {"center", "recommend", "info", "settings"};
    public HaircutItemAdapter hAdapter;
    public ImageView selfImage;
    public ImageView testImage;
    public SelectPicPopUpWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_menu);
        //welcomeRecyclerView = (RecyclerView)findViewById(R.id.welcome_recycler_view);
        //welcomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //welcomeRecyclerView.setAdapter(new WelcomeAdapter(this));

        haircutRecyclerView = (RecyclerView)findViewById(R.id.haircut_recycler_view);
        haircutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hAdapter = new HaircutItemAdapter(this);
        haircutRecyclerView.setAdapter(hAdapter);

        /**
         * Invoke addOnItemTouchListener() to listen to the recycler view item click event
         * Implement RecyclerItemClickListener in Listener package
         */
        haircutRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.onItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                itemPos = position;
                //Log.d("Debug", "Clicked " + String.valueOf(position));
            }
        }));

        selfImage = (ImageView)findViewById(R.id.self_image);
        //testImage = (ImageView)findViewById(R.id.testImage);

        plusFAB = (FloatingActionButton)findViewById(R.id.haircut_fab);
        plusFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuWindow = new SelectPicPopUpWindow(PersonCenterActivity.this, popUpListener);
                menuWindow.showAtLocation(PersonCenterActivity.this.findViewById(R.id.dl_left), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);
                //choseHeadImageFromGallery();
                //chooseHeadImageFromCamera();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);

        toolbar = (Toolbar)findViewById(R.id.tl_custom);
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // mAnimationDrawable.stop();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //mAnimationDrawable.start();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_delete:
                hAdapter.deletaData(itemPos);
                break;
            /*
            case R.id.action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));

                break;
            case R.id.action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.action_staggered:

                Intent intent = new Intent(this,StaggeredGridLayoutActivity.class);
                startActivity(intent);
                break;
            */
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    private void chooseHeadImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.setType("image/*");
        //intent.setAction(Intent.ACTION_CAMERA_BUTTON);
        //String fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE).toString(); // create a file to save the image
        //intent.putExtra("ImageName", fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CODE_CAMERA_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //Bundle bundle = getIntent().getExtras();
        //Log.d("Debug", "Uri: " + bundle.getString("ImageName"));
        if(requestCode == CODE_GALLERY_REQUEST){
            if(data == null){
                Log.d("Debug", "Gallery image is empty!");
                return;
            }
            try {
                File image = new File(getRealFilePath(this, data.getData()));
                //ImageUploadTask iut = new ImageUploadTask();
                //iut.execute(image);
                Log.d("Debug", image.getPath());
                if(null == selfImage){
                    Log.d("Debug", "null self image");
                }else {

                    String path = getRealFilePath(this, data.getData());
                    //BitmapFactory.Options opt = new BitmapFactory.Options();
                    //opt.inJustDecodeBounds = true;
                    //Bitmap bmp  = BitmapFactory.decodeFile(getRealFilePath(this, data.getData()), opt);
                    Bitmap bmp = getPhotoBitmap(path);
                    try{
                        //Log.d("Debug", "I got here");
                        selfImage.setImageBitmap(bmp);
                    }catch(OutOfMemoryError error){
                        Log.e("Debug", "Image out of memory");
                        error.printStackTrace();
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }else if(requestCode == CODE_CAMERA_REQUEST){
            if(data == null){
                Log.d("Debug", "Camera image is empty!");
                return;
            }else {
                //File image = new File(getRealFilePath(this, data.getData()));
                if(selfImage == null){
                    Log.d("Debug", "Self image is null");
                }else{
                    //selfImage.setImageURI(data.getData());
                    String path = getRealFilePath(this, data.getData());
                    Bitmap bmp = getPhotoBitmap(path);
                    try{
                        selfImage.setImageBitmap(bmp);
                    }catch(OutOfMemoryError error){
                        Log.e("Debug", "Image out of memory");
                        error.printStackTrace();
                    }
                }
            }
        }
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()) if (!mediaStorageDir.mkdirs()) {
            Log.d("MyCameraApp", "failed to create directory");
            return null;
        }

        // Create a media file name
        String timeStamp = new Date().toString();
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public Bitmap getPhotoBitmap(String path){
        Bitmap bmp = BitmapFactory.decodeFile(path);
        int height = (int) ( bmp.getHeight() * (512.0 / bmp.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bmp, 512, height, true);
        return scaled;
    }

    public View.OnClickListener popUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            menuWindow.dismiss();
            switch (view.getId()){
                case R.id.popup_pick_from_gallery:
                    choseHeadImageFromGallery();
                    break;
                case R.id.popup_pick_from_photo:
                    chooseHeadImageFromCamera();
                    break;
                default:
                    break;
            }
        }
    };
}

