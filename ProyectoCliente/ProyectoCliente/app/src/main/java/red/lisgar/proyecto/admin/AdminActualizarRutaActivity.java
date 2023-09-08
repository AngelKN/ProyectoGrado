package red.lisgar.proyecto.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import red.lisgar.proyecto.constants.Intents;
import red.lisgar.proyecto.R;
import red.lisgar.proyecto.constants.urlDeLaApi;
import red.lisgar.proyecto.entidades.Parada;
import red.lisgar.proyecto.entidades.Ruta;
import red.lisgar.proyecto.interfaces.ParadaInterface;
import red.lisgar.proyecto.interfaces.RutaInterface;
import red.lisgar.proyecto.login.SharePreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActualizarRutaActivity extends AppCompatActivity {

    EditText nombreActualizarRuta;
    EditText descripcionActualizarRuta;
    EditText precioActualizarRuta;
    EditText fotoActualizarRuta;
    EditText tipoActualizarRuta;
    Button btnActualizarRuta;
    Button btnEliminarRuta;
    ImageButton btnAgregar1;
    ImageButton btnAgregar2;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    SharePreference sHarePreference;
    ImageView imgBarra;
    Ruta ruta;
    RutaInterface interfaces;
    ParadaInterface interfacesP;
    String id;
    Spinner spinner1;
    Spinner spinner2;
    ImageButton btnRutas;
    ImageButton btnParadas;
    ImageButton btnPuntos;
    ImageButton btnUsuarios;
    ImageButton btnCupos;
    Intents inte = new Intents(AdminActualizarRutaActivity.this);
    List<String> listId = new ArrayList<String>();
    List<Parada> listParada1 = new ArrayList<Parada>();
    List<Parada> listParada2 = new ArrayList<Parada>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_actualizar_ruta);
        btnRutas = findViewById(R.id.btnRutas);
        btnParadas = findViewById(R.id.btnParadas);
        btnPuntos = findViewById(R.id.btnPuntos);
        btnUsuarios = findViewById(R.id.btnUser);
        btnCupos = findViewById(R.id.btnCupos);

        nombreActualizarRuta = findViewById(R.id.nombreActualizarRuta);
        descripcionActualizarRuta = findViewById(R.id.descripcionActualizarRuta);
        precioActualizarRuta = findViewById(R.id.precioActualizarRuta);
        fotoActualizarRuta = findViewById(R.id.fotoActualizarRuta);
        tipoActualizarRuta = findViewById(R.id.tipoActualizarRuta);
        btnActualizarRuta = findViewById(R.id.btnActualizarRuta);
        btnEliminarRuta = findViewById(R.id.btnEliminarRuta);
        spinner1 = findViewById(R.id.spinner1);
        btnAgregar1 = findViewById(R.id.agregar1);
        btnAgregar2 = findViewById(R.id.agregar2);
        spinner2 = findViewById(R.id.spinner2);

        ruta = new Ruta();
        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        imgBarra = findViewById(R.id.imgBarra);
        imgBarra.setImageResource(R.drawable.admin);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        rolToolbar.setText(sHarePreference.getNombre());
        nombreToolbar.setText(sHarePreference.getCorreo());

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inte.adminRutas();
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

        paradas();
        paradasruta(id);
        ver(id);

        btnAgregar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Parada parada = (Parada) spinner2.getSelectedItem();
                String id1 = parada.getId();
                boolean veri = true;

                for(String i : listId) {
                    if (i.equalsIgnoreCase(id1)) {
                        veri = false;
                        break;
                    }
                }

                if(veri){
                    listParada1.add(parada);
                    listId.add(id1);
                    Toast toast = Toast.makeText(getApplication(), "LA PARADA REGISTRADA CORRECTAMENTE", Toast.LENGTH_LONG);
                    toast.show();
                    for(Parada i : listParada2){
                        if(i.getId().equalsIgnoreCase(id1)){
                            listParada2.remove(i);
                            break;
                        }
                    }
                }else{
                    Toast toast = Toast.makeText(getApplication(), "LA PARADA YA ESTA REGISTRADA", Toast.LENGTH_LONG);
                    toast.show();
                }
                ArrayAdapter<Parada> adapter1 = new ArrayAdapter<Parada>(AdminActualizarRutaActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, (ArrayList) listParada1);
                spinner1.setAdapter(adapter1);

                ArrayAdapter<Parada> adapter2 = new ArrayAdapter<Parada>(AdminActualizarRutaActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, (ArrayList) listParada2);
                spinner2.setAdapter(adapter2);
            }
        });

        btnAgregar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Parada parada = (Parada) spinner1.getSelectedItem();
                //String id = parada.getId();
                boolean veri = true;

                for(Parada i : listParada2) {
                    if (i.getId().equalsIgnoreCase(parada.getId())) {
                        veri = false;
                        break;
                    }
                }

                if(veri){
                    listParada2.add(parada);
                    //listId.add(id);
                    Toast toast = Toast.makeText(getApplication(), "LA PARADA SE ELIMINO CORRECTAMENTE", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "LA PARADA SE ELIMINO CORRECTAMENTE", Toast.LENGTH_LONG);
                    toast.show();
                }
                listParada1.remove(parada);
                listId.remove(parada.getId());


                ArrayAdapter<Parada> adapter1 = new ArrayAdapter<Parada>(AdminActualizarRutaActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, (ArrayList) listParada1);
                spinner1.setAdapter(adapter1);

                ArrayAdapter<Parada> adapter2 = new ArrayAdapter<Parada>(AdminActualizarRutaActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, (ArrayList) listParada2);
                spinner2.setAdapter(adapter2);
            }
        });

        btnActualizarRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nombre = nombreActualizarRuta.getText().toString().trim();
                String descripcion = descripcionActualizarRuta.getText().toString().trim();
                double precio = Double.parseDouble(precioActualizarRuta.getText().toString().trim());
                String foto = fotoActualizarRuta.getText().toString().trim();
                String tipo = tipoActualizarRuta.getText().toString().trim();
                boolean correcto = false;

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(Nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(foto) && !TextUtils.isEmpty(tipo)) {
                    //SE MODIFICA
                    ruta.setId(id);
                    ruta.setNombre(Nombre);
                    ruta.setDescripcion(descripcion);
                    ruta.setPrecio(precio);
                    ruta.setFoto(foto);
                    ruta.setTipo(tipo);
                    ruta.setParadas(listId);
                    editar(ruta);
                    limpiar();
                }else {
                    Toast.makeText(AdminActualizarRutaActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnEliminarRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(AdminActualizarRutaActivity.this);
                alerta.setMessage("¿Desea eliminar Ruta?:")
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

    private void paradas(){
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
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                List<Parada> user = response.body();
                listParada2 = user;

                ArrayAdapter<Parada> adapter = new ArrayAdapter<Parada>(AdminActualizarRutaActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, (ArrayList) user);
                spinner2.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Parada>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    private void paradasruta(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaces = retrofit.create(RutaInterface.class);
        Call<List<Parada>> call = interfaces.findByParada(id);
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
                listParada1 = user;
                for(Parada i : user) {
                    listId.add(i.getId());
                }

                ArrayAdapter<Parada> adapter = new ArrayAdapter<Parada>(AdminActualizarRutaActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, (ArrayList) user);
                spinner1.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Parada>> call, Throwable t) {
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
        interfaces = retrofit.create(RutaInterface.class);
        Call<Ruta> call = interfaces.getOne(id);
        call.enqueue(new Callback<Ruta>() {
            @Override
            public void onResponse(Call<Ruta> call, Response<Ruta> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("err: ",response.message());
                    return;
                }
                Ruta ver = response.body();

                if(ver != null){
                    nombreActualizarRuta.setText(ver.getNombre());
                    descripcionActualizarRuta.setText(ver.getDescripcion());
                    precioActualizarRuta.setText(ver.getPrecio()+"");
                    fotoActualizarRuta.setText(ver.getFoto());
                    tipoActualizarRuta.setText(ver.getTipo());
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ENCONTRAR LA RUTA", Toast.LENGTH_LONG);
                    toast.show();
                    inte.adminRutas();
                }

            }

            @Override
            public void onFailure(Call<Ruta> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("err: ", t.getMessage());
            }
        });
    }

    private void editar(Ruta u)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlDeLaApi.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        interfaces = retrofit.create(RutaInterface.class);
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
                    inte.adminRutas();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "EL CORREO SE ENCUENTRA EN USO", Toast.LENGTH_LONG);
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
        interfaces = retrofit.create(RutaInterface.class);
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
                    inte.adminRutas();
                }else{
                    Toast toast = Toast.makeText(getApplication(), "ERROR AL ELIMINAR LA RUTA", Toast.LENGTH_LONG);
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
        nombreActualizarRuta.setText("");
        descripcionActualizarRuta.setText("");
        precioActualizarRuta.setText("");
        fotoActualizarRuta.setText("");
        tipoActualizarRuta.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}