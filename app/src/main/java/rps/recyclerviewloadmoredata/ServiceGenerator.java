package rps.recyclerviewloadmoredata;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class ServiceGenerator {
      public static MakeApiCall CreateBaseService(Class<MakeApiCall> makeApiCallClass) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build();
          Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl("http://www.sab99r.com/demos/api/")
                  .addConverterFactory(GsonConverterFactory.create())
                  .client(httpClient).build();
        return retrofit.create(makeApiCallClass);
    }
}
