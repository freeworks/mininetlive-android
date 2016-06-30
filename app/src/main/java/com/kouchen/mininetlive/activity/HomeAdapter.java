package com.kouchen.mininetlive.activity;

import android.content.Intent;
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

import butterknife.ButterKnife;

/**
 * Created by cainli on 16/6/25.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ActivityViewHolder> {

    enum ITEM_TYPE {
        ITEM_TYPE_RECOMMEND,
        ITEM_TYPE_GENERAL
    }

    private HomeModel homeModel;

    @Override
    public HomeAdapter.ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_RECOMMEND.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
            return new ActivityViewHolder1(view);
        }
        if (viewType == ITEM_TYPE.ITEM_TYPE_GENERAL.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity2, parent, false);
            return new ActivityViewHolder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        ActivityInfo[] activityInfos = homeModel.getActivityByItemIndex(position);
        if (holder instanceof ActivityViewHolder1) {
            ((ActivityViewHolder1) holder).setActivity(activityInfos[0]);
        } else if (holder instanceof ActivityViewHolder2) {
            ((ActivityViewHolder2) holder).setActivity(activityInfos);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position < homeModel.recommondItemSize() ? ITEM_TYPE.ITEM_TYPE_RECOMMEND.ordinal() : ITEM_TYPE.ITEM_TYPE_GENERAL.ordinal();
    }


    @Override
    public int getItemCount() {
        if (homeModel == null) {
            return 0;
        }
        return homeModel.getCount();
    }

    public void setData(HomeModel homeModel) {
        this.homeModel = homeModel;
        notifyDataSetChanged();
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {

        public ActivityViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ActivityViewHolder2 extends ActivityViewHolder {

        private ImageView frontCover0;
        private TextView title0, playCount0;

        private ImageView frontCover1;
        private TextView title1, playCount1;

        private View view0, view1;

        public ActivityViewHolder2(View view) {
            super(view);
            view0 = view.findViewById(R.id.item0);
            frontCover0 = (ImageView) view0.findViewById(R.id.frontCover);
            title0 = (TextView) view0.findViewById(R.id.title);
            playCount0 = (TextView) view0.findViewById(R.id.playCount);
            view1 = view.findViewById(R.id.item1);
            frontCover1 = (ImageView) view1.findViewById(R.id.frontCover);
            title1 = (TextView) view1.findViewById(R.id.title);
            playCount1 = (TextView) view1.findViewById(R.id.playCount);
        }

        public void setActivity(ActivityInfo[] infos) {
            final ActivityInfo info0 = infos[0];
            Glide.with(itemView.getContext())
                    .load(info0.getFrontCover())
                    .centerCrop()
                    .centerCrop()
                    .placeholder(R.drawable.img_default)
                    .crossFade()
                    .transform(new GlideRoundTransform(itemView.getContext(), DensityUtil.dip2px(itemView.getContext(), 1.5f)))
                    .into(frontCover0);
            title0.setText(info0.getTitle());
            playCount0.setText(String.valueOf(info0.getPlayCount()));
            view0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ActivityDetailActivity.class);
                    intent.putExtra("activityInfo", info0);
                    view.getContext().startActivity(intent);

                }
            });


            final ActivityInfo info1 = infos[1];
            if (info1 != null) {
                itemView.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext())
                        .load(info1.getFrontCover())
                        .centerCrop()
                        .placeholder(R.drawable.img_default)
                        .crossFade()
                        .transform(new GlideRoundTransform(itemView.getContext(), DensityUtil.dip2px(itemView.getContext(), 1.5f)))
                        .into(frontCover1);
                title1.setText(info1.getTitle());
                playCount1.setText(String.valueOf(info1.getPlayCount()));
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ActivityDetailActivity.class);
                        intent.putExtra("activityInfo", info1);
                        view.getContext().startActivity(intent);

                    }
                });

            } else {
                itemView.setVisibility(View.INVISIBLE);
            }

        }
    }

    static class ActivityViewHolder1 extends ActivityViewHolder {

        private ImageView frontCover;
        private TextView title, nickname, onlineCount, appointCount, state;

        public ActivityViewHolder1(View view) {
            super(view);
            frontCover = (ImageView) view.findViewById(R.id.frontCover);
            title = (TextView) view.findViewById(R.id.title);
            onlineCount = (TextView) view.findViewById(R.id.onlineCount);
            appointCount = (TextView) view.findViewById(R.id.appointmentCount);
            nickname = (TextView) view.findViewById(R.id.nickname);
            state = (TextView) view.findViewById(R.id.state);
        }

        public void setActivity(final ActivityInfo info) {
            Glide.with(itemView.getContext())
                    .load(info.getFrontCover())
                    .centerCrop()
                    .placeholder(R.drawable.img_default)
                    .crossFade()
                    .transform(new GlideRoundTransform(itemView.getContext(), DensityUtil.dip2px(itemView.getContext(), 1.5f)))
                    .into(frontCover);
            title.setText(info.getTitle());
            nickname.setText(info.getOwner().getNickname());
            appointCount.setVisibility(View.INVISIBLE);
            onlineCount.setVisibility(View.INVISIBLE);
            if (info.getStreamType() == 1) {
                state.setVisibility(View.VISIBLE);
                switch (info.getActivityState()) {
                    case 0:
                        state.setBackgroundResource(R.drawable.blue_round_bg);
                        state.setText("预告");
                        appointCount.setVisibility(View.VISIBLE);
                        appointCount.setText("预约：" + String.valueOf(info.getAppointmentCount()) + "人");
                        break;
                    case 1:
                        state.setBackgroundResource(R.drawable.red_round_bg);
                        state.setText("直播中");
                        onlineCount.setVisibility(View.VISIBLE);
                        onlineCount.setText(String.valueOf(11111) + "人在看");
                        break;
                    case 2:
                        state.setBackgroundResource(R.drawable.grey_round_bg);
                        state.setText("已结束");
                        break;

                }
                state.setText(info.getActivityStateStr());
            } else {
                state.setVisibility(View.INVISIBLE);
            }
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


