package red.lisgar.proyecto.interfaces;

import java.util.List;

import red.lisgar.proyecto.entidades.Cupo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CupoInterface {

    @GET("")
    Call<String> saludar();

    @GET("cupos")
    Call<List<Cupo>> findAll();

    @GET("cupo/{id}")
    Call<Cupo> getOne(@Path("id") String id);

    @POST("savecupo")
    Call<String> save(@Body Cupo u);

    @POST("updatecupo")
    Call<String> update(@Body Cupo u);

    @DELETE("deletecupo/{id}")
    Call<String> delete(@Path("id") String id);

    @GET("cupouser/{id}")
    Call<List<Cupo>> findByRuta(@Path("id") String id);
}
