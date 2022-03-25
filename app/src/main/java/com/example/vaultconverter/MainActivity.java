package com.example.vaultconverter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class MainActivity extends Activity {

    TextView textView;
    Downloader downloader;
    Parser parser;

    protected void init()
    {
        textView = findViewById(R.id.text_view);
        downloader = new Downloader(this, getString(R.string.link));
        parser = new Parser(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        downloader.download();
    }

    protected void onDownlaodFinished()
    {
        String json = downloader.getJson();
        //textView.setText(json);
        parser.parse(json);
    }

    protected void onParsingFinished()
    {
        String result = "";
        String[] charCodes = parser.getCharCodes();
        String[] values = parser.getValues();

        for (int i = 0; i < parser.getCount(); i++)
        {
            result = result + charCodes[i] + "   " + values[i] + "\n";
        }

        textView.setText(result);
    }
}
