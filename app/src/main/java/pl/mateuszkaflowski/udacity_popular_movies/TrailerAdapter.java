package pl.mateuszkaflowski.udacity_popular_movies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Trailer;

public class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ClickCallback clickCallback;
    private List<Trailer> trailerList;

    public TrailerAdapter(List<Trailer> list){
        trailerList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrailerViewHolder trailerHolder = (TrailerViewHolder) holder;
        Trailer trailer = trailerList.get(position);

        trailerHolder.title.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public interface ClickCallback {
        void itemClick(View view, int position, Trailer trailer);
    }

    private class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        TrailerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.trailerTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickCallback!=null)
                clickCallback.itemClick(v,getAdapterPosition(), trailerList.get(getAdapterPosition()));
        }
    }
}
