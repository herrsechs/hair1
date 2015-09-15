package sse.tongji.hair.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;


import java.io.File;
import java.io.FileNotFoundException;

import sse.tongji.hair.R;
import sse.tongji.hair.adapter.HaircutItemAdapter;
import sse.tongji.hair.adapter.WelcomeAdapter;
import sse.tongji.hair.httpclient.ImageUploadTask;

/**
 * Created by lenovo on 2015/8/25.
 */
public class PersonCenterActivity extends AppCompatActivity{
    private int itemPos = 0;

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    public RecyclerView welcomeRecyclerView;
    public RecyclerView haircutRecyclerView;
    public FloatingActionButton plusFAB;
    public Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private String[] lvs = {"center", "recommend", "info", "settings"};
    public HaircutItemAdapter hAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_menu);
        welcomeRecyclerView = (RecyclerView)findViewById(R.id.welcome_recycler_view);
        welcomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        welcomeRecyclerView.setAdapter(new WelcomeAdapter(this));

        haircutRecyclerView = (RecyclerView)findViewById(R.id.haircut_recycler_view);
        haircutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hAdapter = new HaircutItemAdapter(this);
        haircutRecyclerView.setAdapter(hAdapter);
        hAdapter.setOnItemClickListener(new HaircutItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                itemPos = position;
            }

            @Override
            public void onItemLongClick(View view, int position) {
                itemPos = position;
            }
        });

        plusFAB = (FloatingActionButton)findViewById(R.id.haircut_fab);
        plusFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_GALLERY_REQUEST){
            if(data == null){
                Log.d("Debug", "Gallery image is empty!");
                return;
            }
            try {
                File image = new File(getRealFilePath(this, data.getData()));
                /*
                RequestParams params = new RequestParams();
                params.put("Img", image);
                params.put("username", "bigbang");

                AliCloudClient.get("upload", null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("Debug", "Http Succeed");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("Debug", error.toString());
                    }
                });
                */
                /*
                AliCloudClient.post("upload", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("Debug","Done uploading image.");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("Debug",error.toString());
                    }
                });
                */
                ImageUploadTask iut = new ImageUploadTask();
                iut.execute(image);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
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
}

