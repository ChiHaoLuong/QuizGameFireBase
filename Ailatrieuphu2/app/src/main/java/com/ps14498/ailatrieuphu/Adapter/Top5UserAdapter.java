package com.ps14498.ailatrieuphu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ps14498.ailatrieuphu.Model.User;
import com.ps14498.ailatrieuphu.R;

import java.util.ArrayList;
import java.util.List;

public class Top5UserAdapter extends ArrayAdapter<User> {
    Context context;
    List<User> list;
    TextView tvten, tvdiem, tvcap, tvstt;

    public Top5UserAdapter(@NonNull Context context, List<User> list){
        super(context, 0 , list);
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.top5_items, null);
        }

        User user = list.get(position);
        if (user!=null)
        {
            tvten = v.findViewById(R.id.tvtenuser);
            tvdiem = v.findViewById(R.id.tvxubigcoin);
            tvcap = v.findViewById(R.id.tvcapuser);
            tvstt = v.findViewById(R.id.tvsttuser);
            tvten.setText(user.getTen());
            tvdiem.setText(user.getDiem()+" xu Bigcoin");
            tvcap.setText(user.getCap());
            tvstt.setText("Top "+(position+1));

        }
        return v;
    }
}
