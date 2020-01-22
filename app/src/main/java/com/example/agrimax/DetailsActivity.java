package com.example.agrimax;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref;

    EditText t1;
    EditText t2;
    EditText t3, t4, t5;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        t1 = (EditText) findViewById(R.id.editText);
        t2 = (EditText) findViewById(R.id.editText2);
        t3 = (EditText) findViewById(R.id.editText3);
        t4 = (EditText) findViewById(R.id.editText4);
        t5 = (EditText) findViewById(R.id.editText5);
        b1 = (Button) findViewById(R.id.btnlogin);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    public boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    private void addUser() {
        final String dist = t1.getText().toString().trim();
        final String yr = t2.getText().toString().trim();
        final String ssn = t3.getText().toString().trim();
        final String crp = t4.getText().toString().trim();
        final String area = t5.getText().toString().trim();

        //final Register register=new Register(name, pass1);

        if (dist.length() == 0) {
            t1.setError("This field should not be empty");
        }
        else if(yr.length() == 0){
            t1.setError("This field should not be empty");
        }
        else if(ssn.length() == 0){
            t1.setError("This field should not be empty");
        }
        else if(crp.length() == 0){
            t1.setError("This field should not be empty");
        }
        else if(area.length() == 0){
            t1.setError("This field should not be empty");
        }
        else {
            if (isInternetConnection()) {
                final ProgressDialog Dialog=new ProgressDialog(DetailsActivity.this);
                Dialog.setMessage("Loading");
                Dialog.show();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent intent=getIntent();
                        String name = intent.getStringExtra("child");
                        String pass = intent.getStringExtra("pass");
                        final Register register=new Register(name, pass);
                        final Details details=new Details(dist, yr, ssn, crp, area);
                        Dialog.hide();

                        database= FirebaseDatabase.getInstance();
                        ref=database.getReference();
                        ref.child(name).setValue(register);
                        ref.child(name).child("Details").setValue(details);
                        Intent intent1=new Intent(DetailsActivity.this, OutputActivity.class);
                        startActivity(intent1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                Toast toast = (Toast) Toast.makeText(DetailsActivity.this, "Please check your internet connction", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
