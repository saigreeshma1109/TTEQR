package com.example.tteqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.tteqr.models.TrainModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BookingActivity extends AppCompatActivity {

    SharedPreferences sp;
    TrainModel model;
    Spinner to,from;
    Button button;
    String trainName,timings,price;
    //int seats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        button=findViewById(R.id.book);


        sp = getSharedPreferences("myPref", MODE_PRIVATE);
        String trainNo = sp.getString("trainNo","");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("train");
        db.child(trainNo).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(TrainModel.class);
                List<String> path = model.getPath();
                trainName=model.getTrain_name();
                timings=model.getTimings();
                price=model.getFare().get(0);
                setPath(path);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button.setOnClickListener(view->{
            String fromS=from.getSelectedItem().toString();
            String toS=to.getSelectedItem().toString();
            Intent intent = new Intent(BookingActivity.this,DetailsActivity.class);
            intent.putExtra("name",trainName);
            intent.putExtra("number",trainNo);
            intent.putExtra("timings",timings);
            intent.putExtra("price",price);
            startActivity(intent);
        });
    }

    private void setPath(List<String> path) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,path);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        to.setAdapter(adapter);
        from.setAdapter(adapter);
    }

}