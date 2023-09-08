package red.lisgar.proyecto.interfaces;

import java.util.List;

import red.lisgar.proyecto.entidades.Parada;
import red.lisgar.proyecto.entidades.Ruta;
import red.lisgar.proyecto.entidades.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RutaInterface {

    @GET("")
    Call<String> saludar();

    @GET("rutas")
    Call<List<Ruta>> findAll();

    @GET("ruta/{id}")
    Call<Ruta> getOne(@Path("id") String id);

    @POST("saveruta")
    Call<String> save(@Body Ruta u);

    @POST("updateruta")
    Call<String> update(@Body Ruta u);

    @DELETE("deleteruta/{id}")
    Call<String> delete(@Path("id") String id);

    @GET("paradasruta/{id}")
    Call<List<Parada>> findByParada(@Path("id") String id);
}
