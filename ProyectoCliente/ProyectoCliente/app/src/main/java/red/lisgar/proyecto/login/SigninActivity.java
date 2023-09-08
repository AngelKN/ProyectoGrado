package red.lisgar.proyecto.login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.constants.JavaMailAPI;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.Usuario;
import red.lisgar.proyecto.interfaces.UsuarioInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SigninActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtCorreosignin;
    EditText txtTelefono;
    EditText txtDireccion;
    EditText txtContrasena;
    EditText txtContrasena2;
    Button btnRegistrarse;
    Button btnIniciarSesion;
    Usuario usuario;
    SharePreference sHarePreference;
    UsuarioInterface interfaces;
    Intents inte = new Intents(SigninActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        txtNombre = findViewById(R.id.txtNombre);
        txtCorreosignin = findViewById(R.id.txtCorreosignin);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtContrasena = findViewById(R.id.txtContrasena);
        txtContrasena2 = findViewById(R.id.txtContrasena2);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        sHarePreference = new SharePreference(this);
        usuario = new Usuario();

        inte.listaCupos();
        inte.listaParadas();
        inte.listaPuntos();
        inte.listaRutas();
        inte.listaUser();

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nombre = txtNombre.getText().toString().trim();
                String Correo = txtCorreosignin.getText().toString().trim().toLowerCase();
                String Telefono = txtTelefono.getText().toString().trim().toLowerCase();
                String Direccion = txtDireccion.getText().toString().trim().toLowerCase();
                String Contrasena = txtContrasena.getText().toString().trim();
                String Contrasena2 = txtContrasena2.getText().toString().trim();

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(Correo) && !TextUtils.isEmpty(Telefono) && !TextUtils.isEmpty(Direccion) && !TextUtils.isEmpty(Contrasena)) {
                    if(validarCorreo(Correo)){
                        //EL TELEFONO DEBE TENER 10 CARACTERES
                        if (Telefono.length()==10) {
                            if(Contrasena.equals(Contrasena2)){
                                //SE REGISTRA
                                usuario.setNombre(Nombre);
                                usuario.setCorreo(Correo);
                                usuario.setTelefono(Telefono);
                                usuario.setDireccion(Direccion);
                                usuario.setContraseña(Contrasena);
                                usuario.setRol("USER");
                                correo(Correo, usuario);
                            }else{
                                txtContrasena2.setText("");
                                Toast.makeText(SigninActivity.this, "Las contraseñas no concuerdan", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            txtTelefono.setText("");
                            Toast.makeText(SigninActivity.this, "El telefono debe tener 10 caracteres", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        txtCorreosignin.setText("");
                        Toast.makeText(SigninActivity.this, "Debe contener un correo valido", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SigninActivity.this, "Rellene todos lo campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.Main();
                limpiar();
            }
        });
    }

    private boolean validarCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correo);
        return mather.find();
    }

    private void registrar(Usuario u)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(UsuarioInterface.class);
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
                    inte.Main();
                    limpiar();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "EL CORREO SE ENCUENTRA EN USO", Toast.LENGTH_LONG);
                    toast.show();
                    txtCorreosignin.setText("");
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

    private void correo(String u, Usuario user)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaces = retrofit.create(UsuarioInterface.class);
        Call<Usuario> call = interfaces.getOneCorreo(u);
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
                Usuario save = response.body();

                if(save == null){
                    sendMail(user);
                }else{
                    Toast toast = Toast.makeText(getApplication(), "El correo ya se encuentra en uso", Toast.LENGTH_LONG);
                    toast.show();
                    txtCorreosignin.setText("");
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

    private void sendMail(Usuario user) {

        String mail = txtCorreosignin.getText().toString();
        String message = UUID.randomUUID().toString().toUpperCase().substring(0, 6);
        String subject = "CODIGO DE VERIFICACION";

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);

        javaMailAPI.execute();

        AlertDialog.Builder alerta = new AlertDialog.Builder(SigninActivity.this);
        alerta.setTitle("INGRESE EL CODIGO");

        final EditText ver = new EditText(SigninActivity.this);
        ver.setInputType(InputType.TYPE_CLASS_TEXT);

        alerta.setView(ver);
        alerta.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(ver.getText().toString().equals(message)){
                    registrar(user);
                }else{
                    Toast toast = Toast.makeText(getApplication(), "EL CODIGO NO ES CORRECTO", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        alerta.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alerta.show();
    }

    private void limpiar(){
        txtNombre.setText("");
        txtCorreosignin.setText("");
        txtDireccion.setText("");
        txtContrasena.setText("");
        txtContrasena2.setText("");
        txtTelefono.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}