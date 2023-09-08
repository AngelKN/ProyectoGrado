package red.lisgar.proyecto.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import red.lisgar.proyecto.constants.JavaMailAPI;
import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.PuntoRecarga;
import red.lisgar.proyecto.entidades.Usuario;
import red.lisgar.proyecto.interfaces.UsuarioInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText txtCorreo;
    EditText txtpass;
    Button btnEntrar;
    Button btnSignin;
    SharePreference sHarePreference;
    PuntoRecarga puntoRecarga = new PuntoRecarga();
    UsuarioInterface interfaces;
    Usuario user;
    Usuario userval;
    Intents inte = new Intents(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCorreo = findViewById(R.id.txtCorreo);
        txtpass = findViewById(R.id.txtpass);
        btnEntrar = findViewById(R.id.btnAgregarRuta);
        sHarePreference = new SharePreference(this);
        btnSignin = findViewById(R.id.btnSignin);
        textView = findViewById(R.id.textView2);

        user = new Usuario();
        userval = new Usuario();

        inte.listaCupos();
        inte.listaParadas();
        inte.listaPuntos();
        inte.listaRutas();
        inte.listaUser();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setCorreo(txtCorreo.getText().toString());
                user.setContraseña(txtpass.getText().toString());

                login(user);
                limpiar();

            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.Singin();
                limpiar();
            }
        });
    }

    private void login(Usuario u)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaces = retrofit.create(UsuarioInterface.class);
        Call<Usuario> call = interfaces.login(u);
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
                userval = response.body();

                if(userval != null){
                    if(userval.getRol().equals("ADMIN")) {
                        Toast toast = Toast.makeText(getApplication(), "BIENVENIDO ADMIN", Toast.LENGTH_LONG);
                        toast.show();
                        inte.Admin();
                    }else{
                        Toast toast = Toast.makeText(getApplication(), "BIENVENIDO "+userval.getNombre(), Toast.LENGTH_LONG);
                        toast.show();
                        inte.User();
                    }
                    sHarePreference.setSharedPreferences(userval);
                }else{
                    Toast toast = Toast.makeText(getApplication(), "USUARIO O CONTRASEÑA INCORRECTO", Toast.LENGTH_LONG);
                    toast.show();
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

    private void limpiar(){
        txtCorreo.setText("");
        txtpass.setText("");
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}