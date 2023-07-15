package com.dovantuan.lab2_ph31763;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dovantuan.lab2_ph31763.R;
import com.dovantuan.lab2_ph31763.adapter.CongViecAdapter;
import com.dovantuan.lab2_ph31763.dao.CongViecDao;
import com.dovantuan.lab2_ph31763.model.CongViec;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    RecyclerView rcvCongViec;
    Button fltAdd;
    CongViecAdapter adapter;
    CongViecDao cvdao;
    private ArrayList<CongViec> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ánh xạ
        rcvCongViec = findViewById(R.id.rcvCongViec);
        fltAdd = findViewById(R.id.btnAdd);

        //
        cvdao = new CongViecDao(this);
        list = cvdao.selectAll(); //lấy toaàn bộ dữ liệu trong CSDL

        //set layout cho recycle view
        LinearLayoutManager layout = new LinearLayoutManager(this);
        //LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        //GridLayoutManager layout = new GridLayoutManager(this, 2);
        rcvCongViec.setLayoutManager(layout);



        // dữ liệu
        adapter = new CongViecAdapter(this, list);
        rcvCongViec.setAdapter(adapter);

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogADD();
            }
        });

    }

    public void dialogADD(){
        AlertDialog.Builder bui = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater infla = ((Activity) MainActivity.this).getLayoutInflater();
        View view = infla.inflate(R.layout.item_add, null);
        bui.setView(view);
        Dialog dia = bui.create();
        dia.show();

        EditText txttieude = view.findViewById(R.id.txttieudea);
        EditText txtnoidung = view.findViewById(R.id.txtnoidunga);
        EditText txtngay = view.findViewById(R.id.txtngaya);
        EditText txtloai = view.findViewById(R.id.txtloaia);
        Button btnadd = view.findViewById(R.id.btnthem);

        txtloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder bui1 = new AlertDialog.Builder(MainActivity.this);
                bui1.setTitle("Chọn độ khó công việc");
                String[] loaia = {"Dễ", "Trung bình", "Khó"};
                bui1.setItems(loaia, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                            txtloai.setText(loaia[i]);
                    }
                });

                AlertDialog dia1 = bui1.create();
                dia1.show();

            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieude = txttieude.getText().toString();
                String noidung = txtnoidung.getText().toString();
                String ngay = txtngay.getText().toString();
                String loai = txtloai.getText().toString();
                CongViec cv = new CongViec(tieude, noidung, ngay, loai);
                if(cvdao.insert(cv)){
                    list.clear();
                    list.addAll(cvdao.selectAll());
                    adapter.notifyDataSetChanged();
                    dia.dismiss();
                    Toast.makeText(MainActivity.this, "Insert thành công", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity.this, "Insert thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}