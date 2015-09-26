package com.kingbell.threads_downloadimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ImageView imgView;
    Button downloadButton;
    EditText imageURLEdit;
    LinearLayout loadingScreen = null;
    Bitmap bmp = null;
    ListView ls;
    ProgressBar pB;

    String url[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = (ImageView) findViewById(R.id.imageView);
        downloadButton = (Button) findViewById(R.id.downloadButton);
        ls = (ListView) findViewById(R.id.listView);
        pB = (ProgressBar) findViewById(R.id.downloadProgress);
        imageURLEdit = (EditText) findViewById(R.id.imageEdit);

        url = getResources().getStringArray(R.array.imageUrls);
        ls.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        imageURLEdit.setText(url[position]);
    }

    public void downloadImage(View view) {

        //If thread is created by extending thread
        //DownloadThread mythread = new DownloadThread();
        //mythread.start();

        Thread myThread = new Thread(new downloadThread());
        myThread.start();
        //myThread = new downloadThread();

    }
    public void downloadImageUsingThread(String url)
    {
        URL downloadUrl = null;
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try
        {
            downloadUrl= new URL(url);
            httpURLConnection= (HttpURLConnection) downloadUrl.openConnection();
            inputStream = httpURLConnection.getInputStream();
            int read = -1;
            byte[] byteArray = new byte[2024];
            while ((read=inputStream.read(byteArray))!=-1)
            {
                Log.e("Hello",""+read);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public class downloadThread implements Runnable
    {

        @Override
        public void run() {
            downloadImageUsingThread(url[0]);
        }
    }

}
