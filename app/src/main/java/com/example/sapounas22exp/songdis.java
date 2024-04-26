package com.example.sapounas22exp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class songdis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_songdis);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ssdis), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final boolean[] voi = {true};
        final ImageButton ppb = findViewById(R.id.PLayPauseb2);
        ppb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voi[0] ==true){
                    ppb.setImageResource(android.R.drawable.ic_media_pause);
                    voi[0] =false;
                }else {
                    ppb.setImageResource(android.R.drawable.ic_media_play);
                    voi[0] =true;
                }
            }
        });
    }
}