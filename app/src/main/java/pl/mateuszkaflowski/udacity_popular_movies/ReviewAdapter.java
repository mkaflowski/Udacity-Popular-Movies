package pl.mateuszkaflowski.udacity_popular_movies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Review;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Trailer;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Review> reviews;

    public ReviewAdapter(List<Review> list){
        reviews = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_list_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReviewViewHolder reviewHolder = (ReviewViewHolder) holder;
        Review review = reviews.get(position);

        reviewHolder.author.setText(review.getAuthor());
        reviewHolder.content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }



    private class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView author;
        TextView content;

        ReviewViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.tvAuthor);
            content = (TextView) itemView.findViewById(R.id.tvReview);
        }

    }
}
