package red.lisgar.proyecto.login;

import android.content.Context;
import android.content.SharedPreferences;

import red.lisgar.proyecto.entidades.Usuario;

public class SharePreference {
    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharePreference(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("base_sp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public  void setSharedPreferences(Usuario u){
        editor.putString("id", u.getId());
        editor.putString("correo", u.getCorreo());
        editor.putString("nombre", u.getNombre());
        editor.putString("contraseña", u.getContraseña());
        editor.putString("direccion", u.getDireccion());
        editor.putString("telefono", u.getTelefono());
        editor.putString("rol", u.getRol());
        editor.apply();
    }

    public String getId(){
        return sharedPreferences.getString("id", "No se encuentra registrado");
    }
    public String getCorreo(){
        return sharedPreferences.getString("correo", "No se encuentra registrado");
    }
    public String getNombre(){
        return sharedPreferences.getString("nombre", "No se encuentra registrado");
    }

    public String getRol() {
        return sharedPreferences.getString("rol", "No se encuentra registrado");
    }
}
