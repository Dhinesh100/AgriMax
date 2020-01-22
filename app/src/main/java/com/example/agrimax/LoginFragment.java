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

public class LoginFragment extends Fragment {

    EditText t1,t2;
    Button b1;

    FirebaseDatabase database;
    DatabaseReference ref, ref1;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_login, container, false);
        t1=(EditText) rootview.findViewById(R.id.editText);
        t2=(EditText) rootview.findViewById(R.id.editText2);
        b1=(Button) rootview.findViewById(R.id.btnlogin);
        database=FirebaseDatabase.getInstance();

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkUser();
            }
        });
        return rootview;
    }
    private void checkUser()
    {
        final String name=t1.getText().toString().trim();
        final String pass=t2.getText().toString().trim();

        if(name.length()==0)
        {
            t1.setError("User name is required");
        }

        else
        {
            if(pass.length()==0)
            {
                t2.setError("Password is required");
            }

            else
            {
                if(isInternetConnection())
                {
                    ref=database.getReference();
                    ref.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            if(dataSnapshot.hasChild(name))
                            {
                                ref1=database.getReference(name);
                                ref1.child("pass").addListenerForSingleValueEvent(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        String pass1=dataSnapshot.getValue(String.class);

                                        if(pass1.equals(pass))
                                        {
                                            final ProgressDialog Dialog=new ProgressDialog(getActivity());
                                            Dialog.setMessage("Loading");
                                            Dialog.show();
                                            Intent intent=new Intent(getActivity(), DetailsActivity.class);
                                            startActivity(intent);
                                            Dialog.hide();
                                        }

                                        else
                                        {
                                            t2.setError("Incorrect Password");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError)
                                    {

                                    }
                                });
                            }

                            else
                            {
                                t1.setError("Incorrect User Name");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });
                }

                else
                {
                    Toast toast= Toast.makeText(getActivity(), "Please check your internet connction", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    public boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)  getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED)
        {
            return true;
        }

        else
        {
            return false;
        }
    }
}