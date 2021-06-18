package com.example.tteqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TrainVerificationActivity extends AppCompatActivity {

    EditText trainNo;
    Button login;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_verification);
        trainNo = findViewById(R.id.trainNo);
        login = findViewById(R.id.login);
        sp = getSharedPreferences("myPref",MODE_PRIVATE);
        String no = sp.getString("trainNo","");
        if(!no.equals("")){
            Intent intent = new Intent(TrainVerificationActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        login.setOnClickListener(v -> {
            String trainNoS = trainNo.getText().toString().trim();
            if(trainNoS.length()==5){
                Intent intent = new Intent(TrainVerificationActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("trainNo",trainNoS);
                editor.apply();
                startActivity(intent);
            }
            else{
                Toast.makeText(TrainVerificationActivity.this,"Invalid Train number",Toast.LENGTH_SHORT).show();
            }
        });
    }
}