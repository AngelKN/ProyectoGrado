package red.lisgar.proyecto.interfaces;

import java.util.List;
import java.util.Optional;

import red.lisgar.proyecto.entidades.Parada;
import red.lisgar.proyecto.entidades.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioInterface {

    @GET("")
    Call<String> saludar();

    @GET("all")
    Call<List<Usuario>> findAll();

    @POST("login")
    Call<Usuario> login(@Body Usuario u);

    @GET("find/{id}")
    Call<Usuario> getOne(@Path("id") String id);

    @GET("findcorreo/{correo}")
    Call<Usuario> getOneCorreo(@Path("correo") String correo);

    @POST("save")
    Call<String> save(@Body Usuario u);

    @POST("update")
    Call<String> update(@Body Usuario u);

    @DELETE("delete/{id}")
    Call<String> delete(@Path("id") String id);
}
