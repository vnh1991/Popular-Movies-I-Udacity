package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivekh on 2/6/2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private List<Result> moviesData = new ArrayList<>();
    private LayoutInflater layoutInflaterObj;
    private Context ctx;

    RVAdapter(Context context, ResponseBean response) {
        this.layoutInflaterObj = LayoutInflater.from(context);
        this.moviesData = response.getResults();
        ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflaterObj.inflate(R.layout.relative_child_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("vivek", "poster path=" + moviesData.get(position).getPosterPath());
        String imgURL = NetworkUtils.createImageFetchUrl(moviesData.get(position).getPosterPath());

        Log.d("vivek", "url=" + imgURL);

        if (imgURL != null && imgURL.length() > 0)
            Picasso.with(ctx).load(imgURL).into(holder.imgMoviePoster);


        String title = moviesData.get(position).getTitle();
        Double rating=moviesData.get(position).getVoteAverage();
        String date=moviesData.get(position).getReleaseDate();
        String synopsis=moviesData.get(position).getOverview();

        final Intent i = new Intent(ctx, MovieDetailActivity.class);
        i.putExtra("poster", imgURL);
        i.putExtra("title",title!=null?title:"");
        i.putExtra("rating",rating!=null?rating.toString():0);
        i.putExtra("date",date!=null?date:"");
        i.putExtra("plot",synopsis!=null?synopsis:"");


        holder.imgMoviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMoviePoster;

        ViewHolder(View itemView) {
            super(itemView);
            imgMoviePoster = (ImageView) itemView.findViewById(R.id.img_movie_poster);
        }
    }
}