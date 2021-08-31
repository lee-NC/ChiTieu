package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.demo.Fragment.Tab.Chi;
import com.example.demo.Fragment.Tab.HorizontalFlipTransformation;
import com.example.demo.Fragment.TKThuChi.Nam;
import com.example.demo.Fragment.Nhap.NhapChi;
import com.example.demo.Fragment.Nhap.NhapThu;
import com.example.demo.Fragment.TKThuChi.TatCa;
import com.example.demo.Fragment.TKThuChi.Thang;
import com.example.demo.Fragment.Tab.Thu;
import com.example.demo.Fragment.Adapter.viewPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tab;
    private ViewPager viewPager;
    private viewPagerAdapter adapter;
    private Toolbar toolbar;
    private FragmentManager manager;
    private Fragment fragment;
    private int STORAGE_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //anh xa cac dau
        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //khoi tao viewpager chinh
        adapter = new viewPagerAdapter(getSupportFragmentManager(), viewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new HorizontalFlipTransformation());
        tab.setupWithViewPager(viewPager);
        tab.getTabAt(0).setIcon(R.drawable.thu);
        tab.getTabAt(1).setIcon(R.drawable.chi);
        tab.getTabAt(2).setIcon(R.drawable.bieu_do);

        //XIN QUYEN
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

        }
        else{
            requestStoragePermissions();
        }
    }

    private void requestStoragePermissions(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Cấp quyền đọc ghi dữ liệu")
                    .setMessage("Bạn có phép chúng mình đọc và ghi vào bộ nhớ của bạn không?")
                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

                        }
                    }).setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE){
            if((grantResults.length>0)&&(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(MainActivity.this,"Tận hưởng tiện ích nhé!",Toast.LENGTH_SHORT).show();
            }
            else {
                finish();
            }
        }
    }

    public void getTKTC(View v){
        fragment = new TatCa();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
    public void getTKT(View v){
        fragment = new Thang();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
    public void getTKN(View v){
        fragment = new Nam();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.rate:
                rateApp();
                return true;
            case R.id.share:
                shareApp();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void rateApp(){
        Uri link = Uri.parse("https://play.google.com/store/apps/details?id=com.hdpsolution.quanlychitieu");
        Intent intent = new Intent(Intent.ACTION_VIEW,link);
        startActivity(intent);
    }
    private void shareApp(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        String share = "https://play.google.com/store/apps/details?id=com.hdpsolution.quanlychitieu";
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, share);
        startActivity(Intent.createChooser(intent, "Share app chi tieu"));
    }

}