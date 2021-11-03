package com.ps14498.ailatrieuphu.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14498.ailatrieuphu.Adapter.Question_Adapter;
import com.ps14498.ailatrieuphu.MainActivity;
import com.ps14498.ailatrieuphu.Model.Question;
import com.ps14498.ailatrieuphu.Model.User;
import com.ps14498.ailatrieuphu.R;

import java.util.ArrayList;

public class ListQuestion extends AppCompatActivity {
    ListView lv;
    Question_Adapter adapter;
    Button btn;
    ImageView iv;
    DatabaseReference quesData;
    ArrayList<Question> list;
    Question question;
    EditText edtcauhoi, edta, edtb, edtc, edtd, edtdapan;
    Button btnquaylai, btnsua;
    TextView tvid;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_question);
        anhxa();
        getDataFromFirebase();

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListQuestion.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListQuestion.this, AddQuestion.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void anhxa(){
        lv = findViewById(R.id.lvques);
        iv = findViewById(R.id.ivtrolaitrangchu);
        btn = findViewById(R.id.btnthemcauhoi);
    }

    public void getDataFromFirebase(){
        list = new ArrayList<>();
        question = null;
        quesData = FirebaseDatabase.getInstance().getReference();
        quesData.child("Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot sn: snapshot.getChildren())
                {
                    question = sn.getValue(Question.class);
                    list.add(question);
                }
                adapter = new Question_Adapter(ListQuestion.this, list);
                lv.setAdapter(adapter);
//                Set chọn items listview
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        openDialog(ListQuestion.this, "xem",list.get(position));
                    }
                });

                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        openDialog(ListQuestion.this, "sua", list.get(position));
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void openDialog(Context context, String type, Question question){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.questions_dialog);
        edtcauhoi = dialog.findViewById(R.id.edtcauhoi_dialog);
        edta = dialog.findViewById(R.id.edta_dialog);
        edtb = dialog.findViewById(R.id.edtb_dialog);
        edtc = dialog.findViewById(R.id.edtc_dialog);
        edtd = dialog.findViewById(R.id.edtd_dialog);
        edtdapan = dialog.findViewById(R.id.edtdapan_dialog);
        tvid = dialog.findViewById(R.id.tvid_dialog);
        btnquaylai = dialog.findViewById(R.id.btnquaylai_dialog);
        btnsua = dialog.findViewById(R.id.btnsua_dialog);
//        Gán giá trị cho EDT
        edtcauhoi.setText(question.getCauhoi());
        edta.setText(question.getA());
        edtb.setText(question.getB());
        edtc.setText(question.getC());
        edtd.setText(question.getD());
        edtdapan.setText(question.getDapan());
        Toast.makeText(context, question.getIdgoc(), Toast.LENGTH_SHORT).show();
        tvid.setText("Câu số: "+question.getId());
//

//        Cấm ghi sửa cho edt
            if (type.equalsIgnoreCase("xem"))
            {
                edta.setEnabled(false);
                edtb.setEnabled(false);
                edtc.setEnabled(false);
                edtd.setEnabled(false);
                edtcauhoi.setEnabled(false);
                edtdapan.setEnabled(false);
                btnsua.setText("Xóa");

                btnsua.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DatabaseReference xoa = FirebaseDatabase.getInstance().getReference("Question");
                        xoa.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                {
                                    if (dataSnapshot.hasChild("idgoc"))
                                    {
                                        if (dataSnapshot.child("idgoc").getValue().toString().equalsIgnoreCase(question.getIdgoc()));
                                        {
                                            xoa.child(question.getIdgoc()).removeValue();
                                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        return false;
                    }
                });
                
                btnsua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Giữ nút để xóa, quá trình này sẽ không cần xác nhận", Toast.LENGTH_SHORT).show();
                    }
                });

                btnquaylai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            else
            {
                Toast.makeText(context, "Sửa", Toast.LENGTH_SHORT).show();

                btnquaylai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnsua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String A =edta.getText().toString();
                            String B = edtb.getText().toString();
                            String C = edtc.getText().toString();
                            String D= edtd.getText().toString();
                            String cauhoi = edtcauhoi.getText().toString();
                            String dapan = edtdapan.getText().toString();
                            if (A.isEmpty() || B.isEmpty() || C.isEmpty() || D.isEmpty() || cauhoi.isEmpty() || dapan.isEmpty())
                                {
                                    throw  new Exception();
                                }


                            DatabaseReference sua = FirebaseDatabase.getInstance().getReference("Question");
                            sua.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Question question1 = null;
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        if (dataSnapshot.hasChild("idgoc")) {
                                            if (dataSnapshot.child("idgoc").getValue().toString().equalsIgnoreCase(question.getIdgoc())) {
                                                question1 = dataSnapshot.getValue(Question.class);
                                                Log.d("Không lấy được", "Lấy rồi nè");
                                            }
                                        }
                                    }

                                    if (question1 == null) {
                                        Log.d("Không lấy được", "Không lấy được");

                                    } else {

                                        question1.setA(A);
                                        question1.setB(B);
                                        question1.setC(C);
                                        question1.setD(D);
                                        question1.setCauhoi(cauhoi);
                                        question1.setDapan(dapan);
                                        sua.child(question1.getIdgoc()).setValue(question1);
                                        dialog.dismiss();
                                    }
                                    return;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(context, "Lỗi khi sửa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }



            dialog.show();
    }
}