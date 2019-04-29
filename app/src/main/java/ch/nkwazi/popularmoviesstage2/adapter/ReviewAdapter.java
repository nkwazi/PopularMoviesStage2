package ch.nkwazi.popularmoviesstage2.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.nkwazi.popularmoviesstage2.R;
import ch.nkwazi.popularmoviesstage2.model.Review;

/**
 * Created by nkwazi on 29.01.19.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private List<Review> reviewList;

    public ReviewAdapter(ArrayList<Review> reviews, Context context) {
        this.reviewList = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_detail, parent, false);
        return new ReviewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.author.setText(reviewList.get(position).getAuthor());
        holder.content.setText(reviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() { return reviewList.size(); }

    public void setItems(List<Review> items) {
        this.reviewList = items;
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.review_author)
        TextView author;

        @BindView(R.id.review_content)
        TextView content;

        ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
