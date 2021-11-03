package com.ps14498.ailatrieuphu.NgoaiLeBackGround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14498.ailatrieuphu.Adapter.Top5UserAdapter;
import com.ps14498.ailatrieuphu.MainActivity;
import com.ps14498.ailatrieuphu.Model.User;
import com.ps14498.ailatrieuphu.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Top5Activity extends AppCompatActivity {
    ListView lv;
    Button btnreturn;
   DatabaseReference userdata;
   ArrayList<User> userlist;
   Top5UserAdapter adapter;
   User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top5);
        anhxa();
//        Lấy dữ liệu từ Firebase
        getDataFromFirebase();
//


//        Về trang chủ
        btnreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Top5Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
//
    }

    public void anhxa(){
        lv = findViewById(R.id.lvtop5);
        btnreturn = findViewById(R.id.btnreturn);
    }

    public void getDataFromFirebase(){
        userlist = new ArrayList<>();
        userdata = FirebaseDatabase.getInstance().getReference("Users");
        user = new User();
        userdata.orderByChild("diem").limitToLast(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    user = dataSnapshot.getValue(User.class);
                    userlist.add(user);
                }
                Collections.sort(userlist, new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return (int)(o2.getDiem() - o1.getDiem());
                    }
                });
                adapter = new Top5UserAdapter(Top5Activity.this, userlist);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}