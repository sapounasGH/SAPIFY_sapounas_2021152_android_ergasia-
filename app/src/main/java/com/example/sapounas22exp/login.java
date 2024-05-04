package com.example.sapounas22exp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String username, password, music_taste,Name,Surrname,avatar;



    public login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment login.
     */
    // TODO: Rename and change types and number of parameters
    public static login newInstance(String param1, String param2) {
        login fragment = new login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    EditText ed1,ed2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ed1 = view.findViewById(R.id.username);
        ed2 = view.findViewById(R.id.password);
        Button btn = view.findViewById(R.id.login);
        db= FirebaseFirestore.getInstance();
        db.collection("Users").document("22").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            username = documentSnapshot.getString("Username");
                            password = documentSnapshot.getString("Password");
                            music_taste= documentSnapshot.getString("Musitaste");
                            Name= documentSnapshot.getString("Name");
                            Surrname= documentSnapshot.getString("Surrname");
                            avatar= documentSnapshot.getString("avatar");
                        } else {
                            Log.d(TAG, "Document does not exist");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error getting document", e);
                    }
                });

        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView textView1 = headerView.findViewById(R.id.name);
        TextView textView2 = headerView.findViewById(R.id.surrname);
        TextView textView3 = headerView.findViewById(R.id.UserName);
        TextView textView4 = headerView.findViewById(R.id.MusicTaste);
        ImageView imageView = headerView.findViewById(R.id.avatar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = ed1.getText().toString();
                String pass = ed2.getText().toString();
                if (usr.equals(username) && pass.equals(password)){
                    Toast.makeText(getActivity(),"You are logged in ! ",Toast.LENGTH_LONG).show();
                    textView1.setText("Name:  "+Name);
                    textView2.setText("Surrname:  "+Surrname);
                    textView3.setText("Username:  "+username);
                    textView4.setText("Music Taste:  "+music_taste);
                    if(avatar.equals("rihana")){
                        imageView.setImageResource(R.drawable.rihana);
                    }
                    MenuItem menuItem = navigationView.getMenu().findItem(R.id.acc);
                    menuItem.setEnabled(false);
                }else {
                    Toast.makeText(getActivity(),"wrong username or password",Toast.LENGTH_LONG).show();
                }
            }
        });

    return view;
    }
}