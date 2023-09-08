package red.lisgar.proyecto.usuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import red.lisgar.proyecto.admin.AdminParadasActivity;
import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.adaptadores.ListaCuposAdapter;
import red.lisgar.proyecto.admin.AdminCuposActivity;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.Cupo;
import red.lisgar.proyecto.interfaces.CupoInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuPortalActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

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
    ImageButton btnPuntos;
    ImageButton btnCupos;
    Intents inte = new Intents(UsuPortalActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usu_portal);

        btnRutas = findViewById(R.id.btnRutas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnCupos = findViewById(R.id.btnCupos);

        buscadorAdminDispon = findViewById(R.id.buscadorUsuPrestados);//TOOLBAR
        sHarePreference = new SharePreference(this);
        btnMas = findViewById(R.id.btnMas);
        rolToolbar = findViewById(R.id.rolToolbar);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        imgBarra = findViewById(R.id.imgBarra);
        imgBarra.setImageResource(R.drawable.admin);
        btnMas.setImageResource(R.drawable.ic_mas);
        rolToolbar.setText(sHarePreference.getNombre());
        rvLista = findViewById(R.id.recyclerCupos);
        nombreToolbar.setText(sHarePreference.getCorreo());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);
        lista();

        buscadorAdminDispon.setOnQueryTextListener(this);

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
                popupMenu = new PopupMenu(UsuPortalActivity.this, view);
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
                                inte.usuCupos();
                                break;
                            case R.id.salir:
                                inte.alerta();
                                break;
                            default:
                                return UsuPortalActivity.super.onOptionsItemSelected(menuItem);
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
                String actualizar;
                if(sHarePreference.getCorreo().equalsIgnoreCase("1")){
                    actualizar = "ACTUALIZAR";
                }else{
                    actualizar = "NINGUNO";
                }
                String ventana = "HORIZONTAL";
                adapter = new ListaCuposAdapter((ArrayList<Cupo>) user, UsuPortalActivity.this, actualizar, ventana);
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