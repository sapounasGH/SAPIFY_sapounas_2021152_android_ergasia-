package com.example.sapounas22exp;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
public class FileManager {

    public static String saveuriTointernalStorage(Context context , Uri uri , String filename){
        ContentResolver contentResolver =context.getContentResolver();
        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(uri);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        byte[] buffer = new byte[20971520];
        int bytedRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] byteArray;
        while (true){
            try {
                if ((bytedRead = inputStream.read(buffer))!=-1){
                    output.write(buffer, 0, bytedRead);
                }
                byteArray = output.toByteArray();
                inputStream.close();



            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            File file = new File(context.getFilesDir(), filename);
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                try {
                    outputStream.write(byteArray);
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            return file.getAbsolutePath();

        }

    }

}
