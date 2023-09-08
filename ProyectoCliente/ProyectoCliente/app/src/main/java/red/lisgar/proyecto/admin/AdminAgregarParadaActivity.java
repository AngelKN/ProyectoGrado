package red.lisgar.proyecto.admin;

import androidx.appcompat.app.AppCompatActivity;

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

public class AdminAgregarParadaActivity extends AppCompatActivity {

    //LAYOUT
    EditText ubicacionAgregarParada;
    EditText mapaAgregarParada;
    Button btnAgregarParada;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;

    //LLAMAR CLASES
    Parada parada;
    SharePreference sHarePreference;
    ImageView imgBarra;
    ParadaInterface interfaces;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    Intents inte = new Intents(AdminAgregarParadaActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_agregar_parada);
        btnRutas = findViewById(R.id.btnRutas);
        btnParadas = findViewById(R.id.btnParadas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnUsuarios = findViewById(R.id.btnUser);
        btnCupos = findViewById(R.id.btnCupos);

        //FINDVIEWBYID
        ubicacionAgregarParada = findViewById(R.id.ubicacionAgregarParada);
        mapaAgregarParada = findViewById(R.id.mapaAgregarParada);
        //idRutaAgregarParada = findViewById(R.id.idRutaAgregarParada);
        btnAgregarParada = findViewById(R.id.btnAgregarParada);

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

        //AGREGAR LIBRO
        btnAgregarParada = findViewById(R.id.btnAgregarParada);
        parada = new Parada();

        btnAgregarParada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ubicacion = ubicacionAgregarParada.getText().toString().trim();
                String mapa = mapaAgregarParada.getText().toString().trim();

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(ubicacion) && !TextUtils.isEmpty(mapa)) {
                    //SE MODIFICA}
                    parada.setUbicacion(ubicacion);
                    parada.setMapa(mapa);
                    agregar(parada);
                    limpiar();
                }else {
                    Toast.makeText(AdminAgregarParadaActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void agregar(Parada u)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(ParadaInterface.class);
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

    private void limpiar(){
        ubicacionAgregarParada.setText("");
        mapaAgregarParada.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}