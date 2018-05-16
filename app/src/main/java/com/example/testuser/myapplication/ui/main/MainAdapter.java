package com.example.testuser.myapplication.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testuser.myapplication.R;
import com.example.testuser.myapplication.model.Post;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private final List<Post> mPosts;

    MainAdapter(List<Post> posts) {
        mPosts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post post = mPosts.get(position);
        holder.getTitleTextView().setText(post.getTitle());
        holder.getDescriptionTextView().setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTextView;
        private final TextView mDescriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title);
            mDescriptionTextView = itemView.findViewById(R.id.description);
        }

        TextView getTitleTextView() {
            return mTitleTextView;
        }

        TextView getDescriptionTextView() {
            return mDescriptionTextView;
        }
    }

}
