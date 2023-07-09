package com.hotrodatmon_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

  //  private DatabaseHelper dbHelper;
    Button btnSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_trangchu);

        btnSkip = findViewById(R.id.skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TrangChuActivity.class);
                startActivity(intent);
            }
        });


    }


    //void loadLoai()
    //{
   //     //Fix cứng
   //     dbHelper = new DatabaseHelper();
   //     try {
    //        dbHelper.copyDatabaseFromAssets(this);
    //    } catch (IOException e) {
    //        e.printStackTrace();
   //     }
   //     dbHelper.openDatabase(this);
   //     SQLiteDatabase database =dbHelper.getData();

    //    //Đây là phần truy vấn: Muốn them xoa sua là làm việc ở chổ này nha.
    //    Cursor cursor = database.query("LOAI", null, null, null, null, null, null);
    //    cursor.moveToFirst();
    //    String data ="";
    //    while (cursor.isAfterLast() == false) {
    //        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
   //         cursor.moveToNext();
    //    }
        //---------------------------------------------


      //  cursor.close();
     //   dbHelper.closeDatabase();
   // }

}