package red.lisgar.proyecto.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.Parada;
import red.lisgar.proyecto.entidades.Ruta;
import red.lisgar.proyecto.interfaces.ParadaInterface;
import red.lisgar.proyecto.interfaces.RutaInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActualizarParadaActivity extends AppCompatActivity {

    EditText ubicacionActualizarParada;
    EditText mapaActualizarParada;
    Button btnActualizarParda;
    Button btnEliminarParada;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    SharePreference sHarePreference;
    ImageView imgBarra;
    Parada parada;
    ParadaInterface interfaces;
    RutaInterface interfacesR;
    String id;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    Intents inte = new Intents(AdminActualizarParadaActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_actualizar_parada);
        btnRutas = findViewById(R.id.btnRutas);
        btnParadas = findViewById(R.id.btnParadas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnUsuarios = findViewById(R.id.btnUser);
        btnCupos = findViewById(R.id.btnCupos);

        ubicacionActualizarParada = findViewById(R.id.ubicacionActualizarParada);
        mapaActualizarParada = findViewById(R.id.mapaActualizarParada);
        btnActualizarParda = findViewById(R.id.btnActualizarParada);
        btnEliminarParada = findViewById(R.id.btnEliminarParada);

        parada = new Parada();
        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        imgBarra = findViewById(R.id.imgBarra);
        imgBarra.setImageResource(R.drawable.admin);
        rolToolbar.setText(sHarePreference.getNombre());
        nombreToolbar.setText(sHarePreference.getCorreo());

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.adminParadas();
            }
        });
        btnRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.adminRutas();
            }
        });
        btnParadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.adminParadas();
            }
        });
        btnPuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.adminPuntos();
            }
        });
        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.adminUsers();
            }
        });
        btnCupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.adminCupos();
            }
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                id = extras.getString("idi");
            }
        } else {
            id = (String) savedInstanceState.getString("idi");
        }

        ver(id);

        btnActualizarParda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ubicacion = ubicacionActualizarParada.getText().toString().trim();
                String mapa = mapaActualizarParada.getText().toString().trim();
                boolean correcto = false;

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(ubicacion) && !TextUtils.isEmpty(mapa)) {
                    //SE MODIFICA
                    parada.setId(id);
                    parada.setUbicacion(ubicacion);
                    parada.setMapa(mapa);
                    editar(parada);
                    limpiar();
                }else {
                    Toast.makeText(AdminActualizarParadaActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnEliminarParada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(AdminActualizarParadaActivity.this);
                alerta.setMessage("¿Desea eliminar Parada?:")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                eliminar(id);
                                limpiar();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Eliminar");
                titulo.show();
            }
        });
    }


    private void ver(String id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaces = retrofit.create(ParadaInterface.class);
        Call<Parada> call = interfaces.getOne(id);
        call.enqueue(new Callback<Parada>() {
            @Override
            public void onResponse(Call<Parada> call, Response<Parada> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                Parada ver = response.body();

                if(ver != null){
                    ubicacionActualizarParada.setText(ver.getUbicacion());
                    mapaActualizarParada.setText(ver.getMapa());
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ENCONTRAR LA RUTA", Toast.LENGTH_LONG);
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<Parada> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    private void editar(Parada u)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(ParadaInterface.class);
        Call<String> call = interfaces.update(u);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                String save = response.body();

                if(save.equals("actualizado")){
                    Toast toast = Toast.makeText(getApplication(), "REGISTRO SATISFACTORIO", Toast.LENGTH_LONG);
                    toast.show();
                    inte.adminParadas();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "LA PARADA SE ENCUENTRA EN USO", Toast.LENGTH_LONG);
                    toast.show();
                    limpiar();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    private void eliminar(String id)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(ParadaInterface.class);
        Call<String> call = interfaces.delete(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                String ver = response.body();

                if(ver.equals("eliminado")){
                    limpiar();
                    inte.adminParadas();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ELIMINAR LA PARADA", Toast.LENGTH_LONG);
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    private void limpiar(){
        ubicacionActualizarParada.setText("");
        mapaActualizarParada.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}