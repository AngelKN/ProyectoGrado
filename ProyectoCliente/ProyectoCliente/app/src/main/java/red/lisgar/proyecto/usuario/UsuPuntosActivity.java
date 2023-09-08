package red.lisgar.proyecto.usuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import red.lisgar.proyecto.admin.AdminParadasActivity;
import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.adaptadores.ListaPuntosAdapter;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.PuntoRecarga;
import red.lisgar.proyecto.entidades.Usuario;
import red.lisgar.proyecto.interfaces.PuntosInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuPuntosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recyclerView;
    ListaPuntosAdapter adapter;
    ArrayList<PuntoRecarga> listPrestados;
    //LAYOUT
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    ImageView imgBarra;
    SearchView buscadorUsuPrestados;
    PuntosInterface interfaces;
    SharePreference sHarePreference;
    PopupMenu popupMenu;
    Usuario usuario;
    ImageButton btnRutas;
    ImageButton btnPuntos;
    ImageButton btnCupos;
    Intents inte = new Intents(UsuPuntosActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usu_puntos);
        buscadorUsuPrestados = findViewById(R.id.buscadorUsuPrestados);
        btnRutas = findViewById(R.id.btnRutas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnCupos = findViewById(R.id.btnCupos);

        //RECYCLEVIEW
        recyclerView = findViewById(R.id.recyclerPrestados);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        listPrestados = new ArrayList<>();

        lista();

        //TOOLBAR
        sHarePreference = new SharePreference(this);
        btnMas = findViewById(R.id.btnMas);
        rolToolbar = findViewById(R.id.rolToolbar);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        imgBarra = findViewById(R.id.imgBarra);
        btnMas.setImageResource(R.drawable.ic_mas);
        imgBarra.setImageResource(R.drawable.admin);
        usuario = new Usuario();
        rolToolbar.setText(sHarePreference.getNombre());
        nombreToolbar.setText(sHarePreference.getCorreo());

        rolToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.actualizarUser();
            }
        });

        //MENU POPUP
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu = new PopupMenu(UsuPuntosActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_usuario, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.AgregarCupo:
                                inte.agregarCupo();
                                break;
                            case R.id.misPublicaciones:
                                inte.misPublicaciones();
                                break;
                            case R.id.recargarU:
                                inte.usuPuntos();
                                break;
                            case R.id.salir:
                                inte.alerta();
                                break;
                            default:
                                return UsuPuntosActivity.super.onOptionsItemSelected(menuItem);
                        }
                        return true;
                    }
                });
                popupMenu.show();
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

        buscadorUsuPrestados.setOnQueryTextListener(this);

    }

    private void lista()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaces = retrofit.create(PuntosInterface.class);
        Call<List<PuntoRecarga>> call = interfaces.findAll();
        call.enqueue(new Callback<List<PuntoRecarga>>() {
            @Override
            public void onResponse(Call<List<PuntoRecarga>> call, Response<List<PuntoRecarga>> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                List<PuntoRecarga> user = response.body();
                String actualizar = "VERPARADAS";
                String ventana = "HORIZONTAL";
                adapter = new ListaPuntosAdapter((ArrayList<PuntoRecarga>) user, UsuPuntosActivity.this, actualizar, ventana);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<PuntoRecarga>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filter(s);
        return false;
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}