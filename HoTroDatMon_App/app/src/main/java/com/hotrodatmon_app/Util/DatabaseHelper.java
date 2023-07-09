package com.hotrodatmon_app.Util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper {

    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DATABASE_NAME = "QL_NhaHang_Android.db";
    private SQLiteDatabase database;
    private Context mContext;

    public void openDatabase(Context context) {
        String databasePath = getDatabasePath(context);
        database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public  SQLiteDatabase getData()
    {
        return database;
    }

    public void closeDatabase() {
        if (database != null) {
            database.close();
        }
    }

    private String getDatabasePath(Context context) {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public boolean checkDatabaseExists(Context context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    public void copyDatabaseFromAssets(Context context) throws IOException {
        if (checkDatabaseExists(context)) {
            // Cơ sở dữ liệu đã tồn tại, không cần sao chép từ assets
            return;
        }

        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            String outFileName = getDatabasePath(context);
            File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            myInput = context.getAssets().open(DATABASE_NAME);
            myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (myOutput != null) {
                try {
                    myOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (myInput != null) {
                try {
                    myInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
