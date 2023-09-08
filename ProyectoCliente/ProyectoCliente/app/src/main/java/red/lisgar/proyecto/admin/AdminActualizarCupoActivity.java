package red.lisgar.proyecto.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.Cupo;
import red.lisgar.proyecto.entidades.Usuario;
import red.lisgar.proyecto.interfaces.CupoInterface;
import red.lisgar.proyecto.interfaces.UsuarioInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActualizarCupoActivity extends AppCompatActivity {

    EditText precioActualizarCupo;
    Button horaIActualizarCupo;
    Button horaSActualizarCupo;
    EditText descripcionActualizarCupo;
    TextView usuarioCupo;
    Button btnActualizarCupo;
    Button btnEliminarCupo;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    SharePreference sHarePreference;
    ImageView imgBarra;
    Cupo cupo = new Cupo();;
    CupoInterface interfaces;
    UsuarioInterface interfacesR;
    String id;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    Intents inte = new Intents(AdminActualizarCupoActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_actualizar_cupo);
        btnRutas = findViewById(R.id.btnRutas);
        btnParadas = findViewById(R.id.btnParadas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnUsuarios = findViewById(R.id.btnUser);
        btnCupos = findViewById(R.id.btnCupos);

        precioActualizarCupo = findViewById(R.id.precioActualizarCupo);
        horaIActualizarCupo = findViewById(R.id.horaIActualizarCupo);
        horaSActualizarCupo = findViewById(R.id.horaSActualizarCupo);
        descripcionActualizarCupo = findViewById(R.id.descripcionActualizarCupo);
        usuarioCupo = findViewById(R.id.usuarioActualizarCupo);
        btnActualizarCupo = findViewById(R.id.btnActualizarCupo);
        btnEliminarCupo = findViewById(R.id.btnEliminarCupo);

        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        imgBarra = findViewById(R.id.imgBarra);
        imgBarra.setImageResource(R.drawable.admin);
        rolToolbar.setText(sHarePreference.getNombre());
        nombreToolbar.setText(sHarePreference.getCorreo());

        if(!sHarePreference.getRol().equals("ADMIN")){
            ocultar();
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

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                id = extras.getString("idi");
            }
        } else {
            id = (String) savedInstanceState.getString("idi");
        }

        ver(id);

        horaIActualizarCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerI();
            }
        });

        horaSActualizarCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerS();
            }
        });

        btnActualizarCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double precio = Double.parseDouble(precioActualizarCupo.getText().toString().trim());
                String horaI = horaIActualizarCupo.getText().toString().trim();
                String horaS = horaSActualizarCupo.getText().toString().trim();
                String descripcion = descripcionActualizarCupo.getText().toString().trim();
                boolean correcto = false;

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(horaI) && !TextUtils.isEmpty(horaS)) {
                    //SE MODIFICA
                    cupo.setId(id);
                    cupo.setPrecio(precio);
                    cupo.setHora_llegada(horaI);
                    cupo.setHora_salida(horaS);
                    cupo.setDescripcion(descripcion);
                    editar(cupo);
                    limpiar();
                }else {
                    Toast.makeText(AdminActualizarCupoActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnEliminarCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(AdminActualizarCupoActivity.this);
                alerta.setMessage("¿Desea eliminar Publicación?:")
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

    private void verUser(String id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfacesR = retrofit.create(UsuarioInterface.class);
        Call<Usuario> call = interfacesR.getOne(id);
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
                    usuarioCupo.setText(ver.getNombre());
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

    private void ver(String id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaces = retrofit.create(CupoInterface.class);
        Call<Cupo> call = interfaces.getOne(id);
        call.enqueue(new Callback<Cupo>() {
            @Override
            public void onResponse(Call<Cupo> call, Response<Cupo> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                Cupo ver = response.body();

                if(ver != null){
                    precioActualizarCupo.setText(ver.getPrecio()+"");
                    horaIActualizarCupo.setText(ver.getHora_llegada());
                    horaSActualizarCupo.setText(ver.getHora_salida());
                    descripcionActualizarCupo.setText(ver.getDescripcion());
                    cupo.setId_user(ver.getId_user());
                    verUser(ver.getId_user());
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ENCONTRAR LA RUTA", Toast.LENGTH_LONG);
                    toast.show();
                    usu();
                }

            }

            @Override
            public void onFailure(Call<Cupo> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    private void editar(Cupo u)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(CupoInterface.class);
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
                    Toast toast = Toast.makeText(getApplication(), "LA PUBLICACION SE ENCUENTRA EN USO", Toast.LENGTH_LONG);
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
        interfaces = retrofit.create(CupoInterface.class);
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
                    Toast toast = Toast.makeText(getApplication(), "ELIMINADA CORRECTAMENTE", Toast.LENGTH_LONG);
                    toast.show();
                    limpiar();
                    usu();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ELIMINAR LA PUBLICACION", Toast.LENGTH_LONG);
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
        precioActualizarCupo.setText("");
        horaIActualizarCupo.setText("");
        horaSActualizarCupo.setText("");
        descripcionActualizarCupo.setText("");
    }

    private void ocultar(){
        btnCupos.setVisibility(View.INVISIBLE);
        btnPuntos.setVisibility(View.INVISIBLE);
        btnRutas.setVisibility(View.INVISIBLE);
        btnParadas.setVisibility(View.INVISIBLE);
        btnUsuarios.setVisibility(View.INVISIBLE);
    }

    private void openTimePickerI(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                //Showing the picked value in the textView
                horaIActualizarCupo.setText(String.valueOf(hour)+ ":"+String.valueOf(minute));

            }
        }, 15, 30, false);

        timePickerDialog.show();
    }

    private void openTimePickerS(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                //Showing the picked value in the textView
                horaSActualizarCupo.setText(String.valueOf(hour)+ ":"+String.valueOf(minute));

            }
        }, 15, 30, false);

        timePickerDialog.show();
    }

    private void usu(){
        if(!sHarePreference.getRol().equals("ADMIN")){
            inte.usuCupos();
        }else{
            inte.adminCupos();
        }
    }


    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}