package com.example.demo.Fragment.Tab;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.Fragment.SQLite.SQLitedemoOpenHelper;
import com.example.demo.Fragment.Adapter.ThuRecyclerViewAdapter;
import com.example.demo.Fragment.Nhap.NhapThu;
import com.example.demo.Model.DanhMucChi;
import com.example.demo.Model.DanhMucThu;
import com.example.demo.Model.ThuNhap;
import com.example.demo.R;
import com.example.demo.Spinner.SpinnerThuAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Thu extends Fragment  implements NhapThu.ShowInThuInterface {
    private View v;
    private RecyclerView revThu;
    private List<ThuNhap> list;
    private SQLitedemoOpenHelper sqLite;
    ThuRecyclerViewAdapter adapter;
    private FloatingActionButton fabThu;
    List<DanhMucThu> danhMuc;
    private RadioButton btLocLoai, btLocThoiGian,btLocAll;
    List<String> names ;
    int srcimg = 0;
    public Thu() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.thu,container,false);

        //anh xa
        fabThu = v.findViewById(R.id.fabThu);
        btLocLoai = v.findViewById(R.id.locThuLoai);
        btLocThoiGian = v.findViewById(R.id.locThuThoiGian);
        btLocAll = v.findViewById(R.id.locThuAll);
        revThu = v.findViewById(R.id.revThu);

        //set recyclerView
        sqLite = new SQLitedemoOpenHelper(getContext());
        list = sqLite.getThuNhapAll();
        adapter = new ThuRecyclerViewAdapter(this,list);
        adapter.setList(list);
        revThu.setLayoutManager(new LinearLayoutManager(getActivity()));
        revThu.setAdapter(adapter);

        //set fabThu
        fabThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhapThu nhapThu = new NhapThu();
                nhapThu.setTargetFragment(Thu.this, 1);
                nhapThu.show(getFragmentManager(),"Nhap khoan thu");
            }
        });

        //set btloc
        btLocLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btLocThoiGian.isChecked()==true) btLocThoiGian.setChecked(false);
                if(btLocAll.isChecked()==true) btLocAll.setChecked(false);
                locTheoLoai();
            }
        });

        btLocThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btLocLoai.isChecked()==true) btLocLoai.setChecked(false);
                if(btLocAll.isChecked()==true) btLocAll.setChecked(false);
                locTheoThoiGian();
            }
        });

        btLocAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btLocLoai.isChecked()==true) btLocLoai.setChecked(false);
                if(btLocThoiGian.isChecked()==true) btLocThoiGian.setChecked(false);
                list.clear();
                list = sqLite.getThuNhapAll();
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }
        });


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void locTheoThoiGian(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Lọc khoản thu");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.thoi_gian_tuy_chon,null);
        dialog.setView(view);

        //khai bao cac truong
        TextView lichTTC, lichDTC;
        ImageButton btlichTTC,btlichDTC;

        //Anh xa
        lichTTC = view.findViewById(R.id.lichTTC);
        lichDTC = view.findViewById(R.id.lichDTC);
        btlichTTC = view.findViewById(R.id.btLichTTC);
        btlichDTC = view.findViewById(R.id.btLichDTC);


        //set thoi gian default
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        lichTTC.setText(simpleDateFormat.format(calendar.getTime()));
        lichDTC.setText(simpleDateFormat.format(calendar.getTime()));

        //set Thoi gian
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);


        //set lich chon
        btlichTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        lichTTC.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },  nam,thang,ngay);
                dialog.show();
            }
        });
        btlichDTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        lichDTC.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },  nam,thang,ngay);
                dialog.show();
            }
        });


        //set cac button action
        dialog.setPositiveButton("Lọc", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tu = lichTTC.getText().toString();
                String den = lichDTC.getText().toString();
                list.clear();
                list = sqLite.getThuNhapTuyChon(tu, den);
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void locTheoLoai(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Lọc khoản thu");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.loc_du_lieu_loai,null);
        dialog.setView(view);
        Locale locale = new Locale("vi", "VN");
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);

        //khai bao cac truong
        EditText tienDau, tienCuoi;
        Spinner spLoai;
        SpinnerThuAdapter spinnerAdapter;

        //Anh xa
        tienCuoi = view.findViewById(R.id.edTienCuoi);
        tienDau = view.findViewById(R.id.edTienDau);
        spLoai = view.findViewById(R.id.spDanhMuc);

        //set spinner
        names = getDataLoai();
        spinnerAdapter = new SpinnerThuAdapter(getActivity(),names);
        spLoai.setAdapter(spinnerAdapter);

        //set item selection
        spLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String loai = names.get(position);
                String []parts = loai.split("-");
                String part2 = parts[1];
                srcimg = Integer.parseInt(part2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //cai dat nhap tien
        tienDau.addTextChangedListener(new TextWatcher() {
            String t="";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tienDau.removeTextChangedListener(this);
                String cleanString = s.toString().replaceAll("[$,.]", "");

                double parsed = Double.parseDouble(cleanString);
                if (parsed == 0.0) parsed=1000.0;
                if (parsed<100.0){
                    parsed = parsed*100;
                }

                String formatted = decimalFormat.format(parsed);
                t = formatted;
                tienDau.setText(formatted);
                tienDau.setSelection(formatted.length()-3);
                tienDau.addTextChangedListener(this);
            }
        });

        tienCuoi.addTextChangedListener(new TextWatcher() {
            String t="";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tienCuoi.removeTextChangedListener(this);
                String cleanString = s.toString().replaceAll("[$,.]", "");

                double parsed = Double.parseDouble(cleanString);
                if (parsed == 0.0) parsed=1000.0;
                if (parsed<100.0){
                    parsed = parsed*100;
                }

                String formatted = decimalFormat.format(parsed);
                t = formatted;
                tienCuoi.setText(formatted);
                tienCuoi.setSelection(formatted.length()-3);
                tienCuoi.addTextChangedListener(this);
            }
        });

        //set cac button action
        dialog.setPositiveButton("Lọc", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String []parts = spLoai.getSelectedItem().toString().split("-");
                String loai = parts[0];
                String tD = tienDau.getText().toString();
                if(tD.isEmpty())    {
                    double t = 100.0;
                    tD = decimalFormat.format(t);
                }
                tD= tD.replaceAll("[., ]","");

                String tC =tienCuoi.getText().toString();
                if ((tC.isEmpty())){
                    double t= 10.0E99;
                    tC = decimalFormat.format(t);
                }
                tC= tC.replaceAll("[., ]","");
                list.clear();
                list = sqLite.getThuNhapTheoLoai(loai,tD,tC);
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void updateThu(ThuNhap thuNhap){

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Sửa khoản thu");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.nhap_thu,null);
        dialog.setView(view);

        //Khai báo các trường để sử dụng
        TextView lichThu;
        EditText tien, ghiChu;
        Spinner spLoai;
        SpinnerThuAdapter spinnerAdapter;
        ImageButton btLich,btThemDanhMuc;

        //anh xa ca truong
        tien = view.findViewById(R.id.tienThu);
        lichThu = view.findViewById(R.id.lichThu);
        ghiChu= view.findViewById(R.id.ghichuThu);
        spLoai =view.findViewById(R.id.spDanhMucThu);
        btLich = view.findViewById(R.id.btLichThu);
        btThemDanhMuc = view.findViewById(R.id.btThemDanhMucThu);

        // set cac gia tri vơi cac gia tri lay duoc tien, lich, ghichu
        double num = Double.parseDouble(thuNhap.getTien().toString());
        Locale locale = new Locale("vi", "VN");
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        String tient = decimalFormat.format(num);
        tien.setText(tient);
        lichThu.setText(thuNhap.getTgian());
        ghiChu.setText(thuNhap.getGhiChu());

        //set spinner
        names = getDataLoai();
        spinnerAdapter = new SpinnerThuAdapter(getActivity(),names);
        spLoai.setAdapter(spinnerAdapter);

        //set loai voi gia tri lay duoc
        int vitri=0;
        for(String i: names){
            String loai = i;
            String []parts = loai.split("-");
            String part1 = parts[0];
            if(thuNhap.getLoai().equals(part1)) break;
            else    vitri++;
        }
        spLoai.setSelection(vitri);

        //them danh muc
        btThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Thêm danh mục thu");
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view= inflater.inflate(R.layout.them_danh_muc,null);
                dialog.setView(view);

                EditText edThem;

                edThem = view.findViewById(R.id.edThemDanhMuc);

                dialog.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(edThem.getText().equals("")) Toast.makeText(getContext(),"Bạn chưa nhập danh mục muốn thêm",Toast.LENGTH_SHORT).show();
                        else{
                            DanhMucThu danhMucThu = new DanhMucThu(R.drawable.thu_chi,edThem.getText().toString());
                            sqLite.addDanhMucThu(danhMucThu);
                            SpinnerThuAdapter adapter = new SpinnerThuAdapter(getContext(),getDataLoai());
                            spLoai.setAdapter(adapter);
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        //set item chon
        names = getDataLoai();
        spLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String loai = names.get(position);
                String []parts = loai.split("-");
                String part2 = parts[1];
                srcimg = Integer.parseInt(part2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //cai dat nhap tien
        tien.addTextChangedListener(new TextWatcher() {
            String t="";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tien.removeTextChangedListener(this);
                String cleanString = s.toString().replaceAll("[$,.]", "");

                double parsed = Double.parseDouble(cleanString);
                if (parsed == 0.0) parsed=1000.0;
                if (parsed<100.0){
                    parsed = parsed*100;
                }
                
                String formatted = decimalFormat.format(parsed);
                t = formatted;
                tien.setText(formatted);
                tien.setSelection(formatted.length()-3);
                tien.addTextChangedListener(this);
            }
        });

        //set date now
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        lichThu.setText(simpleDateFormat.format(calendar.getTime()));

        //get date
        btLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        lichThu.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },  nam,thang,ngay);
                dialog.show();
            }
        });

        //set cac nut
        dialog.setPositiveButton("SỬA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String []parts = spLoai.getSelectedItem().toString().split("-");
                thuNhap.setLoai(parts[0]);
                String t = tien.getText().toString();
                t= t.replaceAll("[., ]","");
                thuNhap.setTien(Double.parseDouble(t));
                thuNhap.setTgian(lichThu.getText().toString());
                thuNhap.setGhiChu(ghiChu.getText().toString());
                thuNhap.setSrcimg(srcimg);
                if(tien.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Bạn chưa nhập số tiền!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    sqLite.updateThuNhap(thuNhap);
                    list.clear();
                    list = sqLite.getThuNhapAll();
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public List<String> getDataLoai(){
        List<String> kind = new ArrayList<>();
        danhMuc = sqLite.getDanhMucThuAll();
        if(danhMuc.isEmpty()){
            sqLite.createDanhMucChi();
            danhMuc = sqLite.getDanhMucThuAll();
        }
        for(DanhMucThu i:danhMuc){
            String value = i.getLoai()+"-"+ i.getSrcimg();
            kind.add(value);
        }
        return kind;
    }

    @Override
    public void sendThu(int src, String loai, String tien, String tgian, String ghiChu) {
        try {
            sqLite.addThuNhap(new ThuNhap(src,loai,Double.parseDouble(tien),tgian,ghiChu));
        }catch (Exception e){
            System.out.println("Lỗi thêm thu nhập");
        }
        list.clear();
        try {
            list = sqLite.getThuNhapAll();
        }catch (Exception e){
            System.out.println("Lỗi get all thu nhập");
        }
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }


}
