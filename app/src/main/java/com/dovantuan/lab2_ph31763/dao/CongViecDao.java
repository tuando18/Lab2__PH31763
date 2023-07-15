package com.dovantuan.lab2_ph31763.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


import com.dovantuan.lab2_ph31763.database.DbHelper;
import com.dovantuan.lab2_ph31763.model.CongViec;
import com.dovantuan.lab2_ph31763.model.CongViec;


public class CongViecDao {
    private final DbHelper dbHelper;

    public CongViecDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    //select all: lấy toàn bộ dữ liệu từ bảng trong database để add vào list
    public ArrayList<CongViec> selectAll() {
        ArrayList<CongViec> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();// đọc dữ liệu từ dataBase

        try{
            Cursor cursor = db.rawQuery("select * from congviec", null); //truy vấn lấy toàn bộ dữ liệu từ bảng congviec
            if (cursor.getCount() > 0){
                cursor.moveToFirst(); //đưa con trỏ về dòng đầu tiên trong bảng
                while (!cursor.isAfterLast()){
                    CongViec cv = new CongViec(); //tạo đối tượng để sau đó add đối tượng này vào list
                    //nhập thông tin cho đối tượng
                    cv.setId(cursor.getInt(0));
                    cv.setNoidung(cursor.getString(2));
                    cv.setTieude(cursor.getString(1));
                    cv.setNgay(cursor.getString(3));
                    cv.setLoai(cursor.getString(4));
                    cv.setTrangthai(cursor.getInt(5));

                    //add đối tượng vào list
                    list.add(cv);
                    cursor.moveToNext(); // next con trỏ sang dòng tiếp theo
                }
            }
        }
        catch (Exception e){
            Log.i(TAG, "Lỗi", e);
        }
        return list;
    }

    //insert dữ liệu vao bảng congviec
    public boolean insert(CongViec cv){
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //getWrite.. ghi dữ liệu vào database
        ContentValues value = new ContentValues(); // đưa dữ lệu vào database
        value.put("tieude", cv.getTieude());//key ở đây là tên cột
        value.put("noidung", cv.getNoidung());
        value.put("ngay", cv.getNgay());
        value.put("loai", cv.getLoai());
        value.put("trangthai", cv.getTrangthai());

        //nếu add thành công thì trả về giá trị là số dòng được chèn vào bảng
        long row = db.insert("congviec", null, value);
        return (row > 0);
    }

    //update dữ liệu vào bảng congviec
    public boolean update(CongViec cv){
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //getWrite.. ghi dữ liệu vào database
        ContentValues value = new ContentValues(); // đưa dữ lệu vào database
        value.put("tieude", cv.getTieude());//key ở đây là tên cột
        value.put("noidung", cv.getNoidung());
        value.put("ngay", cv.getNgay());
        value.put("loai", cv.getLoai());
        value.put("trangthai", cv.getTrangthai());
        long row = db.update("congviec", value, "id = ?", new String[] {String.valueOf(cv.getId())});
        return (row > 0);
    }
    //xóa đữa liệu trong bảng công việc
    public boolean delete (int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("congviec", "id=?", new String[] {String.valueOf(id)});
        return (row > 0);
    }
}
