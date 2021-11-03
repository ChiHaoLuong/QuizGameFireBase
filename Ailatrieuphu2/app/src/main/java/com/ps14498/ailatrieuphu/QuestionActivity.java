package com.ps14498.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14498.ailatrieuphu.Model.Question;
import com.ps14498.ailatrieuphu.NgoaiLeBackGround.KetQuaActitivy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {
    Button btna, btnb, btnc, btnd, btntieptuc, btnchoilai, btnok;
    TextView tvstt, tvcauhoi, tvdapan, cau5, cau10, cau15, cau20, cau25, cau30, tvid, tvdiem;
    ArrayList<Question> list;
    int idcauhoi = 0, sodiemtru=0, sodiemtrudodoicauhoi=0;
    Boolean stoptime = false, cohoi = false, doicauhoi=false, stoptimeed = false, cohoied = false, doicauhoied = false, dapaned = false;
    DatabaseReference mData;
    Button btnxn;
    ImageView ivchucdanh, ivdoicauhoi, ivdapan, ivcohoi, ivstoptime;
    Question question;
    int diem;
    int socauhoifirebase =0;
    ProgressBar pb;
    String dapanla;

    private long START_TIME_IN_MILLIS = 180000;
    private CountDownTimer countDownTimer;
    private  long tgconlai = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        anhxa();
        doidulieulist();
        batdaudem(list.get(idcauhoi));
        daydulieu(list.get(idcauhoi));

        ivchucdanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                tamdung();
            }
        });
    }

    public void anhxa(){
        btna = findViewById(R.id.btnA);
        btnb = findViewById(R.id.btnB);
        btnc = findViewById(R.id.btnC);
        btnd = findViewById(R.id.btnD);
        tvcauhoi = findViewById(R.id.tvcauhoi);
        tvstt = findViewById(R.id.tvstt);
        tvdiem = findViewById(R.id.tvdiemhientai);
        ivchucdanh = findViewById(R.id.ivchucdanh);
        ivdapan = findViewById(R.id.ivdapan);
        ivcohoi = findViewById(R.id.ivcohoi);
        ivdoicauhoi = findViewById(R.id.ivdoicauhoi);
        ivstoptime = findViewById(R.id.ivstoptime);
        tvid = findViewById(R.id.tvid);
        pb = findViewById(R.id.pbtimer);
    }

    public void doidulieulist(){
        Intent itent = getIntent();
        socauhoifirebase= Integer.parseInt(itent.getStringExtra("socau"));
        list = new ArrayList<>();
        list.add(new Question("asdsad",1, "TP.Hồ Chí Minh ở miền nào Việt Nam", "Bắc", "Trung", "Nam", "Không có đáp án", "Nam"));
        mData = FirebaseDatabase.getInstance().getReference("Question");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    question = dataSnapshot.getValue(Question.class);
                    list.add(question);
                }
                ramdom(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void daydulieu(Question question){
        Log.d("Câu", question.getId()+"");
        enableiv();
        doicauhoi = false;
        stoptime = false;
        cohoi = false;
        if (question==null) return;
        int socau = (idcauhoi-sodiemtrudodoicauhoi+1);
        tieptucdem();
        tvstt.setText(socau+"/10");
        tvcauhoi.setText(question.getCauhoi()+"");
        btna.setText(question.getA()+"");
        btnb.setText(question.getB()+"");
        btnc.setText(question.getC()+"");
        btnd.setText(question.getD()+"");
        tvid.setText("ID câu hỏi: "+question.getId());
        int diemhientai = ((idcauhoi-sodiemtru-sodiemtrudodoicauhoi)*10);
        tvdiem.setText(diemhientai+" xu");
        btna.setBackgroundResource(R.drawable.btn_dapan);
        btnc.setBackgroundResource(R.drawable.btn_dapan);
        btnb.setBackgroundResource(R.drawable.btn_dapan);
        btnd.setBackgroundResource(R.drawable.btn_dapan);
        btna.setTextColor(Color.WHITE);
        btnb.setTextColor(Color.WHITE);
        btnc.setTextColor(Color.WHITE);
        btnd.setTextColor(Color.WHITE);
        dapanla = question.getDapan().toString();

        btna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ktcauhoi(btna, question);

            }
        });

        btnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ktcauhoi(btnb, question);

            }
        });

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ktcauhoi(btnc, question);
            }
        });

        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ktcauhoi(btnd, question);
            }
        });

        ivstoptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                builder.setTitle("Sử dụng quyền");
                builder.setMessage("Bạn muốn ngưng đọng thời gian? ");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stoptime = true;
                        stoptimeed = true;
                        tamdung();
                        disableiv();
                    }
                });
                builder.create().show();
            }
        });
        
        ivdapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                builder.setTitle("Sử dụng quyền");
                builder.setMessage("Bạn muốn xem đáp án? ");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dapan();
                        disableiv();
                    }
                });
                builder.create().show();
            }
        });

        ivdoicauhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                builder.setTitle("Sử dụng quyền");
                builder.setMessage("Bạn muốn đổi câu hỏi? ");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doicauhoi();
                        disableiv();
                    }
                });
                builder.create().show();
            }
        });

        ivcohoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                builder.setTitle("Sử dụng quyền");
                builder.setMessage("Bạn muốn chọn cơ hội? ");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cohoi();
                        disableiv();
                    }
                });
                builder.create().show();
            }
        });

        tvid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienthiDialog("dung", question);
            }
        });


    }


    public void disableiv(){
        ivdapan.setEnabled(false);
        ivcohoi.setEnabled(false);
        ivdoicauhoi.setEnabled(false);
        ivstoptime.setEnabled(false);
        ivdapan.setImageResource(R.drawable.camdung);
        ivcohoi.setImageResource(R.drawable.camdung);
        ivdoicauhoi.setImageResource(R.drawable.camdung);
        ivstoptime.setImageResource(R.drawable.camdung);
    }

    public void enableiv(){
        if (stoptimeed==false)
        {
            ivstoptime.setEnabled(true);
            ivstoptime.setImageResource(R.drawable.dungtime);
        }
        if (cohoied==false)
        {
            ivcohoi.setEnabled(true);
            ivcohoi.setImageResource(R.drawable.cohoi);
        }

        if (doicauhoied == false)
        {
            ivdoicauhoi.setEnabled(true);
            ivdoicauhoi.setImageResource(R.drawable.doicauhoi);
        }

        if (dapaned == false)
        {
            ivdapan.setEnabled(true);
            ivdapan.setImageResource(R.drawable.dapan);
        }

    }

    public void ktcauhoi(Button btn, Question question){
        btn.setBackgroundResource(R.drawable.btn_nhan);
        btn.setTextColor(Color.BLACK);
        tamdung();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (btn.getText().toString().equalsIgnoreCase(question.getDapan().toString()))
                {
//                    Đúng
                    hienthiDialog("dung", question);

                }
                else
                {
//                    Sai
                        hienthiDialog("sai", question);
                }
            }
        },1000);
    }

    public void hienthiDialog(String trangthai, Question questions){
        Dialog dialog = new Dialog(this);
        if (trangthai.equalsIgnoreCase("dung"))
        {

            dialog.setContentView(R.layout.dialog_dung);
            idcauhoi++;
            diem = (idcauhoi-sodiemtru-sodiemtrudodoicauhoi)*10;
            btntieptuc = dialog.findViewById(R.id.btntieptuc);
            btntieptuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("list sai", list.size()+"");
                    if ((idcauhoi-sodiemtrudodoicauhoi)>=10)
                    {
                        ketqua();
                        tamdung();
                    }
                    else {
                        daydulieu(list.get(idcauhoi));
                    }
                    dialog.dismiss();
                }
            });
        }
        else
        {
            sodiemtru++;
            dialog.setContentView(R.layout.dialog_sai);
            tvdapan = dialog.findViewById(R.id.tvdapan);
            tvdapan.setText(questions.getDapan().toString());
            btnchoilai = dialog.findViewById(R.id.btnchoilai);
            idcauhoi++;
            if (cohoi==true)
            {
                sodiemtru--;
            }
            btnchoilai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((idcauhoi-sodiemtrudodoicauhoi)>=10)
                        {
                            tamdung();
                            ketqua();
                        }
                    else
                        {
                            tamdung();
                            daydulieu(list.get(idcauhoi));
                        }
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }



    public void ketqua(){
        Intent intent = getIntent();
        String ten =intent.getStringExtra("ten");
        Intent i = new Intent(QuestionActivity.this, KetQuaActitivy.class);
         diem = (idcauhoi-sodiemtru-sodiemtrudodoicauhoi)*10;
        i.putExtra("diem", diem+"");
        i.putExtra("ten", ten+"");
        Log.d("Số câu hiện tại", diem+"");
        startActivity(i);
    }



    public void openDialog(){
            Dialog dialog = new Dialog(QuestionActivity.this);
            dialog.setContentView(R.layout.dialog_bangdiem);
            btnxn = dialog.findViewById(R.id.btnok);
            cau5 = dialog.findViewById(R.id.cau5);
            cau10 = dialog.findViewById(R.id.cau10);
            cau15 = dialog.findViewById(R.id.cau15);
            cau25 = dialog.findViewById(R.id.cau25);
            cau30 = dialog.findViewById(R.id.cau30);
            Log.d("loi", diem+"");
            if (diem>=0 && diem < 20) cau5.setTextColor(Color.parseColor("#FFEB3B"));
            else if (diem>=20 && diem < 40) cau10.setTextColor(Color.parseColor("#FFEB3B"));
            else if (diem>=40 && diem < 60) cau15.setTextColor(Color.parseColor("#FFEB3B"));
            else if (diem>=60 && diem < 80) cau25.setTextColor(Color.parseColor("#FFEB3B"));
            else if (diem>=80 && diem < 100) cau30.setTextColor(Color.parseColor("#FFEB3B"));
            else                cau30.setTextColor(Color.parseColor("#FFEB3B"));
            btnxn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stoptime==false)
                    {
                        tieptucdem();
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        }



    public void tamdung()
    {
        countDownTimer.cancel();
    }

    public void tieptucdem()
    {
        countDownTimer.start();
    }

    public void batdaudem(Question question) {
        countDownTimer = new CountDownTimer(tgconlai, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = pb.getProgress();
                pb.setProgress(progress+1);
                tgconlai = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                hetthoigian();
            }
        }.start();
    }

    public ArrayList<Question> ramdom(ArrayList<Question> list){
        Collections.shuffle(list);
        return list;
    }

//    Đổi câu hỏi
    public void doicauhoi(){
        doicauhoi = true;
        doicauhoied = true;
        sodiemtrudodoicauhoi = 1;
        idcauhoi++;
        daydulieu(list.get(idcauhoi));

    }

//    Cơ hội
    public void cohoi(){
        cohoi = true;
        cohoied = true;
    }

//    Trợ giúp người thân
    public void  dapan(){
        dapaned = true;
        doibackgroundnut(btna);
        doibackgroundnut(btnb);
        doibackgroundnut(btnc);
        doibackgroundnut(btnd);
    }

    public void doibackgroundnut(Button btn){
        if (dapanla.equalsIgnoreCase(btn.getText().toString()))
        {
            btn.setBackgroundResource(R.drawable.btn_showdapan);
        }
    }

//    Hết thời gian
    public void hetthoigian(){
       ketqua();
    }
}