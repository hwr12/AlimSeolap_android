package com.whysly.alimseolap1.Util;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVedit {

    public static String abc=null;

        private static void generateCsvFile(String sFileName)
        {
            try
            {
                File root = Environment.getExternalStorageDirectory();
                File gpxfile = new File(root, sFileName);
                FileWriter writer = new FileWriter(gpxfile);
                abc="Guwahati";
                writer.append("Emp_Name");
                writer.append(',');
                writer.append("Adress");
                writer.append('\n');
                writer.append("hussain");
                writer.append(',');
                writer.append("Delhi");
                writer.append('\n');

                //generate whatever data you want

                writer.flush();
                writer.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

