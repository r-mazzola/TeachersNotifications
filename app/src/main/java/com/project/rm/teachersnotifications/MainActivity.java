package com.project.rm.teachersnotifications;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
//    TextView tvMsg;
    EditText etMsg;
    Spinner spCorso;
    Button btSend;
    ListView lvMessaggi;

    DatabaseReference databaseMessages;

    List<Messaggio> messaggioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMsg=(EditText)findViewById(R.id.etMsg);
        spCorso =(Spinner) findViewById(R.id.spCorso);
        btSend =(Button)findViewById(R.id.btSend);
        lvMessaggi=(ListView)findViewById(R.id.lvMessaggi);
        messaggioList=new ArrayList<>();


        databaseMessages = FirebaseDatabase.getInstance().getReference("messages");

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMessage();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseMessages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messaggioList.clear();
                for(DataSnapshot messageSnapshot : dataSnapshot.getChildren()){
                    Messaggio messaggio = messageSnapshot.getValue(Messaggio.class);
                    messaggioList.add(messaggio);
                }

                MessageList adapter = new MessageList(MainActivity.this, messaggioList);
                lvMessaggi.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void addMessage(){
        String testo = etMsg.getText().toString().trim();
        String corso = spCorso.getSelectedItem().toString();
        String timestamp = getDate();
        if(!TextUtils.isEmpty(testo)){
            String id = databaseMessages.push().getKey();
            Messaggio messaggio = new Messaggio(id, corso, testo, timestamp);
            databaseMessages.child(id).setValue(messaggio);
            etMsg.getText().clear();
            etMsg.clearFocus();
            hideKeyboard();
            Toast.makeText(this,"\""+corso+": "+testo+"\" inviato alle "+timestamp, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Inserire un messaggio", Toast.LENGTH_LONG).show();
        }
    }

    //METODO CALENDAR PER LA DATA
    public String getDate(){
        Calendar rightNow = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        rightNow.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
        int day_of_the_month = rightNow.get(Calendar.DAY_OF_MONTH);
        //AGGIUNGO UN UNO PERCHE' L'INDICIZZAZIONE DEI MESI PARTE DA 0 -.-
        int month = rightNow.get(Calendar.MONTH)+1;
        int year = rightNow.get(Calendar.YEAR);
        int hours = rightNow.get(Calendar.HOUR_OF_DAY);
        int minutes = rightNow.get(Calendar.MINUTE);
//        int seconds = rightNow.get(Calendar.SECOND);
//        String DATA = "" + day_of_the_month+"/"+month+"/"+ year+" "+ hours+":"+minutes+":"+seconds;
        String DATA = "" + day_of_the_month+"/"+month+"/"+ year+" "+ hours+":"+((minutes<10)?"0"+minutes:minutes);
        return DATA;
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
