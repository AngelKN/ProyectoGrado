package red.lisgar.proyecto.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
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

import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.adaptadores.ListaParadasAdapter;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.Parada;
import red.lisgar.proyecto.entidades.Usuario;
import red.lisgar.proyecto.interfaces.ParadaInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminParadasActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    //LAYOUT
    RecyclerView rvLista;
    ListaParadasAdapter adapter;
    ArrayList<Usuario> listItem;
    TextView rolToolbar;
    TextView nombreToolbar;
    SearchView buscadorAdminDispon;
    ImageView btnMas;
    ParadaInterface interfaces;
    //CLASES
    PopupMenu popupMenu;
    SharePreference sHarePreference;
    ImageView imgBarra;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    Intents inte = new Intents(AdminParadasActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_paradas);
        btnRutas = findViewById(R.id.btnRutas);
        btnParadas = findViewById(R.id.btnParadas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnUsuarios = findViewById(R.id.btnUser);
        btnCupos = findViewById(R.id.btnCupos);

        buscadorAdminDispon = findViewById(R.id.buscadorAdmin);//TOOLBAR
        sHarePreference = new SharePreference(this);
        btnMas = findViewById(R.id.btnMas);
        rolToolbar = findViewById(R.id.rolToolbar);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        imgBarra = findViewById(R.id.imgBarra);
        imgBarra.setImageResource(R.drawable.admin);
        btnMas.setImageResource(R.drawable.ic_mas);
        rolToolbar.setText(sHarePreference.getNombre());
        rvLista = findViewById(R.id.recyclerId);
        nombreToolbar.setText(sHarePreference.getCorreo());
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvLista.setLayoutManager(manager);
        lista();

        buscadorAdminDispon.setOnQueryTextListener(this);

        //MENU POPUP
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu = new PopupMenu(AdminParadasActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_admin, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.recargar:
                                inte.adminParadas();
                                break;
                            case R.id.agregar:
                                inte.agregarParada();
                                break;
                            case R.id.salir:
                                AlertDialog.Builder alerta = new AlertDialog.Builder(AdminParadasActivity.this);
                                alerta.setMessage("¿Desea cerrar sesion?:")
                                        .setCancelable(false)
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                inte.Main();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                AlertDialog titulo = alerta.create();
                                titulo.setTitle("Cerrar Sesion");
                                titulo.show();
                                break;
                            default:
                                return AdminParadasActivity.super.onOptionsItemSelected(menuItem);
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

        buscadorAdminDispon.setOnQueryTextListener(this);
    }

    private void lista()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaces = retrofit.create(ParadaInterface.class);
        Call<List<Parada>> call = interfaces.findAll();
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
                String actualizar = "ACTUALIZAR";
                String ventana = "VERTICAL";
                adapter = new ListaParadasAdapter((ArrayList<Parada>) user, AdminParadasActivity.this, actualizar, ventana);
                rvLista.setAdapter(adapter);
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

    private void verLibrosDisponibles() {
        Intent intent3 = new Intent(this, AdminParadasActivity.class);
        startActivity(intent3);
    }
}