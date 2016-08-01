package com.kouchen.mininetlive.contracts;

import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;
import com.kouchen.mininetlive.ui.PayChannel;
import java.util.List;

/**
 * Created by cainli on 16/7/14.
 */
public interface ActivityDetailContract {

    interface View extends BaseView<Presenter> {

        void onGetMemberListSuccess(List<String> uid);
    }

    interface Presenter extends BasePresenter {

        void pay(String aid, PayChannel channel, int count, int payType);

        void join(String aid);

        void leave(String aid);

        void appointment(String aid);

        void getOnlineMemberList(String aid);
    }
}
