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

import com.ps14498.ailatrieuphu.Model.Question;
import com.ps14498.ailatrieuphu.Model.User;
import com.ps14498.ailatrieuphu.R;

import java.util.List;

public class Question_Adapter extends ArrayAdapter<Question> {
    Context context;
    List<Question> list;
    TextView tvid, tvcauhoi;

    public Question_Adapter(@NonNull Context context, List<Question> list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.question_items, null);
        }

        Question question = list.get(position);
        if (question!=null)
        {
            tvid = v.findViewById(R.id.tvquesid);
            tvcauhoi = v.findViewById(R.id.tvcauhoiquestion);
            tvid.setText("Câu hỏi số: "+question.getId());
            tvcauhoi.setText(question.getCauhoi());

            
        }

        return v;
    }
}
