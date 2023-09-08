package red.lisgar.proyecto.usuario;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.adaptadores.ListaParadasAdapter;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.Parada;
import red.lisgar.proyecto.entidades.Ruta;
import red.lisgar.proyecto.entidades.Usuario;
import red.lisgar.proyecto.interfaces.ParadaInterface;
import red.lisgar.proyecto.interfaces.RutaInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuVisualizarRutaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListaParadasAdapter adapter;
    ArrayList<Parada> listParadas;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    SharePreference sHarePreference;
    ImageView imagen;
    TextView nombreRuta;
    TextView descripcionRuta;
    TextView precioRuta;
    TextView tipoRuta;
    ImageView imgBarra;
    RutaInterface interfaces;
    ParadaInterface interfacesP;
    String id;
    ImageButton btnRutas;
    ImageButton btnPuntos;
    ImageButton btnCupos;
    Intents inte = new Intents(UsuVisualizarRutaActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usu_visualizar_ruta);
        btnRutas = findViewById(R.id.btnRutas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnCupos = findViewById(R.id.btnCupos);

        imagen = findViewById(R.id.imagen);
        nombreRuta = findViewById(R.id.nombreRuta);
        descripcionRuta = findViewById(R.id.descripcionRuta);
        precioRuta = findViewById(R.id.precioRuta);
        tipoRuta = findViewById(R.id.tipoRuta);

        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        imgBarra = findViewById(R.id.imgBarra);
        nombreToolbar = findViewById(R.id.nombreToolbar);

        //TOOLBAR
        imgBarra.setImageResource(R.drawable.admin);
        Usuario usuario = new Usuario();
        rolToolbar.setText(sHarePreference.getNombre());
        nombreToolbar.setText(sHarePreference.getCorreo());

        rolToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.actualizarUser();
            }
        });

        if (usuario != null) {
            nombreToolbar.setText(usuario.getNombre());
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                id = extras.getString("ID");
            }
        } else {
            id = (String) savedInstanceState.getString("ID");
        }

        ver(id);
        paradas(id);


        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.usuRutas();
            }
        });
        btnRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.usuRutas();
            }
        });
        btnPuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.usuPuntos();
            }
        });
        btnCupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.usuCupos();
            }
        });
    }

    private void ver(String id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaces = retrofit.create(RutaInterface.class);
        Call<Ruta> call = interfaces.getOne(id);
        call.enqueue(new Callback<Ruta>() {
            @Override
            public void onResponse(Call<Ruta> call, Response<Ruta> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                Ruta ver = response.body();

                if(ver != null){
                    Glide.with(UsuVisualizarRutaActivity.this)
                            .load(ver.getFoto())
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imagen);
                    nombreRuta.setText(ver.getNombre());
                    descripcionRuta.setText(ver.getDescripcion());
                    precioRuta.setText("$"+ver.getPrecio());
                    tipoRuta.setText(ver.getTipo());
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ENCONTRAR LA RUTA", Toast.LENGTH_LONG);
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<Ruta> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    private void paradas(String id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaces = retrofit.create(RutaInterface.class);
        Call<List<Parada>> call = interfaces.findByParada(id);
        call.enqueue(new Callback<List<Parada>>() {
            @Override
            public void onResponse(Call<List<Parada>> call, Response<List<Parada>> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                List<Parada> user = response.body();

                //RECYCLEVIEW
                recyclerView = findViewById(R.id.recyclerHistorial);
                LinearLayoutManager manager = new LinearLayoutManager(UsuVisualizarRutaActivity.this);
                recyclerView.setLayoutManager(manager);
                listParadas = new ArrayList<>();
                String buscar = "BUSCAR";
                String ru = "RUTA";
                adapter = new ListaParadasAdapter((ArrayList<Parada>) user, UsuVisualizarRutaActivity.this, buscar, ru);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Parada>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}