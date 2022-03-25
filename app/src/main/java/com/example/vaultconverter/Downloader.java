package com.example.vaultconverter;

import android.app.Activity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Downloader {
    private String json;
    private MainActivity mainActivity;
    private String url;

    public Downloader(MainActivity mainActivity, String url)
    {
        this.mainActivity = mainActivity;
        this.url = url;
    }

    public void download()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(()-> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            try {
                Response response = client.newCall(request).execute();
                json = response.body().string();

            } catch (Exception e) {
                json = e.toString();

            }

            mainActivity.runOnUiThread(() -> {
                mainActivity.onDownlaodFinished();
            });

        });
    }

    public String getJson()
    {
        return json;
    }
}
