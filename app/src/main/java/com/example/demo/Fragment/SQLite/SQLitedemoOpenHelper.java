package com.example.demo.Fragment.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.demo.Model.ChiTieu;
import com.example.demo.Model.DanhMucChi;
import com.example.demo.Model.DanhMucThu;
import com.example.demo.Model.ThuNhap;
import com.example.demo.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SQLitedemoOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "demoDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_THU = "thu";
    private static final String TABLE_CHI = "chi";
    private static final String TABLE_DANH_MUC_THU = "dmthu";
    private static final String TABLE_DANH_MUC_CHI = "dmchi";

    public SQLitedemoOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createTableThu = "CREATE TABLE " + TABLE_THU + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "src INTEGER," +
                "loai TEXT," +
                "tien REAL," +
                "thoiGian DATE," +
                "ghiChu TEXT)";
        db.execSQL(createTableThu);
        final String createTableChi = "CREATE TABLE " + TABLE_CHI + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "src INTEGER," +
                "loai TEXT," +
                "tien REAL," +
                "thoiGian DATE," +
                "ghiChu TEXT)";
        db.execSQL(createTableChi);
        final String createTableDanhMucThu = "CREATE TABLE " + TABLE_DANH_MUC_THU + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "src INTEGER," +
                "loai TEXT)";
        db.execSQL(createTableDanhMucThu);

        final String createTableDanhMucChi = "CREATE TABLE " + TABLE_DANH_MUC_CHI + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "src INTEGER," +
                "loai TEXT)";
        db.execSQL(createTableDanhMucChi);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANH_MUC_CHI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANH_MUC_THU);
    }

    public void addThuNhap(ThuNhap thuNhap) {
        String sql = "INSERT INTO thu(src,loai,tien,thoiGian,ghiChu) VALUES (?,?,?,?,?)";

        String thoiGian = thuNhap.getTgian();
        String[] parts = thoiGian.split("-");
        String ngay = parts[0];
        String thang = parts[1];
        String nam = parts[2];
        String time = nam+"/"+thang+"/"+ngay;
        String[] arg = {Integer.toString(thuNhap.getSrcimg()), thuNhap.getLoai(), thuNhap.getTien().toString(),
                time, thuNhap.getGhiChu()};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, arg);
    }

    public List<ThuNhap> getThuNhapAll() {
        List<ThuNhap> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor cursor = st.query("thu", null, null, null,
                null, null, null);
        while ((cursor != null) && (cursor.moveToNext())) {
            int id = cursor.getInt(0);
            int src = cursor.getInt(1);
            String loai = cursor.getString(2);
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            String ghiChu = cursor.getString(5);
            list.add(new ThuNhap(id, src, loai, tien, thoiGian, ghiChu));
        }
        return list;
    }
    public List<ThuNhap> getThuNhapNam(String namTK) {
        List<ThuNhap> list = new ArrayList<>();
        String tu = namTK+"/01/01";
        String den = namTK+"/12/31";
        String[] whereArgs = {tu,den};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM thu WHERE thoiGian BETWEEN ? AND ? ORDER BY thoiGian",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            list.add(new ThuNhap(tien, thoiGian));
        }
        return list;
    }
    public List<ThuNhap> getThuNhapThang(String thangTK,String namTK) {
        List<ThuNhap> list = new ArrayList<>();
        String tu = namTK+"/"+thangTK+"/01";
        String den = namTK+"/"+thangTK+"/31";
        String[] whereArgs = {tu,den};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM thu WHERE thoiGian BETWEEN ? AND ? ORDER BY thoiGian",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            list.add(new ThuNhap(tien, thoiGian));
        }
        return list;
    }
    public List<ThuNhap> getThuNhapNgay(String ngayTK, String thangTK,String namTK) {
        List<ThuNhap> list = new ArrayList<>();
        String tu = namTK+"/"+thangTK+"/"+ngayTK;
        String[] whereArgs = {tu};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM thu WHERE thoiGian = ? ",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            list.add(new ThuNhap(tien, thoiGian));
        }
        return list;
    }
    public List<ThuNhap> getThuNhapTuyChon(String tu,String den) {
        List<ThuNhap> list = new ArrayList<>();
        //Chuyen thoi gian thanh dang can thiet
        String[] partsBD = tu.split("-");
        String ngayBD = partsBD[0];
        String thangBD = partsBD[1];
        String namBD = partsBD[2];
        String timeBD = namBD+"/"+thangBD+"/"+ngayBD;
        String[] partsKT = den.split("-");
        String ngayKT = partsKT[0];
        String thangKT = partsKT[1];
        String namKT = partsKT[2];
        String timeKT = namKT+"/"+thangKT+"/"+ngayKT;
        //Khoi tao cac cau lenh
        String[] whereArgs = {timeBD,timeKT};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM thu WHERE thoiGian BETWEEN ? AND ?",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            int id = cursor.getInt(0);
            int src = cursor.getInt(1);
            String loai = cursor.getString(2);
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            String ghiChu = cursor.getString(5);
            list.add(new ThuNhap(id, src, loai, tien, thoiGian, ghiChu));
        }
        return list;
    }

    public List<ThuNhap> getThuNhapTheoLoai(String loai, String tienDau, String tienCuoi) {
        List<ThuNhap> list = new ArrayList<>();
        //Khoi tao cac cau lenh
        String[] whereArgs = {loai,tienDau,tienCuoi};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM thu WHERE loai=? AND tien BETWEEN ? AND ?",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            int id = cursor.getInt(0);
            int src = cursor.getInt(1);
            String loaiT = cursor.getString(2);
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            String ghiChu = cursor.getString(5);
            list.add(new ThuNhap(id, src, loai, tien, thoiGian, ghiChu));
        }
        return list;
    }
    public int deleteThuNhap(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("thu", whereClause, whereArgs);
    }

    public int updateThuNhap(ThuNhap thuNhap) {
        ContentValues values = new ContentValues();
        values.put("src",thuNhap.getSrcimg());
        values.put("loai", thuNhap.getLoai());
        values.put("tien", thuNhap.getTien());
        String time = thuNhap.getTgian();
        String[] parts = time.split("-");
        String ngay = parts[0];
        String thang = parts[1];
        String nam = parts[2];
        String thoiGian = nam+"/"+thang+"/"+ngay;
        values.put("thoiGian",thoiGian);
        values.put("ghiChu", thuNhap.getGhiChu());
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(thuNhap.getId())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("thu",
                values, whereClause, whereArgs);
    }

    public void addChiTieu(ChiTieu chiTieu) {
        String sql = "INSERT INTO chi(src,loai,tien,thoiGian,ghiChu) VALUES (?,?,?,?,?)";

        String thoiGian = chiTieu.getTgian();
        String[] parts = thoiGian.split("-");
        String ngay = parts[0];
        String thang = parts[1];
        String nam = parts[2];
        String time = nam+"/"+thang+"/"+ngay;

        String[] arg = {Integer.toString(chiTieu.getSrcimg()), chiTieu.getLoai(), chiTieu.getTien().toString(),
                time, chiTieu.getGhiChu()};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, arg);
    }

    public List<ChiTieu> getChiTieuAll() {
        List<ChiTieu> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor cursor = st.query("chi", null, null, null,
                null, null, null);
        while ((cursor != null) && (cursor.moveToNext())) {
            int id = cursor.getInt(0);
            int src = cursor.getInt(1);
            String loai = cursor.getString(2);
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            String ghiChu = cursor.getString(5);
            list.add(new ChiTieu(id, src, loai, tien, thoiGian, ghiChu));
        }
        return list;
    }
    public List<ChiTieu> getChiTieuNam(String namTK) {
        List<ChiTieu> list = new ArrayList<>();
        String tu = namTK+"/01/01";
        String den = namTK+"/12/31";
        String[] whereArgs = {tu,den};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM chi WHERE thoiGian BETWEEN ? AND ? ORDER BY thoiGian",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            list.add(new ChiTieu(tien, thoiGian));
        }
        return list;
    }
    public List<ChiTieu> getChiTieuThang(String thangTK,String namTK) {
        List<ChiTieu> list = new ArrayList<>();
        String tu = namTK+"/"+thangTK+"/01";
        String den = namTK+"/"+thangTK+"/31";
        String[] whereArgs = {tu,den};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM chi WHERE thoiGian BETWEEN ? AND ? ORDER BY thoiGian",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            list.add(new ChiTieu(tien, thoiGian));
        }
        return list;
    }
    public List<ChiTieu> getChiTieuNgay(String ngayTK,String thangTK,String namTK) {
        List<ChiTieu> list = new ArrayList<>();
        String den = namTK+"/"+thangTK+"/"+ngayTK;
        String[] whereArgs = {den};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM chi WHERE thoiGian = ? ",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            list.add(new ChiTieu(tien, thoiGian));
        }
        return list;
    }
    public List<ChiTieu> getChiTieuTuyChon(String tu,String den) {
        List<ChiTieu> list = new ArrayList<>();
        //Chuyen thoi gian thanh dang can thiet
        String[] partsBD = tu.split("-");
        String ngayBD = partsBD[0];
        String thangBD = partsBD[1];
        String namBD = partsBD[2];
        String timeBD = namBD+"/"+thangBD+"/"+ngayBD;
        String[] partsKT = den.split("-");
        String ngayKT = partsKT[0];
        String thangKT = partsKT[1];
        String namKT = partsKT[2];
        String timeKT = namKT+"/"+thangKT+"/"+ngayKT;
        //Khoi tao cac cau lenh
        String[] whereArgs = {timeBD,timeKT};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM chi WHERE thoiGian BETWEEN ? AND ?",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            int id = cursor.getInt(0);
            int src = cursor.getInt(1);
            String loai = cursor.getString(2);
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            String ghiChu = cursor.getString(5);
            list.add(new ChiTieu(id, src, loai, tien, thoiGian, ghiChu));
        }
        return list;
    }

    public List<ChiTieu> getChiTieuTheoLoai(String loai, String tienDau, String tienCuoi) {
        List<ChiTieu> list = new ArrayList<>();
        //Chuyen thoi gian thanh dang can thiet
        //Khoi tao cac cau lenh
        String[] whereArgs = {loai,tienDau,tienCuoi};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM chi WHERE loai=? AND tien BETWEEN ? AND ?",whereArgs);
        while ((cursor != null) && (cursor.moveToNext())) {
            int id = cursor.getInt(0);
            int src = cursor.getInt(1);
            String loaiC = cursor.getString(2);
            double tien = cursor.getDouble(3);
            String time = cursor.getString(4);
            String[] parts = time.split("/");
            String nam = parts[0];
            String thang = parts[1];
            String ngay = parts[2];
            String thoiGian = ngay+"-"+thang+"-"+nam;
            String ghiChu = cursor.getString(5);
            list.add(new ChiTieu(id, src, loaiC, tien, thoiGian, ghiChu));
        }
        return list;
    }


    public int deleteChiTieu(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("chi", whereClause, whereArgs);
    }

    public int updateChiTieu(ChiTieu chiTieu) {
        ContentValues values = new ContentValues();
        values.put("src", chiTieu.getSrcimg());
        values.put("loai", chiTieu.getLoai());
        values.put("tien", chiTieu.getTien());

        String time = chiTieu.getTgian();
        String[] parts = time.split("-");
        String ngay = parts[0];
        String thang = parts[1];
        String nam = parts[2];
        String thoiGian = nam+"/"+thang+"/"+ngay;
        values.put("thoiGian",thoiGian);
        values.put("ghiChu", chiTieu.getGhiChu());
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(chiTieu.getId())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("chi",
                values, whereClause, whereArgs);
    }
    public void createDanhMucThu() {
        String sql = "INSERT INTO dmthu(src,loai) VALUES (?,?)";
        String []loai= {"Khác","Cho thuê nhà","Lương","Quà tặng","Trợ cấp","Buôn bán",
                "Thu nhập chính","Lãi suất","Xổ số","Việc làm"};
        int count =0,srcimg =0;
        while(count<10){
            try {
                Class res = R.drawable.class;
                Field field = res.getField("thu_"+(count+1));
                srcimg = field.getInt(null);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            String[] arg = {Integer.toString(srcimg), loai[count]};
            SQLiteDatabase st = getWritableDatabase();
            st.execSQL(sql, arg);
            count++;
        }

    }

    public void addDanhMucThu(DanhMucThu danhMucThu) {
        String sql = "INSERT INTO dmthu(src,loai) VALUES (?,?)";

        String[] arg = {Integer.toString(danhMucThu.getSrcimg()), danhMucThu.getLoai()};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, arg);
    }

    public List<DanhMucThu> getDanhMucThuAll() {
        List<DanhMucThu> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor cursor = st.query("dmthu", null, null, null,
                null, null, null);
        while ((cursor != null) && (cursor.moveToNext())) {
            int id = cursor.getInt(0);
            int src = cursor.getInt(1);
            String loai = cursor.getString(2);
            list.add(new DanhMucThu(id, src, loai));
        }
        return list;
    }

    public void createDanhMucChi() {
        String sql = "INSERT INTO dmchi(src,loai) VALUES (?,?)";
        String []loai= {"Khác","Di chuyển","Mua sắm","Sức khoẻ",
                "Cho vay","Kinh doanh","Ăn uống","Học tập",
                "Quần áo","Xăng xe","Con cái","Điện thoại",
                "Du lịch","Hiếu hỉ","Mĩ phẩm","Điện nước"};
        int count =0,srcimg =0;
        while(count<16){
            try {
                Class res = R.drawable.class;
                Field field = res.getField("chi_"+(count+1));
                srcimg = field.getInt(null);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            String[] arg = {Integer.toString(srcimg), loai[count]};
            SQLiteDatabase st = getWritableDatabase();
            st.execSQL(sql, arg);
            count++;
        }
    }
    public void addDanhMucChi(DanhMucChi danhMucChi) {
        String sql = "INSERT INTO dmchi(src,loai) VALUES (?,?)";

        String[] arg = {Integer.toString(danhMucChi.getSrcimg()), danhMucChi.getLoai()};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, arg);
    }

    public List<DanhMucChi> getDanhMucChiAll() {
        List<DanhMucChi> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor cursor = st.query("dmchi", null, null, null,
                null, null, null);
        while ((cursor != null) && (cursor.moveToNext())) {
            int id = cursor.getInt(0);
            int src = cursor.getInt(1);
            String loai = cursor.getString(2);
            list.add(new DanhMucChi(id, src, loai));
        }
        return list;
    }
}