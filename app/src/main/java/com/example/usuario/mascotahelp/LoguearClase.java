package com.example.usuario.mascotahelp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class LoguearClase extends AppCompatActivity {

    public static final String USER_NAME = "USERNAME";
    public  static final int ID_USER =0;
    Button loguear1;
    EditText nombre,contrasenia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguear_clase);

        loguear1=(Button) findViewById(R.id.btnIngresar);
        nombre=(EditText) findViewById(R.id.txtUsu);
        contrasenia=(EditText) findViewById(R.id.txtPass);

        loguear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TareaLoguearse tarea1= new TareaLoguearse();
                tarea1.execute(nombre.getText().toString(),contrasenia.getText().toString());
            }
        });


    }

    private class TareaLoguearse extends AsyncTask<String, Integer, Boolean> {

        private String nombCli;
        private int id_usuario;
        private Dialog loadingDialog;
        private String ID_USU = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(LoguearClase.this, "Please wait", "Loading...");
        }

        protected Boolean doInBackground(String... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            String nombreU = params[0];
            String contraU = params[1];

            HttpGet del = new HttpGet("http://192.168.56.1:82/PetHelp/logueo.PHP?username="+nombreU+"&password="+contraU);
            del.setHeader("content-type", "application/json");
            try
            {
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONArray respJSON = new JSONArray(respStr);

                nombCli = respJSON.getJSONObject(0).getString("NOMBRE");
                id_usuario = respJSON.getJSONObject(0).getInt("ID_USUARIO");
                // convertir variable id_usuario
                ID_USU = String.valueOf(id_usuario);

            }
            catch(Exception ex)
            {
                Log.e("ServicioRest","Error!", ex);
                resul = false;
            }

            return resul;
        }
        protected void onPostExecute(Boolean result) {
            loadingDialog.dismiss();
            if (result) {
                Toast.makeText(getApplicationContext(),"Bienvenido a PetHelp " + nombCli ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoguearClase.this, ListaMascota.class);
                intent.putExtra(USER_NAME, ID_USU);
                finish();
                startActivity(intent);
                finish();
            }
        }
    }
}
