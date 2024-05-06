package com.example.sapounas22exp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccount extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateAccount() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAccount.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAccount newInstance(String param1, String param2) {
        CreateAccount fragment = new CreateAccount();
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
    EditText ed1,ed2,ed3,ed4,ed5;
    Button b1,b2,b3,b4;
    String pickedAvatar="";
    Button createacc;
    private FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        ed1=view.findViewById(R.id.Nameinput);
        ed2=view.findViewById(R.id.Surrnameinput);
        ed3=view.findViewById(R.id.musicinput);
        ed4=view.findViewById(R.id.Usernameinput);
        ed5=view.findViewById(R.id.passwordinput);
        b1=view.findViewById(R.id.kan);
        b2=view.findViewById(R.id.adel);
        b3=view.findViewById(R.id.es);
        b4=view.findViewById(R.id.riri);
        createacc=view.findViewById(R.id.createaccBUTTON);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickedAvatar="Kanye";
                Toast.makeText(getActivity(),"You picked Kanye West ! ",Toast.LENGTH_LONG).show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickedAvatar="Adele";
                Toast.makeText(getActivity(),"You picked Adele ! ",Toast.LENGTH_LONG).show();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickedAvatar="EdSherean";
                Toast.makeText(getActivity(),"You picked Ed Sherean ! ",Toast.LENGTH_LONG).show();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickedAvatar="Rihana";
                Toast.makeText(getActivity(),"You picked Rihana ! ",Toast.LENGTH_LONG).show();
            }
        });
        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"mpainei edw mesa",Toast.LENGTH_LONG).show();
                FireBaseUser usr= new FireBaseUser();
                usr.setUsername(ed4.getText().toString());
                usr.setSurrname(ed2.getText().toString());
                usr.setMusitaste(ed3.getText().toString());
                usr.setName(ed1.getText().toString());
                usr.setPassword(ed5.getText().toString());
                usr.setAvatar(pickedAvatar);
                db= FirebaseFirestore.getInstance();
                db.collection("Users").add(usr).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Document added with ID: " + task.getResult().getId(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Add operation failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        return view;
    }
}