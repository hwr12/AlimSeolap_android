package com.whysly.alimseolap1.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CsvFile {


public static void main(String[]args){
PrintWriter pw = null;

try {
    pw = new PrintWriter(new File("NewData.csv"));
} catch (FileNotFoundException e) {
    e.printStackTrace();
}
StringBuilder builder = new StringBuilder();
String ColumnNamesList = "Id,Name";
// No need give the headers Like: id, Name on builder.append
builder.append(ColumnNamesList +"\n");
builder.append("1"+",");
builder.append("Chola");
builder.append('\n');
pw.write(builder.toString());
pw.close();
System.out.println("done!");
}
}