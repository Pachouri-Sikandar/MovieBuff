package com.quintype.moviebuff.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.quintype.moviebuff.R;
import com.quintype.moviebuff.parser.MovieResponseParser;
import com.quintype.moviebuff.util.Utils;


import java.util.ArrayList;

/**
 * Created by pachouri on 21/1/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Context context;
    private ArrayList<MovieResponseParser> movieResponseParsers = new ArrayList<>();
    public static OnMovieItemClickedListener onMovieItemClickedListener;

    public MovieListAdapter(Context context, ArrayList<MovieResponseParser> movieResponseParsers, OnMovieItemClickedListener onMovieItemClickedListener) {
        this.context = context;
        this.movieResponseParsers = movieResponseParsers;
        this.onMovieItemClickedListener = onMovieItemClickedListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_items_recyclerview, parent, false);
            return new HeaderViewHolder(view);

        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie_list, parent, false);
            return new ChildItemsViewHolder(view);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType);

    }


    private MovieResponseParser getItem(int position) {
        return movieResponseParsers.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            Log.v("Adapter", "within header");
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.textViewHeader.setText(Utils.PREVIOUS_SEARCH);
        } else if (holder instanceof ChildItemsViewHolder) {
            Log.v("Adapter", "within child");
            ChildItemsViewHolder childItemsViewHolder = (ChildItemsViewHolder) holder;
            MovieResponseParser movieParseObj = getItem(position - 1);
            childItemsViewHolder.textViewMovieTitle.setText(movieParseObj.getTitle());
            Uri uri = Uri.parse(movieParseObj.getPoster());
            childItemsViewHolder.imageViewMovieCover.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (movieResponseParsers != null && !movieResponseParsers.isEmpty()) {
            count = movieResponseParsers.size() + 1;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    public static class ChildItemsViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMovieTitle;
        public SimpleDraweeView imageViewMovieCover;
        public CardView cardView;

        public ChildItemsViewHolder(View itemView) {
            super(itemView);
            textViewMovieTitle = (TextView) itemView.findViewById(R.id.text_view_movie_title);
            imageViewMovieCover = (SimpleDraweeView) itemView.findViewById(R.id.image_view_movie_cover);
            cardView = (CardView) itemView.findViewById(R.id.movie_card_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMovieItemClickedListener.onMovieItemClicked(getAdapterPosition() - 1);
                }
            });
        }

    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        protected TextView textViewHeader;

        public HeaderViewHolder(View v) {
            super(v);
            textViewHeader = (TextView) v.findViewById(R.id.text_view_header);
        }
    }

    public interface OnMovieItemClickedListener {
        public void onMovieItemClicked(int position);
    }
}
