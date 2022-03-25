package com.example.vaultconverter;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Parser {
    int count = 0;
    String[] charCodes = new String[64];
    String[] values = new String[64];
    MainActivity mainActivity;

    public Parser(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    void parse(String json)
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            ArrayList<String> words = new ArrayList<String>();
            int j = 0;
            for (int i = 0; i < json.length(); i++)
            {
                if (json.charAt(i) == '"')
                {
                    words.add(json.substring(j, i + 1));
                    j = i;
                }
            }

            Log.w("WORDS", words.toString());

            for (int i = 0; i < words.size(); i++)
            {
                if (words.get(i).equals("\"CharCode\""))
                {
                    Log.w("found", words.get(i));
                    charCodes[count] = words.get(i + 2).substring(1, 4);

                    String value = words.get(i + 11);
                    value = value.substring(2, value.length() - 2);

                    values[count] = value;
                    count = count + 1;
                }
            }


            mainActivity.runOnUiThread(()->{
                mainActivity.onParsingFinished();
            });
        });
    }

    String[] getCharCodes()
    {
        Log.w("charCodes", charCodes.toString());
        return charCodes;
    }

    String[] getValues()
    {
        Log.w("Values", values.toString());
        return values;
    }

    int getCount()
    {
        Log.w("Count", Integer.toString(count));
        return count;
    }
}
