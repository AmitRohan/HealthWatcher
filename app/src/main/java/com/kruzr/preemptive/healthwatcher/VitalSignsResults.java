package com.kruzr.preemptive.healthwatcher;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VitalSignsResults extends AppCompatActivity {

    private String user,Date;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
    int VBP1,VBP2,VHR,VO2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs_results);

        Date = df.format(today);
        TextView VSBPS = (TextView) this.findViewById(R.id.BP2V);
        TextView VSHR = (TextView) this.findViewById(R.id.HRV);
        TextView VSO2 = (TextView) this.findViewById(R.id.O2V);
        TextView Vdiagnosis = (TextView) this.findViewById(R.id.diagnosis);

        ImageButton All = (ImageButton)this.findViewById(R.id.SendAll);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            VHR = bundle.getInt("bpm");
            VBP1 = bundle.getInt("SP");
            VBP2 = bundle.getInt("DP");
            VO2 = bundle.getInt("O2R");
            VSHR.setText(String.valueOf(VHR));
            VSBPS.setText(String.valueOf(VBP1+" / "+VBP2));
            VSO2.setText(String.valueOf(VO2));


            String healthCondition = "";
             if(VO2 < 93){
                healthCondition = "High risk for Pneumonia and ARDS. consult doctor immediately.";
            } else if(VHR > 92 && VO2 < 95){
                 healthCondition = "Mild Conditions for Pneumonia and ARDS. Should get a checkup done";
             } else if(VO2 < 95){
                 healthCondition = "Running low on oxygen, might develop ARDS. Should get a checkup done";
             } else if(VHR < 60 || VHR > 90){
                 healthCondition = "Abnormal heart rate. Take some rest and check in 15 minutes.";
             } else {
                Vdiagnosis.setVisibility(View.GONE);
            }
            Vdiagnosis.setText(healthCondition);
        }

        All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Health Watcher");
                i.putExtra(Intent.EXTRA_TEXT   , "My new measuerment "+"\n"+" at "+ Date +" are :"+"\n"+"Heart Rate = "+VHR+"\n"+"Blood Pressure = "+VBP1+" / "+VBP2+"\n"+"Oxygen Saturation = "+VO2);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(VitalSignsResults.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent i = new Intent(VitalSignsResults.this, Primary.class);
            i.putExtra("Usr", user);
            startActivity(i);
            finish();
        }
    }
