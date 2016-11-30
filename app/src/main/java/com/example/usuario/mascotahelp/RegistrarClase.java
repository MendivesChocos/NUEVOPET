package com.example.usuario.mascotahelp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class RegistrarClase extends AppCompatActivity {

    Button registrarUsuario;
    EditText nombre,contrasenia;


    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "Registro correctamente la informacion", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_clase);

        registrarUsuario=(Button) findViewById(R.id.regusuari);
        nombre=(EditText) findViewById(R.id.txtnombre);
        contrasenia=(EditText) findViewById(R.id.txtcontra);

        registrarUsuario.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            // TODO Auto-generated method stub
                                                            httpGetData("http://192.168.56.1:82/PetHelp/registro.php?username="+nombre.getText().toString()+"&password="+contrasenia.getText().toString());
                                                            h.sendEmptyMessage(1);
                                                        }
                                                    }).start();
                                                }
                                            }
        );

    }

    public String httpGetData(String mURL){
        String response="";
        mURL= mURL.replace(" ", "%20");
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(mURL);
        ResponseHandler<String> responsehandler = new BasicResponseHandler();
        try {
            response = httpclient.execute(httpget, responsehandler);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }
}
