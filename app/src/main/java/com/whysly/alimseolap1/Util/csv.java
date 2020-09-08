package com.whysly.alimseolap1.Util;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class csv {
       public static String abc=null;


       private static void generateCsvFile(String sFileName)
       {
           try
           {
               File root = Environment.getExternalStorageDirectory();
               File gpxfile = new File(root, sFileName);
               FileWriter writer = new FileWriter(gpxfile);
               Writer fw=new FileWriter("/data/data/com.whysly.alimseolap1/databases/words.csv");
               Writer fileWriter = new FileWriter("/data/data/com.whysly.alimseolap1/databaseswords.csv", false); //overwrites file
               
               abc="Guwahati";

               fileWriter.append("Emp_Name");
               fileWriter.append(',');
               fileWriter.append("Adress");
               fileWriter.append('\n');

               fileWriter.append("hussain");
               fileWriter.append(',');
               fileWriter.append("Delhi");
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