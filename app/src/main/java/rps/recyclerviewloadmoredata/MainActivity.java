package rps.recyclerviewloadmoredata;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    public MyMovieAdapter myMovieAdapter;
    public ArrayList<MovieModal> movieModalArrayList = new ArrayList<>();
    public Context mContext;
    MakeApiCall api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new VerticalLineDecorated(2));
        myMovieAdapter  = new MyMovieAdapter(this,movieModalArrayList);
        mRecyclerView.setAdapter(myMovieAdapter);
        //Loadmore
        myMovieAdapter.setLoadMoreListener(new LoadMoreListener(){

            @Override
            public void onLoadMore() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = movieModalArrayList.size()-1;
                        LoadMoreData(index);
                    }
                });

            }
        });
        api = ServiceGenerator.CreateBaseService(MakeApiCall.class);
        ApiResponse();
    }
    public void ApiResponse(){
        Call<List<MovieModal>> responce = api.getData(0);
        responce.enqueue(new Callback<List<MovieModal>>() {
            @Override
            public void onResponse(Call<List<MovieModal>> call, Response<List<MovieModal>> response) {
                if(response.isSuccessful()){
                    Log.e("Responce","===>"+response.body().toString());
                    movieModalArrayList.addAll(response.body());
                    myMovieAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "Responcse Fails", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MovieModal>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Responcse Fails", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void LoadMoreData(int index){
        try{
            Log.e("LoadIndex",String.valueOf(index));
            movieModalArrayList.add(new MovieModal("load"));
            int pbb = movieModalArrayList.size()-1;
            myMovieAdapter.notifyItemInserted(pbb);
            Call<List<MovieModal>> data = api.getData(index);
            data.enqueue(new Callback<List<MovieModal>>() {
                @Override
                public void onResponse(Call<List<MovieModal>> call, Response<List<MovieModal>> response) {
                    if(response.isSuccessful()){
                        movieModalArrayList.remove(movieModalArrayList.size()-1);
                        List<MovieModal> list = response.body();
                        if(list.size() > 0){
                            movieModalArrayList.addAll(list);
                        }else{
                            myMovieAdapter.setMoreDataAvailable(false);
                            Toast.makeText(getApplicationContext(),"No More Data Available",Toast.LENGTH_LONG).show();
                        }
                        myMovieAdapter.notifyDataChanged();
                    }else{
                        Log.e("MainActivityLoadMoreDat"," Load More Response Error "+String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(Call<List<MovieModal>> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
