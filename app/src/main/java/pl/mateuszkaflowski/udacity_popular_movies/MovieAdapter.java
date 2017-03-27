package pl.mateuszkaflowski.udacity_popular_movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> movieList;
    private LayoutInflater inflater;
    private Context context;
    private ClickCallback clickCallback;

    public MovieAdapter(Context context, List<Movie> movieList) {
        inflater = LayoutInflater.from(context);

        this.context = context;
        this.movieList = movieList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        MovieViewHolder movieHolder = (MovieViewHolder) holder;
        Movie movie = movieList.get(position);

        Picasso.with(context).load(movie.getPosterImageUrl()).into(movieHolder.poster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public interface ClickCallback {
        void itemClick(View view, int position);
    }

    private class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            poster.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickCallback!=null)
                clickCallback.itemClick(view,getAdapterPosition());
        }
    }
}
