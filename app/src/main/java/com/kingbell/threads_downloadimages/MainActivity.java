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
    LinearLayout loadingScreen =null;
    Bitmap bmp =null;
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
        Thread myThread = new Thread(new DownloadThread());
        myThread.start();
        //If thread is created by extending thread
//        DownloadThread mythread = new DownloadThread();
//        mythread.start();

    }

    public class DownloadThread implements Runnable {
        @Override
        public void run() {
            downloadImageInto(url[0]);
        }
    }

    public void downloadImageInto(String url)
    {
        URL downloadU = null;
        HttpURLConnection connection =null;
        InputStream inputStream=null;
        try {
             downloadU= new URL(url);
             connection= (HttpURLConnection) downloadU.openConnection();
             inputStream = connection.getInputStream();
            bmp = BitmapFactory.decodeStream(inputStream);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("hi","set");
                    imgView.setImageBitmap(bmp);
                }
            });

            int read = -1;
            while ((read=inputStream.read())!=-1)
            {
                Log.e("Chumma","Ennamma  "+read);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
            if(inputStream!=null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
