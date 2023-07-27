package com.dovantuan.lab2_ph31763.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import com.dovantuan.lab2_ph31763.R;
import com.dovantuan.lab2_ph31763.dao.CongViecDao;
import com.dovantuan.lab2_ph31763.model.CongViec;


public class CongViecAdapter extends RecyclerView.Adapter<CongViecAdapter.viewholder> {
    //bước 2.1
    private final Context context;
    private final ArrayList<CongViec> list;
    //bước 2.3
    CongViecDao cvdao;

    //bước 2.2
    public CongViecAdapter(Context context, ArrayList<CongViec> list) {
        this.context = context;
        this.list = list;
        //bước 2.4
        cvdao = new CongViecDao(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //bước 3:
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cv, null);

        return new viewholder(view);
    }

    //cập nhật dữ liệu trên view tại vị trí position
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        //bước 4:
        holder.txtTieuDe.setText(list.get(position).getTieude());
        holder.txtNoiDung.setText(list.get(position).getNoidung());
        holder.txtNgay.setText(list.get(position).getNgay());
        holder.txtLoai.setText(list.get(position).getLoai());

        //nếu phần tử nào có trang thái
        if (list.get(position).getTrangthai() == 1) {
            holder.chkcv.setChecked(true);
        } else {
            holder.chkcv.setChecked(false);
        }
        //gán lại đôi tượng
        CongViec cv = list.get(position);//truy xuất đến dữ liệu đang được chọn
        //xóa dữ liệu
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder buider = new AlertDialog.Builder(context);
                buider.setTitle("Cảnh báo");
                buider.setIcon(R.drawable.warning);
                buider.setMessage("Bạn có chắc chắn muốn xóa không?");
                //nút buuton
                buider.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cvdao.delete(cv.getId())) {
                            list.clear();//xóa hết dữ liệu trong list
                            list.addAll(cvdao.selectAll()); //add lại toàn bộ dữ liệu trong list
                            notifyDataSetChanged();
                            Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Delete false", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                buider.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Không xóa", Toast.LENGTH_SHORT).show();
                    }
                });

                //show hộp thoại

                AlertDialog dialog = buider.create();// tạo
                dialog.show();

//                if(cvdao.delete(cv.getId())){
//                    list.clear();//xóa hết dữ liệu trong list
//                    list.addAll(cvdao.selectAll()); //add lại toàn bộ dữ liệu trong list
//                    notifyDataSetChanged();
//                    Toast.makeText(context, "Delete succ", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(context, "Delete false", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        CongViec c = list.get(position);

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogUpdate(c);
            }
        });
    }

    @Override
    //trả về số lượng phần tử hiển thị trên recycle view
    public int getItemCount() {
        return list.size();
    }

    //bước 1: tạo class lớp tĩnh kế thừa từ viewmodel, ánh xạ widget
    public static class viewholder extends RecyclerView.ViewHolder {
        //class này chỉ dun để ánh xạ
        TextView txtTieuDe, txtNoiDung, txtNgay, txtLoai;
        CheckBox chkcv;
        ImageView btnUpdate, btnDelete;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtTieuDe = itemView.findViewById(R.id.txtTieuDe);
            txtNoiDung = itemView.findViewById(R.id.txtNoiDung);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtLoai = itemView.findViewById(R.id.txtLoai);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            chkcv = itemView.findViewById(R.id.chkcv);
        }
    }
    //bước 2 extends class CongViecAdapter

    public void opendialogUpdate(CongViec cv) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context); //tạo đối tượng
        //gán layout vào view
        LayoutInflater infla = ((Activity) context).getLayoutInflater();
        View view = infla.inflate(R.layout.item_update, null);
        buider.setView(view);
        Dialog dia = buider.create();
        dia.show();
        //hiển thị dữ liệu

        TextView txttieudes = view.findViewById(R.id.txttieudes);
        TextView txtnoidungs = view.findViewById(R.id.txtnoidungs);
        TextView txtngays = view.findViewById(R.id.txtngays);
        TextView txtloais = view.findViewById(R.id.txtloais);

        txttieudes.setText(cv.getTieude());
        txtnoidungs.setText(cv.getNoidung());
        txtngays.setText(cv.getNgay());
        txtloais.setText(cv.getLoai());

        txtloais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder bui1 = new AlertDialog.Builder(context);
                bui1.setTitle("Chọn mức độ khó của công việc");
                String[] loai = {"Dễ", "Trung bình", "Khó"};
                bui1.setItems(loai, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        txtloais.setText(loai[i]);
                    }
                });

                AlertDialog dialog1 = bui1.create();
                dialog1.show();

            }
        });

        Button btnupdate = view.findViewById(R.id.btnsua);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.setTieude(txttieudes.getText().toString());
                cv.setNoidung(txtnoidungs.getText().toString());
                cv.setNgay(txtngays.getText().toString());
                cv.setLoai(txtloais.getText().toString());
                if (cvdao.update(cv)) {
                    list.clear();
                    list.addAll(cvdao.selectAll());
                    notifyDataSetChanged();
                    dia.dismiss();
                    Toast.makeText(context, "UPDATE thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "UPDATE thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
