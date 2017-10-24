package com.example.clientdownload;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    ImageView image;
    Button btnSave,btnShow;
    URL url;
    Bitmap bitmap;
    HttpURLConnection httpURLConnection;
    InputStream is;
    FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.imageView);
        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(run).start();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(run2).start();
            }
        });
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            String urlName = "http://localhost:8080/Test/1.jpg";
            try {
                url = new URL(urlName);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200){
                    is = httpURLConnection.getInputStream();
                }
                bitmap = BitmapFactory.decodeStream(is);
                Log.i("测试:",bitmap+"");
                handle.sendEmptyMessage(0);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (is!=null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
    Runnable run2 = new Runnable() {
        @Override
        public void run() {
            String urlname = "http://localhost:8080/Test/1.jpg";
            try {
                url = new URL(urlname);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200){
                    is = httpURLConnection.getInputStream();
                }
                File file = new File("test/");
                file.mkdir();
                fos = new FileOutputStream("test/1.jpg");
                byte[] date = new byte[1024];
                int length;
                while ((length = is.read(date))!=-1){
                    fos.write(date,0,length);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
    StringBuffer sb;
    Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            image.setImageBitmap(bitmap);
        }
    };
}
