package sse.tongji.hair.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sse.tongji.hair.R;

/**
 * Created by LLLLLyj on 2015/9/6.
 */
public class SplashActivity extends AppCompatActivity {
    private static final int FAILURE = 0;
    private static final int SUCCESS = 1;
    private static final int OFFLINE = 2;
    private static final int MIN_SHOWTIME = 800;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.splash);

        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void...params){
                long start = System.currentTimeMillis();

                int result;
                result = loadingCache();

                long loading = System.currentTimeMillis() - start;
                if(loading < MIN_SHOWTIME){
                    try{
                        Thread.sleep(MIN_SHOWTIME - loading);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(Integer result){
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, PersonCenterActivity.class);
                startActivity(intent);
                finish();
            }
        }.execute(new Void[]{});
    }

    protected Integer loadingCache(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm != null){
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if(infos != null){
                for(NetworkInfo ni : infos){
                    if(ni.isConnected())
                        return SUCCESS;
                }
            }
        }
        return OFFLINE;
    }
}
