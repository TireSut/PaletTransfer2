package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;

public class MainActivity extends AppCompatActivity {
    public String AYARGRUP,COMPANY,PLANT,PLANTTXT,WAREHOUSE,STOCKPLACE,STOCKPLACETXT,TARIH,BILTEKPARAM,ATUR,YONETICI;
 TextView tv_cihazid,tv_cihazadi,tv_bilgiler,tarihTxt;
 ImageView btn_plttransfer,btn_pltbozma;
 ImageButton ayarbtn,btn_exit,takvimBtn,deposayim;
 VeriTabani vt =new VeriTabani();




    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    DecimalFormat numf = new DecimalFormat("00");
public ArrayList<ayarlist> ayarlar = new ArrayList<>();
int konum;
String CIHAZID,CIHAZADI;
    private Boolean EthernetAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_cihazid =findViewById(R.id.CIHAZIDTXT);
        tv_cihazadi =findViewById(R.id.CIHAZADITXT);
        tv_bilgiler =findViewById(R.id.bilgiler);
        tarihTxt = findViewById(R.id.tarihTxt);


        AYARGRUP="";

        // cihaz ID al
        CIHAZID=getDeviceUniqueID(this).toUpperCase();
        tv_cihazid.setText(CIHAZID);
            CIHAZADI=vt.getAyarString("CIHAZ",CIHAZID,"ACIKLAMA");;
            AYARGRUP=vt.getAyarString("CIHAZ",CIHAZID,"DEGER");;
        if (AYARGRUP.equals("")){
            vt.setCihazId(CIHAZID);
            return;}
        tv_cihazadi.setText(AYARGRUP+"- " +CIHAZADI);
        // PRAMETRELERİ GETİR
        COMPANY=vt.getAyarString(AYARGRUP,"COMPANY","DEGER");
        PLANT=vt.getAyarString(AYARGRUP,"PLANT","DEGER");
        PLANTTXT=vt.getTesisAdi(COMPANY,PLANT);
        WAREHOUSE=vt.getAyarString(AYARGRUP,"WAREHOUSE","DEGER");
        STOCKPLACE=vt.getAyarString(AYARGRUP,"STOCKPLACE","DEGER");
        BILTEKPARAM=vt.getAyarString(AYARGRUP,"BILTEK","DEGER");
        ATUR=vt.getAyarString(AYARGRUP,"TUR","DEGER");
        if (ATUR.equals("")) {
            ATUR="false";
        }
        tv_bilgiler.setText(COMPANY+"-"+PLANT+"-"+WAREHOUSE+"-"+STOCKPLACE);
        YONETICI=vt.getAyarString(AYARGRUP,"YONETICI","DEGER");
        // yönetici ise yönetici ekranına git
        if (YONETICI.equals("E")){
            Intent intent = new Intent(MainActivity.this,depostokyonetim.class);
            intent.putExtra("AYARGRUP",AYARGRUP );
            intent.putExtra("COMPANY",COMPANY);
            intent.putExtra("PLANT", PLANT);
            intent.putExtra("PLANTTXT", PLANTTXT);
            intent.putExtra("WAREHOUSE",WAREHOUSE);
            intent.putExtra("STOCKPLACE", STOCKPLACE);
            startActivity(intent);
            onBackPressed();
        }

       // Ana DEpo Secim
        ayarbtn=findViewById(R.id.ayarbtn);
        ayarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ana_depo_secim.class);
                intent.putExtra("AYARGRUP",AYARGRUP );
                intent.putExtra("COMPANY",COMPANY);
                intent.putExtra("PLANT", PLANT);
                intent.putExtra("PLANTTXT", PLANTTXT);
                intent.putExtra("WAREHOUSE",WAREHOUSE);
                intent.putExtra("STOCKPLACE", STOCKPLACE);
                startActivity(intent);



            }
        });



        deposayim=findViewById(R.id.deposayim);
        deposayim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,deposayim.class);
                intent.putExtra("AYARGRUP",AYARGRUP );
                intent.putExtra("COMPANY",COMPANY);
                intent.putExtra("PLANT", PLANT);
                intent.putExtra("PLANTTXT", PLANTTXT);
                intent.putExtra("WAREHOUSE",WAREHOUSE);
                intent.putExtra("STOCKPLACE", STOCKPLACE);
                intent.putExtra("TARIH", TARIH);
                intent.putExtra("ATUR", ATUR);
                startActivity(intent);
            }
        });


        btn_exit=findViewById(R.id.exitbtn);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        ///calendar = Calendar.getInstance();
        //int day = calendar.get(Calendar.DAY_OF_MONTH);
        //int month = calendar.get(Calendar.MONTH);
        //int year = calendar.get(Calendar.YEAR);

        TARIH=sdf.format(Calendar.getInstance().getTime());
        takvimBtn=findViewById(R.id.takvimBtn);
        takvimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd.MM.yyyy: " + day + "." + month + "." + year);

                String date = numf.format(day) + "." + numf.format(month) + "." + year;
                TARIH = date;
                tarihTxt.setText(TARIH);

            }
        };

        // DEPOYA PALET GİRİŞ BUTONU
        btn_plttransfer=findViewById(R.id.plttransfer);
        btn_plttransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AYARGRUP,COMPANY,PLANT,WAREHOUSE,STOCKPLACE
                Intent intent = new Intent(getApplicationContext(), transfer_depo_secim.class);
                intent.putExtra("AYARGRUP",AYARGRUP );
                intent.putExtra("COMPANY",COMPANY);
                intent.putExtra("PLANT", PLANT);
                intent.putExtra("PLANTTXT", PLANTTXT);
                intent.putExtra("WAREHOUSE",WAREHOUSE);
                intent.putExtra("STOCKPLACE", STOCKPLACE);
                intent.putExtra("TARIH", TARIH);
                intent.putExtra("BILTEKPARAM", BILTEKPARAM);
                intent.putExtra("ATUR", ATUR);
                startActivity(intent);
            }
        });

    }




    private Handler mHandler = new Handler();

    private void startTime() {
        konum=0;
        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            // buraya ne yapmak istiyorsan o kodu yaz.. Kodun sonlandıktan sonra 1 saniye sonra tekrar çalışacak şekilde handler tekrar çalışacak.
           konum=konum+10;
           if (konum>500){
               konum=0;
           }
            mHandler.postDelayed(this, 100);
        }
    };
    public String getDeviceUniqueID(Activity activity){
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

   public void onBackPressed(){
        super.onBackPressed();

   }
}