package com.ps14498.ailatrieuphu.NgoaiLeBackGround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14498.ailatrieuphu.MainActivity;
import com.ps14498.ailatrieuphu.Model.User;
import com.ps14498.ailatrieuphu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KetQuaActitivy extends AppCompatActivity {
    Button btntrangchu;
    TextView tvdiem, tvusername, tvchucdanh;
    String chucdanh = null;
    ImageView ivkq;
    DatabaseReference mData;
    ArrayList<User> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_actitivy);
        anhxa();
        laydulieu();
        list = new ArrayList<>();

        btntrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KetQuaActitivy.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void anhxa(){
        btntrangchu = findViewById(R.id.btntrangchu);
        tvdiem = findViewById(R.id.tvdiem);
        tvusername = findViewById(R.id.tvusername);
        tvchucdanh = findViewById(R.id.tvchucdanh);
        ivkq = findViewById(R.id.ivkq);
    }

    public void laydulieu(){
        Intent i = getIntent();
        String diem = i.getStringExtra("diem");
        Log.d("point", diem);

        String ten = i.getStringExtra("ten");
        Log.d("ten2", ten+"");
        tvusername.setText(ten+" đào được: ");
        if (Integer.parseInt(diem)>=0 && Integer.parseInt(diem)< 20) chucdanh= "Chưa rành BigCoin";
        else if (Integer.parseInt(diem)>=20 && Integer.parseInt(diem)< 40)  chucdanh = "Tập sự Bigcoin";
        else if (Integer.parseInt(diem)>=40 && Integer.parseInt(diem)< 60) chucdanh = "Lão làng Bigcoin";
        else if (Integer.parseInt(diem)>=60 && Integer.parseInt(diem)< 80) chucdanh = "triệu phú Bigcoin";
        else if (Integer.parseInt(diem)>=80 && Integer.parseInt(diem)< 100) chucdanh = "Tỷ phú Bigcoin";
        else chucdanh = "Ông trùm Bigcoin";
        String cap = chucdanh;
        adduserfirebase(ten, Integer.parseInt(diem), cap);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvdiem.setText(diem+" xu Bigcoin");
                ivkq.setImageResource(R.drawable.xubigcoin);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvchucdanh.setText(chucdanh.toString());

            }
        }, 2000);


    }

    public void adduserfirebase(String ten, Integer diem, String cap) {
        DatabaseReference userRef;
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if (dataSnapshot.hasChild("ten"))
                    {
                        if(dataSnapshot.child("ten").getValue().toString().equalsIgnoreCase(ten))
                        {
                            user = dataSnapshot.getValue(User.class);
                        }
                    }
                }

                if (user == null)
                {
//                    Thêm mới user
                  String key = userRef.push().getKey();

                    Map valueMap = new HashMap();
                    valueMap.put("id", key);
                    valueMap.put("ten", ten);
                    valueMap.put("diem", diem);
                    valueMap.put("cap", cap);


                    //ddaayr le
                    userRef.child(key).updateChildren(valueMap);

                }
                else
                {
                    if (diem > user.getDiem())
                    {
                        user.setDiem(diem);
                        user.setCap(cap);
                    }
                    userRef.child(user.getId()).setValue(user);
                }

                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}