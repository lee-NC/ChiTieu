package com.example.demo.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demo.R;

import java.util.List;

public class SpinnerChiAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> names;

    public void setNames(List<String> names) {
        this.names = names;
    }

    public SpinnerChiAdapter(@NonNull Context context, @NonNull List<String> names) {
        super(context, 0, names);
        this.context = context;
        this.names = names;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.spinner_item,null);
        TextView textView = v.findViewById(R.id.nameSpinner);
        ImageView imageView = v.findViewById(R.id.imgSpinner);
        String loai = names.get(position);
        String []parts = loai.split("-");
        String part1 = parts[0];
        String part2 = parts[1];
        textView.setText(part1);
        Context c = imageView.getContext();
        String src = part2;
        int id = c.getResources().getIdentifier(src, "drawable", c.getPackageName());
        imageView.setImageResource(id);
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.spinner_item,null);
        TextView textView = v.findViewById(R.id.nameSpinner);
        ImageView imageView = v.findViewById(R.id.imgSpinner);
        String loai = names.get(position);
        String []parts = loai.split("-");
        String part1 = parts[0];
        String part2 = parts[1];
        textView.setText(part1);
        Context c = imageView.getContext();
        String src = part2;
        int id = c.getResources().getIdentifier(src, "drawable", c.getPackageName());
        imageView.setImageResource(id);
        return v;
    }

}
