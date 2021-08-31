package com.example.demo.Fragment.Tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.Chart.ChartActivity;
import com.example.demo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ThongKe extends Fragment {
    private Button btTC, btT, btN;
    private FloatingActionButton fabTK;
    private View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.thong_ke,container,false);
        btTC = v.findViewById(R.id.btTC);
        btT = v.findViewById(R.id.btT);
        btN = v.findViewById(R.id.btN);
        fabTK = v.findViewById(R.id.fabTK);
        fabTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChartActivity.class));
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

