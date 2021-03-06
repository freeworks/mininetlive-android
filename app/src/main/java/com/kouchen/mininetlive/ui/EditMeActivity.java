package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.contracts.AccountContract;
import com.kouchen.mininetlive.di.components.DaggerAccountComponent;
import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.models.UserInfo;
import com.kouchen.mininetlive.ui.widget.GlideCircleTransform;
import com.kouchen.mininetlive.ui.widget.ItemView2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cainli on 16/6/24.
 */
public class EditMeActivity extends AbsTitlebarActivity implements ActionSheet.ActionSheetListener, AccountContract.View,
        TakePhoto.TakeResultListener, InvokeListener {

    private static final String TAG = "EditMeActivity";
    private static final int REQUEST_CAPTURE_IMAGE = 1001;
    private static final int REQUEST_CHOOSE = 1002;
    private static final int REQUEST_UPDATE_NICKNAME = 1003;
    private static final int REQUEST_UPDATE_SEX = 1004;
    private static final int REQUEST_BIND_PHONE = 1005;

    @BindView(R.id.avatarItem)
    ItemView2 avatarItem;
    @BindView(R.id.nicknameItem)
    ItemView2 nickanmeItem;
    @BindView(R.id.genderItem)
    ItemView2 genderItem;
    @BindView(R.id.phoneItem)
    ItemView2 phoneItem;

    @Inject
    AccountPresenter presenter;

    @Override
    protected void initInject() {
        DaggerAccountComponent.builder()
                .accountModule(new AccountModule(this))
                .netComponent(((MNLApplication) getApplication()).getNetComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            Glide.with(this)
                    .load(userInfo.getAvatar())
                    .centerCrop()
                    .placeholder(R.drawable.ic_avatar_default)
                    .crossFade()
                    .transform(new GlideCircleTransform(this))
                    .into(avatarItem.getAvatarView());

            nickanmeItem.setValue(userInfo.getNickname());
            genderItem.setValue(userInfo.getGender() == 0 ? "女" : "男");
            phoneItem.setValue(TextUtils.isEmpty(userInfo.getPhone()) ? "点击绑定" : userInfo.getPhone());
        }
    }

    @Override
    protected void initView(View contentView) {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_me_edit;
    }

    @Override
    public String getTitleString() {
        return "个人信息";
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.avatarItem:
                ActionSheet.createBuilder(this, getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles("拍照", "从手机相册选择")
                        .setCancelableOnTouchOutside(true)
                        .setListener(this).show();
                break;
            case R.id.nicknameItem:
                intent = new Intent(this, EditNickActivity.class);
                startActivityForResult(intent, REQUEST_UPDATE_NICKNAME);
                break;
            case R.id.genderItem:
                intent = new Intent(this, EditGenderActivity.class);
                startActivityForResult(intent, REQUEST_UPDATE_SEX);
                break;
            case R.id.phoneItem:
                intent = new Intent(this, BindPhoneActivity.class);
                startActivityForResult(intent, REQUEST_BIND_PHONE);
                break;
        }
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        if (index == 0) {
//            String state = Environment.getExternalStorageState();
//            if (state.equals(Environment.MEDIA_MOUNTED)) {
//                Intent intent = new Intent();
//                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                String filepath = Environment.getExternalStorageDirectory() + "/tmpAvatar.png";
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filepath)));
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
//            } else {
//                Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
//            }
            String filepath = Environment.getExternalStorageDirectory() + "/tmpAvatar.png";
            getTakePhoto().onPickFromCapture(Uri.fromFile(new File(filepath)));
        } else if (index == 1) {
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");//相片类型
//            startActivityForResult(intent, REQUEST_CHOOSE);
            getTakePhoto().onPickFromGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTakePhoto().onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
//            Uri uri;
//            String fPath;
            switch (requestCode) {
//                case REQUEST_CHOOSE:
//                    uri = data.getData(); // 得到Uri
//                    if (uri == null) {
//                        //use bundle to get data
//                        Bundle bundle = data.getExtras();
//                        if (bundle != null) {
//                            Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
//                            //spath :生成图片取个名字和路径包含类型
//                            String filepath = Environment.getExternalStorageDirectory() + "/tmpAvatar.png";
//                            if (saveImage(photo, filepath)) {
//                                fPath = filepath;
//                            } else {
//                                return;
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "获取图片失败!", Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                    } else {
//                        fPath = uri2filePath(uri); // 转化为路径
//                        if (fPath == null || !new File(fPath).exists()) {
//                            Toast.makeText(this, "获取照片失败,请重试!", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        Log.d(TAG, "onActivityResult: " + fPath);
//                    }
//                    presenter.uploadAvatar(fPath);
//                    break;
//                case REQUEST_CAPTURE_IMAGE:
//                    String filepath = Environment.getExternalStorageDirectory() + "/tmpAvatar.png";
//                    if (!new File(filepath).exists()) {
//                        Toast.makeText(this, "获取照片失败,请重试!", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    presenter.uploadAvatar(filepath);
//                    break;
                case REQUEST_UPDATE_NICKNAME:
                    String nickname = data.getStringExtra("nickname");
                    nickanmeItem.setValue(nickname);
                    break;

                case REQUEST_BIND_PHONE:
                    String phone = data.getStringExtra("phone");
                    phoneItem.setValue(phone);
                    break;
            }
        }
    }

    public static boolean saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private String uri2filePath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, proj, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(index);
        return path;
    }

    @Override
    public void showProgress() {
        showProgressView("正在上传");
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof String) {
            Glide.with(this)
                    .load(getUserInfo().getAvatar())
                    .centerCrop()
                    .placeholder(R.drawable.ic_avatar_default)
                    .crossFade()
                    .transform(new GlideCircleTransform(this))
                    .into(avatarItem.getAvatarView());

        }
        Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    InvokeParam invokeParam;
    TakePhoto takePhoto;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        String originalPath = result.getImage().getOriginalPath();
        if(originalPath!=null && new File(originalPath).exists()){
            presenter.uploadAvatar(originalPath);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        //DO NOTHING
    }
}
