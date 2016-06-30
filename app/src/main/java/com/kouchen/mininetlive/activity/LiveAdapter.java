package com.kouchen.mininetlive.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.util.DensityUtil;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.GlideRoundTransform;

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

        private ImageView frontCover;
        private TextView title, nickname, onlineCount, state;

        public ActivityViewHolder(View view) {
            super(view);
            frontCover = (ImageView) view.findViewById(R.id.frontCover);
            title = (TextView) view.findViewById(R.id.title);
            onlineCount = (TextView) view.findViewById(R.id.onlineCount);
            nickname = (TextView) view.findViewById(R.id.nickname);
            state = (TextView) view.findViewById(R.id.state);
        }

        public void setActivity(ActivityInfo info) {
            Glide.with(itemView.getContext())
                    .load(info.getFrontCover())
                    .centerCrop()
                    .placeholder(R.drawable.img_default)
                    .crossFade()
                    .transform(new GlideRoundTransform(itemView.getContext(), DensityUtil.dip2px(itemView.getContext(),1f)))
                    .into(frontCover);
            title.setText(info.getTitle());
            onlineCount.setText("111111");
            nickname.setText(info.getOwner().getNickname());
            state.setBackgroundResource(R.drawable.blue_round_bg);
            state.setText("直播中");
        }
    }

}


