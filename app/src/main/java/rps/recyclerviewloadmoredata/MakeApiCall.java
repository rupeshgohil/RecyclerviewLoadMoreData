package rps.recyclerviewloadmoredata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MakeApiCall {

    @GET("movies.php")
    Call<List<MovieModal>> getData(@Query("index") int i);
}
