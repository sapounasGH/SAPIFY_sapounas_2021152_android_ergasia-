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
import com.google.firebase.firestore.QueryDocumentSnapshot;

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

    static String username;
    String password;
    static String music_taste;
    String Name;
    String Surrname;
    String avatar;



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
    boolean flag=false;
    String documentPath=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ed1 = view.findViewById(R.id.username);
        ed2 = view.findViewById(R.id.password);
        Button btn = view.findViewById(R.id.login);
        db= FirebaseFirestore.getInstance();


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
                    db.collection("Users")
                            .whereEqualTo("username", usr)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        db.collection("Users")
                                                .whereEqualTo("password", pass)
                                                .get()
                                                .addOnCompleteListener(task2 -> {
                                                    if (task2.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document2 : task2.getResult()) {
                                                            documentPath = document2.getId();
                                                            Log.d(TAG, documentPath);
                                                            db.collection("Users").document(""+documentPath).get()
                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                            if (documentSnapshot.exists()) {
                                                                                username = documentSnapshot.getString("username");
                                                                                password = documentSnapshot.getString("password");
                                                                                music_taste= documentSnapshot.getString("musitaste");
                                                                                Name= documentSnapshot.getString("name");
                                                                                Surrname= documentSnapshot.getString("surrname");
                                                                                avatar= documentSnapshot.getString("avatar");
                                                                                Toast.makeText(getActivity(),"You are logged in!",Toast.LENGTH_LONG).show();
                                                                                textView1.setText("Name: " + Name);
                                                                                textView2.setText("Surrname: "+Surrname);
                                                                                textView3.setText("Username: "+username);
                                                                                textView4.setText("MusicTaste: "+music_taste);
                                                                                if(avatar.equals("Rihana")){
                                                                                    imageView.setImageResource(R.drawable.rihana);
                                                                                }else if(avatar.equals("Kanye")){
                                                                                    imageView.setImageResource(R.drawable.kanye);
                                                                                }
                                                                                else if(avatar.equals("EdSherean")){
                                                                                    imageView.setImageResource(R.drawable.edshe);
                                                                                }
                                                                                else if(avatar.equals("Adele")){
                                                                                    imageView.setImageResource(R.drawable.adele);
                                                                                }
                                                                                MenuItem menuItem1=navigationView.getMenu().findItem(R.id.acc);
                                                                                menuItem1.setEnabled(false);
                                                                                MenuItem menuItem2=navigationView.getMenu().findItem(R.id.Search);
                                                                                menuItem2.setEnabled(true);
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


                                                            break;
                                                        }
                                                    } else {
                                                        Toast.makeText(getActivity(),"Wrong Password :(",Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                        break;
                                    }
                                } else {
                                    Toast.makeText(getActivity(),"Wrong Username or Password :(",Toast.LENGTH_LONG).show();
                                }
                            });
            }
        });
    return view;
    }
}