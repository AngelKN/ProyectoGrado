package red.lisgar.proyecto.constants;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import red.lisgar.proyecto.adaptadores.ListaCuposAdapter;
import red.lisgar.proyecto.adaptadores.ListaParadasAdapter;
import red.lisgar.proyecto.adaptadores.ListaPuntosAdapter;
import red.lisgar.proyecto.adaptadores.ListaRutasAdapter;
import red.lisgar.proyecto.adaptadores.ListaUsuariosAdapter;
import red.lisgar.proyecto.admin.AdminActualizarUsuarioActivity;
import red.lisgar.proyecto.admin.AdminAgregarCupoActivity;
import red.lisgar.proyecto.admin.AdminAgregarParadaActivity;
import red.lisgar.proyecto.admin.AdminAgregarPuntosActivity;
import red.lisgar.proyecto.admin.AdminAgregarRutaActivity;
import red.lisgar.proyecto.admin.AdminCuposActivity;
import red.lisgar.proyecto.admin.AdminParadasActivity;
import red.lisgar.proyecto.admin.AdminPuntosActivity;
import red.lisgar.proyecto.admin.AdminRutasActivity;
import red.lisgar.proyecto.admin.AdminUsuariosActivity;
import red.lisgar.proyecto.entidades.Cupo;
import red.lisgar.proyecto.entidades.Parada;
import red.lisgar.proyecto.entidades.PuntoRecarga;
import red.lisgar.proyecto.entidades.Ruta;
import red.lisgar.proyecto.entidades.Usuario;
import red.lisgar.proyecto.interfaces.CupoInterface;
import red.lisgar.proyecto.interfaces.ParadaInterface;
import red.lisgar.proyecto.interfaces.PuntosInterface;
import red.lisgar.proyecto.interfaces.RutaInterface;
import red.lisgar.proyecto.interfaces.UsuarioInterface;
import red.lisgar.proyecto.login.MainActivity;
import red.lisgar.proyecto.login.SigninActivity;
import red.lisgar.proyecto.usuario.MisPublicacionesActivity;
import red.lisgar.proyecto.usuario.UsuPortalActivity;
import red.lisgar.proyecto.usuario.UsuPuntosActivity;
import red.lisgar.proyecto.usuario.UsuRutasActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Intents {

    Context context;
    CupoInterface interfaces;
    ParadaInterface interfacesP;
    PuntosInterface interfacesPR;
    RutaInterface interfacesR;
    UsuarioInterface interfacesU;

    boolean correcto;

    public Intents(Context context) {
        this.context = context;
    }

    public void Admin(){
        Intent intent = new Intent(context, AdminRutasActivity.class);
        context.startActivity(intent);
    }

    public void User(){
        Intent intent = new Intent(context, UsuRutasActivity.class);
        context.startActivity(intent);
    }

    public void Singin(){
        Intent intent = new Intent(context, SigninActivity.class);
        context.startActivity(intent);
    }

    public void Main(){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void usuPuntos(){
        Intent intent = new Intent(context, UsuPuntosActivity.class);
        context.startActivity(intent);
    }

    public void usuRutas(){
        Intent intent = new Intent(context, UsuRutasActivity.class);
        context.startActivity(intent);
    }

    public void usuCupos() {
        Intent intent = new Intent(context, UsuPortalActivity.class);
        context.startActivity(intent);
    }

    public void adminCupos(){
        Intent intent = new Intent(context, AdminCuposActivity.class);
        context.startActivity(intent);
    }

    public void adminParadas(){
        Intent intent = new Intent(context, AdminParadasActivity.class);
        context.startActivity(intent);
    }

    public void adminPuntos(){
        Intent intent = new Intent(context, AdminPuntosActivity.class);
        context.startActivity(intent);
    }

    public void adminRutas(){
        Intent intent = new Intent(context, AdminRutasActivity.class);
        context.startActivity(intent);
    }

    public void adminUsers(){
        Intent intent = new Intent(context, AdminUsuariosActivity.class);
        context.startActivity(intent);
    }

    public void agregarCupo(){
        Intent intent = new Intent(context, AdminAgregarCupoActivity.class);
        context.startActivity(intent);
    }

    public void agregarParada(){
        Intent intent = new Intent(context, AdminAgregarParadaActivity.class);
        context.startActivity(intent);
    }

    public void agregarRuta(){
        Intent intent = new Intent(context, AdminAgregarRutaActivity.class);
        context.startActivity(intent);
    }

    public void agregarPunto(){
        Intent intent = new Intent(context, AdminAgregarPuntosActivity.class);
        context.startActivity(intent);
    }

    public void misPublicaciones() {
        Intent intent = new Intent(context, MisPublicacionesActivity.class);
        context.startActivity(intent);
    }

    public void actualizarUser() {
        Intent intent = new Intent(context, AdminActualizarUsuarioActivity.class);
        context.startActivity(intent);
    }

    public void alerta(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setMessage("¿Desea cerrar sesion?:")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Main();
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
    }

    public void listaCupos()
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
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<Cupo>> call, Throwable t) {

            }
        });
    }

    public void listaParadas()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfacesP = retrofit.create(ParadaInterface.class);
        Call<List<Parada>> call = interfacesP.findAll();
        call.enqueue(new Callback<List<Parada>>() {
            @Override
            public void onResponse(Call<List<Parada>> call, Response<List<Parada>> response) {
                if(!response.isSuccessful())
                {
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<Parada>> call, Throwable t) {
            }
        });
    }

    public void listaPuntos()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfacesPR = retrofit.create(PuntosInterface.class);
        Call<List<PuntoRecarga>> call = interfacesPR.findAll();
        call.enqueue(new Callback<List<PuntoRecarga>>() {
            @Override
            public void onResponse(Call<List<PuntoRecarga>> call, Response<List<PuntoRecarga>> response) {
                if(!response.isSuccessful())
                {
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<PuntoRecarga>> call, Throwable t) {
            }
        });
    }

    public void listaRutas()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfacesR = retrofit.create(RutaInterface.class);
        Call<List<Ruta>> call = interfacesR.findAll();
        call.enqueue(new Callback<List<Ruta>>() {
            @Override
            public void onResponse(Call<List<Ruta>> call, Response<List<Ruta>> response) {
                if(!response.isSuccessful())
                {
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<Ruta>> call, Throwable t) {
            }
        });
    }

    public void listaUser()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfacesU = retrofit.create(UsuarioInterface.class);
        Call<List<Usuario>> call = interfacesU.findAll();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if(!response.isSuccessful())
                {
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
            }
        });
    }

}
