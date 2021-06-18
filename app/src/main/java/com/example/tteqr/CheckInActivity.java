package com.example.tteqr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class CheckInActivity extends AppCompatActivity {

    IntentIntegrator ii;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        ii=new IntentIntegrator(this);
        ii.setPrompt("Scan the QR Code");
        ii.setOrientationLocked(true);
        ii.setCaptureActivity(CapturePortrait.class);

        ii.setBeepEnabled(false);
        ii.initiateScan();
    }

    //@Override
    /*protected void onRestart() {
        super.onRestart();
        this.recreate();
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Failed to scan.Re-scan", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent=new Intent(CheckInActivity.this,TicketDetailsActivity.class);
                intent.putExtra("QR",result.getContents());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}