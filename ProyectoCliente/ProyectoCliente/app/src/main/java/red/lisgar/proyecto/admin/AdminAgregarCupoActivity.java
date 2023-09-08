package red.lisgar.proyecto.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.Cupo;
import red.lisgar.proyecto.interfaces.CupoInterface;
import red.lisgar.proyecto.interfaces.RutaInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminAgregarCupoActivity extends AppCompatActivity {

    //LAYOUT
    EditText precioAgregarCupo;
    Button horaIAgregarCupo;
    Button horaSAgregarCupo;
    EditText descripcionAgregarCupo;
    Button btnAgregarCupo;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;

    //LLAMAR CLASES
    Cupo cupo;
    SharePreference sHarePreference;
    ImageView imgBarra;
    CupoInterface interfaces;
    RutaInterface interfacesR;
    Spinner spinner;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    Intents inte = new Intents(AdminAgregarCupoActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_agregar_cupo);
        btnRutas = findViewById(R.id.btnRutas);
        btnParadas = findViewById(R.id.btnParadas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnUsuarios = findViewById(R.id.btnUser);
        btnCupos = findViewById(R.id.btnCupos);

        //FINDVIEWBYID
        precioAgregarCupo = findViewById(R.id.precioAgregarCupo);
        horaIAgregarCupo = findViewById(R.id.horaIAgregarCupo);
        horaSAgregarCupo = findViewById(R.id.horaSAgregarCupo);
        descripcionAgregarCupo = findViewById(R.id.descripcionAgregarCupo);
        //idRutaAgregarParada = findViewById(R.id.idRutaAgregarParada);
        btnAgregarCupo = findViewById(R.id.btnAgregarCupo);

        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        imgBarra = findViewById(R.id.imgBarra);
        imgBarra.setImageResource(R.drawable.admin);
        nombreToolbar = findViewById(R.id.nombreToolbar);
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //AGREGAR LIBRO
        cupo = new Cupo();

        horaIAgregarCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerI();
            }
        });

        horaSAgregarCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerS();
            }
        });

        btnAgregarCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double precio = Double.parseDouble(precioAgregarCupo.getText().toString().trim());
                String horaI = horaIAgregarCupo.getText().toString().trim();
                String horaS = horaSAgregarCupo.getText().toString().trim();
                String descripcion = descripcionAgregarCupo.getText().toString().trim();
                String id_user = sHarePreference.getId();

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(horaI) && !TextUtils.isEmpty(horaS) && !TextUtils.isEmpty(descripcion)) {
                    //SE MODIFICA}
                    cupo.setPrecio(precio);
                    cupo.setHora_llegada(horaI);
                    cupo.setHora_salida(horaS);
                    cupo.setDescripcion(descripcion);
                    cupo.setId_user(id_user);
                    agregar(cupo);
                    limpiar();
                }else {
                    Toast.makeText(AdminAgregarCupoActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void agregar(Cupo u)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(CupoInterface.class);
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

    private void limpiar(){
        precioAgregarCupo.setText("");
        horaIAgregarCupo.setText("");
        horaSAgregarCupo.setText("");
        descripcionAgregarCupo.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
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
                horaIAgregarCupo.setText(String.valueOf(hour)+ ":"+String.valueOf(minute));

            }
        }, 15, 30, false);

        timePickerDialog.show();
    }

    private void openTimePickerS(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                //Showing the picked value in the textView
                horaSAgregarCupo.setText(String.valueOf(hour)+ ":"+String.valueOf(minute));

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
}