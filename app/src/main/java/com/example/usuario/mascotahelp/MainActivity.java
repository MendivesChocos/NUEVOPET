package com.example.usuario.mascotahelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnRegistrar, btnLoguearse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrar=(Button) findViewById(R.id.btnRegistrar);
        btnLoguearse=(Button) findViewById(R.id.btnLoguear);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),RegistrarClase.class);
                startActivity(i);

            }
        });
        btnLoguearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),LoguearClase.class);
                startActivity(i);
            }
        });
    }
}
