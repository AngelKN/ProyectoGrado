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
import red.lisgar.proyecto.adaptadores.ListaCuposAdapter;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.Cupo;
import red.lisgar.proyecto.interfaces.CupoInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminCuposActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    //LAYOUT
    RecyclerView rvLista;
    ListaCuposAdapter adapter;
    TextView rolToolbar;
    TextView nombreToolbar;
    SearchView buscadorAdminDispon;
    ImageView btnMas;
    CupoInterface interfaces;
    //CLASES
    PopupMenu popupMenu;
    SharePreference sHarePreference;
    ImageView imgBarra;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    String menu;
    Intents inte = new Intents(AdminCuposActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cupos);
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
                popupMenu = new PopupMenu(AdminCuposActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_admin, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.recargar:
                                inte.adminCupos();
                                break;
                            case R.id.agregar:
                                inte.agregarCupo();
                                break;
                            case R.id.salir:

                                AlertDialog.Builder alerta = new AlertDialog.Builder(AdminCuposActivity.this);
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
                                return AdminCuposActivity.super.onOptionsItemSelected(menuItem);
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
        interfaces = retrofit.create(CupoInterface.class);
        Call<List<Cupo>> call = interfaces.findAll();
        call.enqueue(new Callback<List<Cupo>>() {
            @Override
            public void onResponse(Call<List<Cupo>> call, Response<List<Cupo>> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                List<Cupo> user = response.body();
                String actualizar = "ACTUALIZAR";
                String ventana = "VERTICAL";
                adapter = new ListaCuposAdapter((ArrayList<Cupo>) user, AdminCuposActivity.this, actualizar, ventana);
                rvLista.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Cupo>> call, Throwable t) {
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

    private void verCupos() {
        Intent intent3 = new Intent(this, AdminCuposActivity.class);
        startActivity(intent3);
    }
}