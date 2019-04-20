package ch.nkwazi.popularmoviesstage2;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nkwazi on 29.01.19.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private List<Review> reviewList;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.review_detail, parent, false);
        return new ReviewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review reviewItem = reviewList.get(position);
        Picasso.get().load(R.drawable.baseline_account_circle_black_18dp).into(holder.reviewAvatar);
        holder.reviewAuthor.setText(reviewItem.getAuthor());
        holder.reviewContent.setText(reviewItem.getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewList != null){
            return reviewList.size();
        } else {
            return 0;
        }
    }

    public void setItems(List<Review> items) {
        reviewList = new ArrayList<>();
        reviewList.clear();
        this.reviewList.addAll(items);
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.review_avatar)
        ImageView reviewAvatar;
        @BindView(R.id.review_author)
        TextView reviewAuthor;
        @BindView(R.id.review_content)
        TextView reviewContent;
        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
