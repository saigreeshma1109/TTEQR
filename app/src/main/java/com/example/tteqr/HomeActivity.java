package com.example.tteqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tteqr.models.TteModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    TextView logOut,checkin,checkout,end,ticket,bookticket,no;
    ImageView logOutI,checkinI,checkoutI,endI,ticketI,bookticketI;
    SharedPreferences sp;
    String trainNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp = getSharedPreferences("myPref", MODE_PRIVATE);
        trainNo = sp.getString("trainNo","");
        if(trainNo.equals("")){
            Intent intent = new Intent(HomeActivity.this,TrainVerificationActivity.class);
            startActivity(intent);
            finish();
        }
        findViews();
    }
    @Override
    public void onClick(View v) {
        if(v==logOut||v==logOutI) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if(v==bookticket||v==bookticketI){
            startActivity(new Intent(getApplicationContext(),BookingActivity.class));
        }
        else if(v==end||v==endI){
            SharedPreferences.Editor editor=sp.edit();
            editor.remove("trainNo");
            editor.apply();
            FirebaseAuth auth=FirebaseAuth.getInstance();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("tte").child(auth.getCurrentUser().getPhoneNumber());
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            TteModel model = snapshot.getValue(TteModel.class);
                            model.setCurrent(null);
                            db.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }
        else if(v==checkin|| v==checkinI){
            startActivity(new Intent(getApplicationContext(),CheckInActivity.class));
        }
        else if(v==ticket|| v==ticketI){
            startActivity(new Intent(getApplicationContext(),TicketActivity.class));
        }
        else if(v==checkout|| v==checkoutI){
            Toast.makeText(this, "Reserved for future enhancements", Toast.LENGTH_SHORT).show();
        }
    }

    public void findViews(){
        logOut = findViewById(R.id.log_out);
        logOutI = findViewById(R.id.logout);
        checkin=findViewById(R.id.checkIn);
        checkinI = findViewById(R.id.check_in);
        checkout=findViewById(R.id.checkOut);
        checkoutI = findViewById(R.id.check_out);
        end=findViewById(R.id.endJourney);
        endI = findViewById(R.id.end_journey);
        ticket=findViewById(R.id.prevTicket);
        ticketI = findViewById(R.id.prev_ticket);
        bookticket=findViewById(R.id.bookTicket);
        bookticketI = findViewById(R.id.book_ticket);
        no = findViewById(R.id.no);
        no.setText(trainNo);
        addListeners();
    }
    private void addListeners() {
        logOut.setOnClickListener(this);
        logOutI.setOnClickListener(this);
        checkin.setOnClickListener(this);
        checkinI.setOnClickListener(this);
        checkout.setOnClickListener(this);
        checkoutI.setOnClickListener(this);
        end.setOnClickListener(this);
        endI.setOnClickListener(this);
        ticket.setOnClickListener(this);
        ticketI.setOnClickListener(this);
        bookticket.setOnClickListener(this);
        bookticketI.setOnClickListener(this);
    }
}