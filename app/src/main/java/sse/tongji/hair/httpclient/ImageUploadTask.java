package sse.tongji.hair.httpclient;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by LLLLLyj on 2015/9/6.
 */
public class ImageUploadTask extends AsyncTask<File, Integer, Boolean> {
    private static final String TAG = "upload_file";
    private static final int TIME_OUT = 10 * 10000000;
    private static final String CHARSET = "utf-8";
    private static final String SUCCESS = "1";
    private static final String FAILURE = "0";

    protected Boolean doInBackground(File... params){
        try{
            //uploadFile(params[0]);
            FTPUploader ftpUploader = new FTPUploader();
            InputStream inputStream = new FileInputStream(params[0]);
            Boolean result = ftpUploader.uploadFile("/home/clouddata", "test2.jpg", inputStream);
            if(result){
                Log.e(TAG, "Done uploading image");
            }else{
                Log.e(TAG, "Fail to upload image");
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    protected static String uploadFile(File file){
        String RequestURL = "http://123.56.148.119/upload";
        String BOUNDARY = "-----------------------------7df118b603ec";
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";
        try{
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            //InputStream i = new FileInputStream(file);
            //conn.setRequestProperty("Content-Length", String.valueOf(i.available() + 286));

            if(file != null){
                OutputStream outputStream = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputStream);
                StringBuffer stringBuffer = new StringBuffer();
                //stringBuffer.append(PREFIX);
                stringBuffer.append(BOUNDARY);
                stringBuffer.append(LINE_END);
                stringBuffer.append("Content-Disposition: form-data; name=\"username\"; " + LINE_END + LINE_END + "aaa" + LINE_END);
                stringBuffer.append(BOUNDARY + LINE_END);
                stringBuffer.append("Content-Disposition: form-data; name=\"Img\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                stringBuffer.append("Content-Type: image/jpeg" + LINE_END);
                stringBuffer.append(LINE_END);

                Log.e(TAG, stringBuffer.toString());

                dos.write(stringBuffer.toString().getBytes());

                InputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[1024 * 3];
                int len = 0;
                while( (len=inputStream.read(bytes)) != -1){
                    dos.write(bytes, 0, len);
                }
                inputStream.close();

                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();


                int res = conn.getResponseCode();
                Log.e(TAG, "response code:" + res);
                if(res == 200){
                    return SUCCESS;
                }
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(NetworkOnMainThreadException e){
            e.printStackTrace();
        }
        return FAILURE;
    }
}
