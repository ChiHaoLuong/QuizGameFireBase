package com.ps14498.ailatrieuphu.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ps14498.ailatrieuphu.R;

public class AdminActivity extends AppCompatActivity {
    EditText edttk, edtmk;
    Button btnlog, btnre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        anhxa();
        dangnhap();
    }

    public void anhxa(){
        edtmk = findViewById(R.id.edtmk);
        edttk = findViewById(R.id.edttk);
        btnlog = findViewById(R.id.btndangnhap);
        btnre = findViewById(R.id.btntrolai);
    }
    
    public void dangnhap(){

        
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {

                    if (edttk.getText().toString().equalsIgnoreCase("admin") && edtmk.getText().toString().equalsIgnoreCase("admin"))
                    {
                        Intent intent = new Intent(AdminActivity.this, ListQuestion.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(AdminActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    }
                    else throw new Exception();
                }
                catch (Exception e)
                {
                    Toast.makeText(AdminActivity.this, "Sai tài khoản mật khẩu", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(AdminActivity.this, , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}