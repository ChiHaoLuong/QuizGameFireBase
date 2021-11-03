package com.ps14498.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14498.ailatrieuphu.Admin.AdminActivity;
import com.ps14498.ailatrieuphu.Model.User;
import com.ps14498.ailatrieuphu.Admin.AddQuestion;
import com.ps14498.ailatrieuphu.NgoaiLeBackGround.Top5Activity;

public class MainActivity extends AppCompatActivity {
    Button btnchoi;
    EditText edtten;
    ImageView ivchucdanh, ivthemcauhoi, ivtop5;
    Button btnok;
    int quesnumber;
    DatabaseReference mData;
    TextView tvthutu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        if(layThuTu()>0) Toast.makeText(this, "Lấy được", Toast.LENGTH_SHORT).show();

                btnchoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try 
                        {
                            String ten = edtten.getText().toString();
                            if (ten.isEmpty()) throw new Exception();
                            Toast.makeText(MainActivity.this, "Bắt đầu chơi", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, QuestionActivity.class);
                            i.putExtra("ten", edtten.getText().toString());
                            i.putExtra("socau", tvthutu.getText().toString());
                            Log.d("socau", tvthutu.getText().toString());
                            startActivity(i);
                            finish();
//
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this, "Lỗi nhập tên", Toast.LENGTH_SHORT).show();
                        }
                   
                    }
                });

                ivchucdanh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                    }
                });

                ivthemcauhoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i =new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(i);
                    }
                });

                ivtop5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, Top5Activity.class);
                        startActivity(i);
                        finish();
                    }
                });
        }

    public void anhxa(){
        tvthutu = findViewById(R.id.tvsocau);
        btnchoi = findViewById(R.id.btnbatdau);
        edtten = findViewById(R.id.edtten);
        ivchucdanh = findViewById(R.id.ivchucdanh);
        ivthemcauhoi = findViewById(R.id.ivthemcauhoi);
        ivtop5 = findViewById(R.id.ivtop5);
    }

    public void openDialog(){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_bangdiem);
        btnok = dialog.findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public int layThuTu()
    {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("QuesNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quesnumber = Integer.parseInt(snapshot.getValue().toString());
                tvthutu.setText(quesnumber+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return quesnumber;
    }
}