package com.kruzr.preemptive.healthwatcher;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Primary extends AppCompatActivity {

    private String user;
    private int p;
    private final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 866;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        ImageButton HeartRate = (ImageButton)this.findViewById(R.id.HR);
        ImageButton BloodPressure = (ImageButton)this.findViewById(R.id.BP);
        ImageButton Ox2 = (ImageButton)this.findViewById(R.id.O2);
        ImageButton RRate = (ImageButton)this.findViewById(R.id.RR);
        ImageButton VitalSigns = (ImageButton)this.findViewById(R.id.VS);
        ImageButton Abt = (ImageButton)this.findViewById(R.id.About);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = "Test" ;
//            extras.getString("Usr");
            //The key argument here must match that used in the other activity
        }

        Abt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),AboutApp.class);
                startActivity(i);
                finish();
            }
        });


        //Every Test Button sends the username + the test number, to go to the wanted test after the instructions activity
        HeartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScannerActivity(1,v);
            }
        });

        BloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScannerActivity(2,v);
            }
        });

        RRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScannerActivity(3,v);
            }
        });

        Ox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScannerActivity(4,v);

            }
        });

        VitalSigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScannerActivity(5,v);
            }
        });

    }


    public void startScannerActivity(int page,View v){
        p = page;
        if (ContextCompat.checkSelfPermission(Primary.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(v.getContext(),StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", page);
            startActivity(i);
            finish();
        }else{
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Primary.this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(Primary.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_READ_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        Primary.super.onBackPressed();
                        finish();
                        System.exit(0);
                    }
                }).create().show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_PERMISSIONS_REQUEST_READ_CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getBaseContext(),"You can now press again.",Toast.LENGTH_LONG).show();
        }
    }

    public void openGithub(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/AmitRohan/HealthWatcher"));
        startActivity(browserIntent);
    }
}

