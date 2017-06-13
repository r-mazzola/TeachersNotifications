package com.project.rm.teachersnotifications;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView tvMsg;
    EditText etMsg;
    Button btSend;
    private String cCorso;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    //DatabaseReference mMessRef = mAutomaticaRef.child("Messaggio");
    //DatabaseReference mSisopRef = mRootRef.child("Sistemi Operativi");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMsg =(TextView)findViewById(R.id.tvMsg);
        etMsg=(EditText)findViewById(R.id.etMsg);
        btSend =(Button)findViewById(R.id.btSend);


    }

    private void Corso_selected(String t){cCorso=t;}

    @Override
    protected void onStart() {
        super.onStart();

        //SPINNER SELEZIONE CORSI
        Spinner sp=(Spinner) findViewById(R.id.spCorso);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
                String selected = (String)adapter.getItemAtPosition(pos);
                Corso_selected(selected);
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });


        /*mMessRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                tvMsg.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etMsg.getText().toString();
                if(msg.equals("")){
                    hideKeyboard();
                    Toast.makeText(getApplicationContext(),"Inserire un messaggio!",Toast.LENGTH_LONG).show();
                }else{

                    DatabaseReference mNomeCorsoRef = mRootRef.child("NomeCorso");
                    DatabaseReference mCorsoRef = mRootRef.child(cCorso);
                    DatabaseReference mMessRef = mCorsoRef.child("Messaggio");
//                    mNomeCorsoRef.setValue(cCorso);
                    mMessRef.setValue(msg);
                    etMsg.getText().clear();
                    etMsg.clearFocus();
                    hideKeyboard();
                    Toast.makeText(getApplicationContext(),"\""+cCorso+": "+msg+"\" inviato",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void hideKeyboard(){
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
