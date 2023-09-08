package red.lisgar.proyecto.admin;

import androidx.appcompat.app.AppCompatActivity;

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

public class AdminAgregarPuntosActivity extends AppCompatActivity {


    //LAYOUT
    EditText nombreAgregarPunto;
    EditText ubicacionAgregarPunto;
    EditText mapaAgregarPunto;
    Button btnAgregarPunto;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;

    //LLAMAR CLASES
    PuntoRecarga punto;
    SharePreference sHarePreference;
    ImageView imgBarra;
    PuntosInterface interfaces;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    Intents inte = new Intents(AdminAgregarPuntosActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_agregar_puntos);
        btnRutas = findViewById(R.id.btnRutas);
        btnParadas = findViewById(R.id.btnParadas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnUsuarios = findViewById(R.id.btnUser);
        btnCupos = findViewById(R.id.btnCupos);

        //FINDVIEWBYID
        nombreAgregarPunto = findViewById(R.id.nombreAgregarPunto);
        ubicacionAgregarPunto = findViewById(R.id.ubicacionAgregarPunto);
        mapaAgregarPunto = findViewById(R.id.mapaAgregarPunto);
        btnAgregarPunto = findViewById(R.id.btnAgregarPunto);

        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        imgBarra = findViewById(R.id.imgBarra);
        imgBarra.setImageResource(R.drawable.admin);
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

        //AGREGAR LIBRO
        btnAgregarPunto = findViewById(R.id.btnAgregarPunto);
        punto = new PuntoRecarga();

        btnAgregarPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreAgregarPunto.getText().toString().trim();
                String ubicacion = ubicacionAgregarPunto.getText().toString().trim();
                String mapa = mapaAgregarPunto.getText().toString().trim();

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(ubicacion) && !TextUtils.isEmpty(mapa)) {
                    //SE MODIFICA}
                    punto.setNombre(nombre);
                    punto.setUbicacion(ubicacion);
                    punto.setMapa(mapa);
                    agregar(punto);
                    limpiar();
                }else {
                    Toast.makeText(AdminAgregarPuntosActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void agregar(PuntoRecarga u)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(PuntosInterface.class);
        Call<String> call = interfaces.save(u);
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

                if(save.equals("guardado")){
                    Toast toast = Toast.makeText(getApplication(), "REGISTRO SATISFACTORIO", Toast.LENGTH_LONG);
                    toast.show();
                    inte.adminPuntos();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "EL PUNTO DE RECARGA SE ENCUENTRA EN USO", Toast.LENGTH_LONG);
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

    private void limpiar(){
        nombreAgregarPunto.setText("");
        ubicacionAgregarPunto.setText("");
        mapaAgregarPunto.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}