package com.whysly.alimseolap1.Util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

public class CsvTry {
       //public static String abc=null;



    String toPath = "/data/data//data/data/com.whysly.alimseolap1";  // Your application path
    public CsvTry(Context context){
        File myDir = new File(context.getCacheDir(), "libs");
        myDir.mkdir();
        copyAssets(context);
    }


    private void copyAssets(Context context) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {

                if(filename.equals("d3.layout.cloud.js")){
                    in = assetManager.open(filename);
                    File outFile = new File("/data/data/com.whysly.alimseolap1/cache/libs", filename);
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);

                } else if (filename.equals("d3.v3.min.js") ){
                    in = assetManager.open(filename);
                    File outFile = new File("/data/data/com.whysly.alimseolap1/cache/libs", filename);
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);

                } else{
                    in = assetManager.open(filename);
                File outFile = new File("/data/data/com.whysly.alimseolap1/cache", filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                }
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }


       public static void generateCsvFile()
       {
           try
           {
               //File root = Environment.getExternalStorageDirectory();
               //File gpxfile = new File(root, sFileName);
               //FileWriter writer = new FileWriter(gpxfile);
               Writer fw=new FileWriter("/data/data/com.whysly.alimseolap1/words.csv");
               Writer fileWriter = new FileWriter("/data/data/com.whysly.alimseolap1/words.csv", false); //overwrites file

               //abc="Guwahati";

               fileWriter.append("word");
               fileWriter.append(',');
               fileWriter.append("freq");
               fileWriter.append('\n');

               fileWriter.append("안드로이드");
               fileWriter.append(',');
               fileWriter.append("10");
               fileWriter.append('\n');


               fileWriter.append("알림서랍");
               fileWriter.append(',');
               fileWriter.append("5");
               fileWriter.append('\n');

               fileWriter.append("유현우");
               fileWriter.append(',');
               fileWriter.append("3");
               fileWriter.append('\n');


               fileWriter.append("개발자");
               fileWriter.append(',');
               fileWriter.append("7");
               fileWriter.append('\n');

               fileWriter.append("여행");
               fileWriter.append(',');
               fileWriter.append("4");
               fileWriter.append('\n');


               fileWriter.append("한국");
               fileWriter.append(',');
               fileWriter.append("8");
               fileWriter.append('\n');

               fileWriter.append("초밥");
               fileWriter.append(',');
               fileWriter.append("5");
               fileWriter.append('\n');


               fileWriter.append("치킨");
               fileWriter.append(',');
               fileWriter.append("3");
               fileWriter.append('\n');


               fileWriter.append("치맥");
               fileWriter.append(',');
               fileWriter.append("1");
               fileWriter.append('\n');

               //generate whatever data you want

               fileWriter.flush();
               fileWriter.close();
           }
           catch(IOException e)
           {
               e.printStackTrace();
           } 
       }
}