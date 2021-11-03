package com.ps14498.ailatrieuphu.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14498.ailatrieuphu.MainActivity;
import com.ps14498.ailatrieuphu.Model.Question;
import com.ps14498.ailatrieuphu.R;

public class AddQuestion extends AppCompatActivity {
    EditText edtNoiDung,edtDapAnA,edtDapAnB,edtDapAnC,edtDapAnD,edtDapAnDung;
    TextView tvThuTu;
    Button btnluu, btnhuy;
    int quesnumber = 0;
    DatabaseReference mData;
    Question question;
    int newNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        anhxa();
        layThuTu();

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (luuvaofirebase()>0)
                {
                    trovetrangchu();
                    count();
                    Toast.makeText(AddQuestion.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(AddQuestion.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
//                if (layThuTu()!=0){
//                    edNoiDung.setText("");
//                    edDapAnA.setText("");
//                    edDapAnB.setText("");
//                    edDapAnC.setText("");
//                    edDapAnD.setText("");
//                    edDapAnDung.setText("");
//                }
            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trovetrangchu();
            }
        });
    }

    public void trovetrangchu(){
        Intent i = new Intent(AddQuestion.this, ListQuestion.class);
        startActivity(i);
    }

    public void anhxa(){
        edtNoiDung = findViewById(R.id.edtnoidungcauhoi);
        edtDapAnA = findViewById(R.id.edtdapana);
        edtDapAnB = findViewById(R.id.edtdapanb);
        edtDapAnC = findViewById(R.id.edtdapanc);
        edtDapAnD = findViewById(R.id.edtdapand);
        edtDapAnDung = findViewById(R.id.edtdapandung);
        tvThuTu = findViewById(R.id.tvthemcauhoi);
        btnhuy = findViewById(R.id.btnhuy);
        btnluu = findViewById(R.id.btnluu);
    }

    public int layThuTu()
    {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("QuesNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quesnumber = Integer.parseInt(snapshot.getValue().toString());
                tvThuTu.setText("Question"+quesnumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return quesnumber;
    }

    public long luuvaofirebase(){
        mData =FirebaseDatabase.getInstance().getReference("Question");
        String loi = "Lỗi";
        try
        {
            String key = mData.push().getKey();
            String edND= edtNoiDung.getText().toString();
            String edA= edtDapAnA.getText().toString();
            String edB= edtDapAnB.getText().toString();
            String edC= edtDapAnC.getText().toString();
            String edD= edtDapAnD.getText().toString();
            String edAns= edtDapAnDung.getText().toString();
             if (edND.isEmpty()||edA.isEmpty()||edB.isEmpty()||edC.isEmpty()||edD.isEmpty()||edAns.isEmpty())
            {
                loi+= "\n Vui lòng điền đầy đủ các ô nhập";
                throw  new Exception();
            }

            else
            {
                if (edA.equalsIgnoreCase(edAns) || edB.equalsIgnoreCase(edAns) || edC.equalsIgnoreCase(edAns) || edD.equalsIgnoreCase(edAns))
                {
                    if (edA.equalsIgnoreCase(edB) || edB.equalsIgnoreCase(edC) || edC.equalsIgnoreCase(edD))
                    {
                        loi+= "\n Các đáp án không được trùng nhau";
                        throw  new Exception();
                    }
                    else
                    {
                        question = new Question(key, quesnumber, edND, edA, edB, edC, edD, edAns);
                        mData.child(key).setValue(question);
                    }
                }
                else
                {
                    loi+= "\n Đáp án không có trong các câu trả lời";
                    throw new Exception();
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, loi, Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }

    public void count(){
        mData = FirebaseDatabase.getInstance().getReference();
        newNumber = quesnumber+1;
        mData.child("QuesNumber").setValue(newNumber);
    }
}