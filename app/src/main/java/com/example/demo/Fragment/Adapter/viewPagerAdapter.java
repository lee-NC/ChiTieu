package com.example.demo.Fragment.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demo.Fragment.Tab.Chi;
import com.example.demo.Fragment.Tab.ThongKe;
import com.example.demo.Fragment.Tab.Thu;

public class viewPagerAdapter extends FragmentStatePagerAdapter {
    private int numPage =3;
    public viewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0: return new Thu();
           case 1: return new Chi();
           case 2: return new ThongKe();
           default: return new Thu();
       }
    }

    @Override
    public int getCount() {
        return numPage;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Thu";
            case 1: return "Chi";
            case 2: return "Thống Kê";
            default: return "Thu";
        }
    }

}
