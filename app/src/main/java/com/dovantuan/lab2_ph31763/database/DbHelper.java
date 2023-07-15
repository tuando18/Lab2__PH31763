package com.dovantuan.lab2_ph31763.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    //bước 1: tạo csdl
    public static final String Db_name = "QLCV";
    public DbHelper(Context context) {
        super(context, Db_name, null, 2);
    }

    //tạo bảng trong Oncreate
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tb_congviec = "create table congviec(id integer primary key autoincrement," +
                                            "tieude text," +
                                            " noidung text," +
                                            " ngay text," +
                                            " loai text," +
                                            "trangthai integer)";
        sqLiteDatabase.execSQL(tb_congviec);

        // chèn dữ liệu vào bảng
        String data = "insert into congviec values (1, 'java', 'java cơ bản', '3/3/2023', 'dễ', 0),\n" +
                                                   "(2, 'python', 'python cơ bản', '2/3/2023', 'dễ', 1)";
        sqLiteDatabase.execSQL(data);
    }

    //được gọi khi nâng cấp dữ liệu cần
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            sqLiteDatabase.execSQL("drop table if exists congviec");
            onCreate(sqLiteDatabase);
        }
    }
}
