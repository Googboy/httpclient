package com.example.httppost;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String,Void,Void>(){//新开一条线程处理耗时操作，以免造成卡死状态

                    @Override
                    protected Void doInBackground(String... params) {
                        try {
                            URL url = new URL(params[0]);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//通过url的openConection进行连接
                            connection.setDoInput(true);//写不写都可以
                            connection.setDoOutput(true);
                            connection.setRequestMethod("POST");//特别指定方式为post，因为默认为get方法
                            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
                            BufferedWriter bw = new BufferedWriter(osw);
                            bw.write("keyfrom=<keyfrom>&key=<key>&type=data&doctype=xml&version=1.1&q=good");
                            bw.flush();//强制输出
                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is,"utf-8");
                            BufferedReader reader = new BufferedReader(isr);
                            String line;
                            while ((line = reader.readLine())!=null){
                                System.out.println(line);
                            }
                            reader.close();
                            isr.close();
                            is.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute("http://fanyi.youdao.com/openapi.do");
            }
        });
    }
}
