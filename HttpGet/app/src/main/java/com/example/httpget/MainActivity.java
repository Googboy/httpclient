package com.example.httpget;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String,Void,Void>(){

                    @Override
                    protected Void doInBackground(String... params) {
                        try {
                            URL url = new URL(params[0]);
                            URLConnection connection = url.openConnection();//通过url.openConnection进行连接
                            InputStream is = connection.getInputStream();  //通过输入流读取数据（默认的是get方法）
                            InputStreamReader isr = new InputStreamReader(is,"utf-8");//把inputStream封装为inpputStreamReader,并把输入流加载进来。
                            BufferedReader br = new BufferedReader(isr);//读取一行数据
                            String line;
                            while ((line = br.readLine())!=null){            //读取当前一行的数据值,判断如果不为空，直接输出。
                                System.out.println(line);
                            }
                            br.close();
                            isr.close();
                            is.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute("http://fanyi.youdao.com/openapi.do?keyfrom=<keyfrom>&key=<key>&type=data&doctype=xml&version=1.1&q=good");
            }
        });
    }
}
