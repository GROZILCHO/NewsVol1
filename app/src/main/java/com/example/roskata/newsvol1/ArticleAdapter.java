package com.example.roskata.newsvol1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {

    static class ViewHolder {
        private TextView section;
        private TextView title;
        private TextView authorName;
        private TextView publishedDate;
        private ImageView imageView;
    }

    public ArticleAdapter(@NonNull Context context, List<Article> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        Article currArticle = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
            holder = new ViewHolder();
            holder.section = convertView.findViewById(R.id.txtSection);
            holder.title = convertView.findViewById(R.id.txtTitle);
            holder.authorName = convertView.findViewById(R.id.txtAuthor);
            holder.publishedDate = convertView.findViewById(R.id.txtDate);
            holder.imageView = convertView.findViewById(R.id.imageView);
        }

        if (holder != null) {

            if (currArticle.getSection() != null) {
                holder.section.setText(currArticle.getSection());
            } else {
                holder.section.setVisibility(View.GONE);
            }

            holder.title.setText(currArticle.getTitle());

            holder.authorName.setText(currArticle.getAuthor());

            holder.publishedDate.setText(currArticle.getFormattedDate());

            if (currArticle != null) {
                String imgUrl = currArticle.getImageUrl();
                Picasso.get().load(imgUrl).into(holder.imageView);
            } else {
                holder.imageView.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}
