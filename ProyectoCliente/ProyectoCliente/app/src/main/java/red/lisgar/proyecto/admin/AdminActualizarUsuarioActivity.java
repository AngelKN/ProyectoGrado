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
import red.lisgar.proyecto.entidades.Usuario;
import red.lisgar.proyecto.interfaces.UsuarioInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActualizarUsuarioActivity extends AppCompatActivity {


    EditText nombreActualizarUsuario;
    EditText correoActualizarUsuario;
    EditText telefonoActualizarUsuario;
    EditText direccionActualizarUsuario;
    EditText contrasenaActualizarUsuario;
    Button btnActualizarUsuario;
    Button btnEliminarUsuario;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    SharePreference sHarePreference;
    ImageView imgBarra;
    Usuario user;
    UsuarioInterface interfaces;
    String id;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    Intents inte = new Intents(AdminActualizarUsuarioActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_actualizar_usuario);
        btnRutas = findViewById(R.id.btnRutas);
        btnParadas = findViewById(R.id.btnParadas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnUsuarios = findViewById(R.id.btnUser);
        btnCupos = findViewById(R.id.btnCupos);

        nombreActualizarUsuario = findViewById(R.id.nombreActualizarUsuario);
        correoActualizarUsuario = findViewById(R.id.correoActualizarUsuario);
        telefonoActualizarUsuario = findViewById(R.id.telefonoActualizarUsuario);
        direccionActualizarUsuario = findViewById(R.id.direccionActualizarUsuario);
        contrasenaActualizarUsuario = findViewById(R.id.contrasenaActualizarUsuario);
        btnActualizarUsuario = findViewById(R.id.btnActualizarUsuario);
        btnEliminarUsuario = findViewById(R.id.btnEliminarUsuario);

        user = new Usuario();
        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        imgBarra = findViewById(R.id.imgBarra);
        imgBarra.setImageResource(R.drawable.admin);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        nombreToolbar.setText(sHarePreference.getCorreo());
        rolToolbar.setText(sHarePreference.getNombre());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                id = extras.getString("idi");
            }
        } else {
            id = (String) savedInstanceState.getString("idi");
        }

        if(!sHarePreference.getRol().equals("ADMIN")){
            ocultar();
            ver(sHarePreference.getId());
            user.setId(sHarePreference.getId());
            user.setRol("USER");
        }else{
            user.setId(id);
            user.setRol("ADMIN");
            ver(id);
        }

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usu();
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

        btnActualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreActualizarUsuario.getText().toString().trim();
                String correo = correoActualizarUsuario.getText().toString().trim();
                String telefono = telefonoActualizarUsuario.getText().toString().trim();
                String direccion = direccionActualizarUsuario.getText().toString().trim();
                String contresena = contrasenaActualizarUsuario.getText().toString().trim();
                boolean correcto = false;

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(telefono) && !TextUtils.isEmpty(direccion) && !TextUtils.isEmpty(contresena)) {
                    //SE MODIFICA
                    user.setNombre(nombre);
                    user.setCorreo(correo);
                    user.setTelefono(telefono);
                    user.setDireccion(direccion);
                    user.setContraseña(contresena);
                    editar(user);
                    limpiar();
                }else {
                    Toast.makeText(AdminActualizarUsuarioActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnEliminarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(AdminActualizarUsuarioActivity.this);
                alerta.setMessage("¿Desea eliminar Usuario?:")
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
        interfaces = retrofit.create(UsuarioInterface.class);
        Call<Usuario> call = interfaces.getOne(id);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                Usuario ver = response.body();

                if(ver != null){
                    nombreActualizarUsuario.setText(ver.getNombre());
                    correoActualizarUsuario.setText(ver.getCorreo());
                    telefonoActualizarUsuario.setText(ver.getTelefono());
                    direccionActualizarUsuario.setText(ver.getDireccion());
                    contrasenaActualizarUsuario.setText(ver.getContraseña());
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ENCONTRAR LA RUTA", Toast.LENGTH_LONG);
                    toast.show();
                    usu();
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    private void editar(Usuario u)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(UsuarioInterface.class);
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
                    usu();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "EL USUARIO SE ENCUENTRA EN USO", Toast.LENGTH_LONG);
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
        interfaces = retrofit.create(UsuarioInterface.class);
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
                    inte.adminUsers();
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
        nombreActualizarUsuario.setText("");
        correoActualizarUsuario.setText("");
        telefonoActualizarUsuario.setText("");
        direccionActualizarUsuario.setText("");
        contrasenaActualizarUsuario.setText("");
    }

    private void ocultar(){
        btnCupos.setVisibility(View.INVISIBLE);
        btnPuntos.setVisibility(View.INVISIBLE);
        btnRutas.setVisibility(View.INVISIBLE);
        btnParadas.setVisibility(View.INVISIBLE);
        btnUsuarios.setVisibility(View.INVISIBLE);
        btnEliminarUsuario.setVisibility(View.INVISIBLE);
    }

    private void usu(){
        if(!sHarePreference.getRol().equals("ADMIN")){
            inte.usuRutas();
        }else{
            inte.adminUsers();
        }
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}