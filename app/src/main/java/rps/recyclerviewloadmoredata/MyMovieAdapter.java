package rps.recyclerviewloadmoredata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class MyMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    static Context mContext;
    List<MovieModal> mArray;
    private LoadMoreListener mloadMoreListener;
    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;
    private boolean isLoading = false, isMoreDataAvailable = true;
    public MyMovieAdapter(Context mainActivity, ArrayList<MovieModal> movieModalArrayList) {
        this.mArray = movieModalArrayList;
        this.mContext = mainActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_id,parent,false);
        View loadview = LayoutInflater.from(parent.getContext()).inflate(R.layout.loadmore_progressbar,parent,false);
        if(viewType == TYPE_MOVIE){
            return new MyViewHolder(view);
        }else{
            return new LoadHolder(loadview);
        }

    }
    public class LoadHolder extends RecyclerView.ViewHolder{
            public ProgressBar pbr;
        public LoadHolder(View itemView) {
            super(itemView);
            pbr = (ProgressBar)itemView.findViewById(R.id.progressBar);
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMovieName;
        TextView tvMovieRating;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvMovieName = (TextView)itemView.findViewById(R.id.txtmoviename);
            tvMovieRating = (TextView)itemView.findViewById(R.id.txtmovierating);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && mloadMoreListener!=null){
            isLoading = true;
            mloadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)==TYPE_MOVIE){
            MovieModal modal = mArray.get(position);
            ((MyViewHolder)holder).tvMovieName.setText(modal.title);
            ((MyViewHolder)holder).tvMovieRating.setText("Rating"+modal.rating);
        }

    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }
    public void setLoadMoreListener(LoadMoreListener listener){
            this.mloadMoreListener = listener;
    }
    @Override
    public int getItemViewType(int position) {
        if(mArray.get(position).type.equals("movie")){
            return TYPE_MOVIE;
        }else{
            return TYPE_LOAD;
        }
    }
   /* interface LoadMoreListener{
        void onLoadMore();
    }*/
    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }

}
