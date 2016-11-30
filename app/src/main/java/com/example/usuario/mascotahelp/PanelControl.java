package com.example.usuario.mascotahelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PanelControl extends AppCompatActivity {

    String id_pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_control);

        Intent intent = getIntent();
        id_pet = intent.getStringExtra(ListaMascota.USER_NAME);


    }
}
