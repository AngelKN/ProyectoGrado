package red.lisgar.proyecto.interfaces;

import java.util.List;

import red.lisgar.proyecto.entidades.Parada;
import red.lisgar.proyecto.entidades.PuntoRecarga;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PuntosInterface {

    @GET("")
    Call<String> saludar();

    @GET("puntos")
    Call<List<PuntoRecarga>> findAll();

    @GET("punto/{id}")
    Call<PuntoRecarga> getOne(@Path("id") String id);

    @POST("savepunto")
    Call<String> save(@Body PuntoRecarga u);

    @POST("updatepunto")
    Call<String> update(@Body PuntoRecarga u);

    @DELETE("deletepunto/{id}")
    Call<String> delete(@Path("id") String id);
}
