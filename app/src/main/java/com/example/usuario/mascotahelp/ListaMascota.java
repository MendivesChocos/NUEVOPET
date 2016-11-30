package com.example.usuario.mascotahelp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListaMascota extends AppCompatActivity {

    private ListView listView;
    public static final String USER_NAME = "USERNAME"
    ArrayList titulo = new ArrayList();
    ArrayList mascota = new ArrayList();
    ArrayList raza = new ArrayList();
    ArrayList imagen = new ArrayList();
    private String ID_HELP = "";
    String id_username;
    Button RegistrarMascota, Atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascota);


        listView = (ListView) findViewById(R.id.listView);
        RegistrarMascota = (Button) findViewById(R.id.btnRegPet);
        Atras = (Button) findViewById(R.id.btnAtrasList);

        RegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaMascota.this, RegistrarMascota.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        id_username = intent.getStringExtra(LoguearClase.USER_NAME);
        Toast.makeText(getApplicationContext(),"Bienvenido estas adentro " + id_username ,Toast.LENGTH_SHORT).show();

        descargarImagen(id_username);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ID_HELP = String.valueOf(i);

                Intent intent = new Intent(ListaMascota.this, PanelControl.class);
                intent.putExtra(USER_NAME, ID_HELP);
                finish();
                startActivity(intent);
                finish();
            }
        });

    }

    private void descargarImagen(String usuario) {
       // titulo.clear();
        mascota.clear();
        raza.clear();
        imagen.clear();

        // se ve el usuario que se esta psando por parametro
        String id = usuario;

        final  ProgressDialog loadingDialog = new ProgressDialog(ListaMascota.this);
        loadingDialog.setMessage("CARGANDO DATOS");
        loadingDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.56.1:82/PetHelp/listPets.php?username="+id, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    loadingDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String (responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //titulo.add(jsonArray.getJSONObject(i).getString("NOMBRE"));
                            mascota.add(jsonArray.getJSONObject(i).getString("NOMBRE"));
                            raza.add(jsonArray.getJSONObject(i).getString("RAZA"));
                            imagen.add(jsonArray.getJSONObject(i).getString("FOTO"));

                        }
                        listView.setAdapter(new ImagenAdapter(getApplicationContext()));

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private class ImagenAdapter extends BaseAdapter{


        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView nombre,razatxt;

        public ImagenAdapter(Context applicationContext) {
            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return imagen.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.activity_main_item,null);

            smartImageView = (SmartImageView) viewGroup.findViewById(R.id.imagenPerfil);
            nombre = (TextView) viewGroup.findViewById(R.id.txtTitulo);
            razatxt = (TextView) viewGroup.findViewById(R.id.txtDescripcion);

            String urlFinal = "http://192.168.56.1:82/PetHelp/imagenes/" + imagen.get(i).toString();

            Rect rect = new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());

            smartImageView.setImageUrl(urlFinal,rect);

            nombre.setText(mascota.get(i).toString());
            razatxt.setText(raza.get(i).toString());

            return viewGroup;
        }
    }
}
