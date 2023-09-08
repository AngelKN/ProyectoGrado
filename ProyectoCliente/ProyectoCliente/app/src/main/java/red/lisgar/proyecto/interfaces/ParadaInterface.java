package red.lisgar.proyecto.interfaces;

import java.util.List;

import red.lisgar.proyecto.entidades.Parada;
import red.lisgar.proyecto.entidades.Ruta;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ParadaInterface {

    @GET("")
    Call<String> saludar();

    @GET("paradas")
    Call<List<Parada>> findAll();

    @GET("parada/{id}")
    Call<Parada> getOne(@Path("id") String id);

    @POST("saveparada")
    Call<String> save(@Body Parada u);

    @POST("updateparada")
    Call<String> update(@Body Parada u);

    @DELETE("deleteparada/{id}")
    Call<String> delete(@Path("id") String id);

}
