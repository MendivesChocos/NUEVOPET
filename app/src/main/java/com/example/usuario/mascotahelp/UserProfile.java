package com.example.usuario.mascotahelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {

    String id_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        id_username = intent.getStringExtra(LoguearClase.USER_NAME);
        Toast.makeText(getApplicationContext(),"Bienvenido estas adentro " + id_username ,Toast.LENGTH_SHORT).show();

    }
}
