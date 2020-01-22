package com.example.agrimax;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference ref;

    EditText t1;
    EditText t2;
    EditText t3;
    Button b1;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_signup, container, false);
        t1 = (EditText) rootview.findViewById(R.id.editText);
        t2 = (EditText) rootview.findViewById(R.id.editText2);
        t3 = (EditText) rootview.findViewById(R.id.editText3);
        b1 = (Button) rootview.findViewById(R.id.btnlogin);

        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
        return rootview;
    }

    public boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    private void addUser() {
        final String name = t1.getText().toString().trim();
        final String pass1 = t2.getText().toString().trim();
        final String pass2 = t3.getText().toString().trim();

        //final Register register=new Register(name, pass1);

        if (name.length() == 0) {
            t1.setError("User name is required");
        } else {
            if (isInternetConnection()) {
                final ProgressDialog Dialog=new ProgressDialog(getActivity());
                Dialog.setMessage("Loading");
                Dialog.show();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(name)) {
                            t1.setError("User name already exists");
                        } else {
                            if (pass1.length() == 0) {
                         t2.setError("This field should not be empty");
                            } else if (pass2.length() == 0) {
                                t3.setError("This field should not be empty");
                            } else if (pass1.equals(pass2)) {
                                //ref.child(name).setValue(register);
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                intent.putExtra("child", name);
                                intent.putExtra("pass", pass1);
                                startActivity(intent);
                            } else {
                                t3.setError("Password is not matching");
                            }
                        }
                        Dialog.hide();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                Toast toast = (Toast) Toast.makeText(getActivity(), "Please check your internet connction", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}