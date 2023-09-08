package red.lisgar.proyecto.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.PuntoRecarga;
import red.lisgar.proyecto.interfaces.PuntosInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActualizarPuntoActivity extends AppCompatActivity {

    EditText nombreActualizarPunto;
    EditText ubicacionActualizarPunto;
    EditText mapaActualizarPunto;
    Button btnActualizarPunto;
    Button btnEliminarPunto;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    SharePreference sHarePreference;
    ImageView imgBarra;
    PuntoRecarga punto;
    PuntosInterface interfaces;
    String id;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    Intents inte = new Intents(AdminActualizarPuntoActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_actualizar_punto);
        btnRutas = findViewById(R.id.btnRutas);
        btnParadas = findViewById(R.id.btnParadas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnUsuarios = findViewById(R.id.btnUser);
        btnCupos = findViewById(R.id.btnCupos);

        nombreActualizarPunto = findViewById(R.id.nombreActualizarPunto);
        ubicacionActualizarPunto = findViewById(R.id.ubicacionActualizarPunto);
        mapaActualizarPunto = findViewById(R.id.mapaActualizarPunto);
        btnActualizarPunto = findViewById(R.id.btnActualizarPunto);
        btnEliminarPunto = findViewById(R.id.btnEliminarPunto);

        punto = new PuntoRecarga();
        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        imgBarra = findViewById(R.id.imgBarra);
        imgBarra.setImageResource(R.drawable.admin);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        rolToolbar.setText(sHarePreference.getNombre());
        nombreToolbar.setText(sHarePreference.getCorreo());

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.adminPuntos();
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

        btnActualizarPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreActualizarPunto.getText().toString().trim();
                String ubicacion = ubicacionActualizarPunto.getText().toString().trim();
                String mapa = mapaActualizarPunto.getText().toString().trim();
                boolean correcto = false;

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(ubicacion) && !TextUtils.isEmpty(mapa)) {
                    //SE MODIFICA
                    punto.setId(id);
                    punto.setNombre(nombre);
                    punto.setUbicacion(ubicacion);
                    punto.setMapa(mapa);
                    editar(punto);
                    limpiar();
                }else {
                    Toast.makeText(AdminActualizarPuntoActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnEliminarPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(AdminActualizarPuntoActivity.this);
                alerta.setMessage("¿Desea eliminar Punto De Recarga?:")
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
        interfaces = retrofit.create(PuntosInterface.class);
        Call<PuntoRecarga> call = interfaces.getOne(id);
        call.enqueue(new Callback<PuntoRecarga>() {
            @Override
            public void onResponse(Call<PuntoRecarga> call, Response<PuntoRecarga> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                PuntoRecarga ver = response.body();

                if(ver != null){
                    nombreActualizarPunto.setText(ver.getNombre());
                    ubicacionActualizarPunto.setText(ver.getUbicacion());
                    mapaActualizarPunto.setText(ver.getMapa());
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ENCONTRAR LA RUTA", Toast.LENGTH_LONG);
                    toast.show();
                    inte.adminPuntos();
                }

            }

            @Override
            public void onFailure(Call<PuntoRecarga> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    private void editar(PuntoRecarga u)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(PuntosInterface.class);
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
                    inte.adminPuntos();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "EÑ ÚNTO DE RECARGA SE ENCUENTRA EN USO", Toast.LENGTH_LONG);
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
        interfaces = retrofit.create(PuntosInterface.class);
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
                    inte.adminPuntos();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ELIMINAR EL PUNTO DE RECARGA", Toast.LENGTH_LONG);
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
        nombreActualizarPunto.setText("");
        ubicacionActualizarPunto.setText("");
        mapaActualizarPunto.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}