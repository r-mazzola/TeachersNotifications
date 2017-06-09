package com.project.rm.teachersnotifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView tvMsg;
    EditText etMsg;
    Button btSend;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMsg =(TextView)findViewById(R.id.tvMsg);
        etMsg=(EditText)findViewById(R.id.etMsg);
        btSend =(Button)findViewById(R.id.btSend);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                tvMsg.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etMsg.getText().toString();
                mConditionRef.setValue(msg);
            }
        });


    }
}
