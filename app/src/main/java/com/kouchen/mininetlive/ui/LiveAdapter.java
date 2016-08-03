package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.ui.widget.FrontCoverImageView;
import com.kouchen.mininetlive.ui.widget.GlideRoundTransform;
import com.kouchen.mininetlive.utils.DisplayUtil;

import java.util.List;

/**
 * Created by cainli on 16/6/25.
 */
public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ActivityViewHolder> {


    private List<ActivityInfo> activityList;

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        ActivityViewHolder holder = new ActivityViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        holder.setActivity(activityList.get(position));
    }

    @Override
    public int getItemCount() {
        if(activityList == null){
            return 0;
        }
        return activityList.size();
    }

    public void setData(List<ActivityInfo> list) {
        activityList = list;
        notifyDataSetChanged();
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {

        private FrontCoverImageView frontCover;
        private TextView title, nickname, onlineCount, state;

        public ActivityViewHolder(View view) {
            super(view);
            frontCover = (FrontCoverImageView) view.findViewById(R.id.frontCover);
            title = (TextView) view.findViewById(R.id.title);
            onlineCount = (TextView) view.findViewById(R.id.onlineCount);
            nickname = (TextView) view.findViewById(R.id.nickname);
            state = (TextView) view.findViewById(R.id.state);
        }

        public void setActivity(final ActivityInfo info) {
            frontCover.setUrl(info.getFrontCover());
            title.setText(info.getTitle());
            onlineCount.setText("111111");
            nickname.setText(info.getOwner().getNickname());
            state.setBackgroundResource(R.drawable.blue_round_bg);
            state.setText("直播中");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ActivityDetailActivity.class);
                    intent.putExtra("activityInfo", info);
                    view.getContext().startActivity(intent);

                }
            });
        }
    }

}


