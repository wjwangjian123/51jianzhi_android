package com.part.jianzhiyi.modulemerchants.mvp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.ui.InputFilteEditText;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseActivity;
import com.part.jianzhiyi.modulemerchants.loader.GlideRoundTransformUtil;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MAuthInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MAuthSuccessEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MFileEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MGetEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDFaPositiveEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDPositiveEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.AuthContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.AuthPresenter;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MerAuthActivity extends BaseActivity<AuthPresenter> implements AuthContract.IAuthView, TakePhoto.TakeResultListener, InvokeListener {

    private ImageView mIvReturn;
    private TextView mTvReason;
    private View mViewReason;
    private RelativeLayout mRlOne;
    private ImageView mIvPhotoOne;
    private ImageView mIvCameraOne;
    private TextView mTvUploadOne;
    private ImageView mIvSeeOne;
    private ImageView mIvCancelOne;
    private RelativeLayout mRlTwo;
    private ImageView mIvPhotoTwo;
    private ImageView mIvCameraTwo;
    private TextView mTvUploadTwo;
    private ImageView mIvSeeTwo;
    private ImageView mIvCancelTwo;
    private InputFilteEditText mTvAdminName;
    private InputFilteEditText mTvAdminId;
    private InputFilteEditText mTvAdminCompany;
    private TextView mTvNext;

    //type:1相册 2相机
    private int type = 1;
    //photoType:1国徽面 2人像面
    private int photoType = 1;
    //jumpType:1选择上传图片 2查看大图
    private int jumpTypeOne = 1;
    private int jumpTypeTwo = 1;
    private String imagePath;
    private String img_z;
    private String img_f;
    private int urlType;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getmdAdd("74");
        MobclickAgent.onEvent(MerAuthActivity.this, "mer_auth_in");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_auth;
    }

    @Override
    protected void initView() {
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mTvReason = (TextView) findViewById(R.id.tv_reason);
        mViewReason = (View) findViewById(R.id.view_reason);
        mRlOne = (RelativeLayout) findViewById(R.id.rl_one);
        mIvPhotoOne = (ImageView) findViewById(R.id.iv_photo_one);
        mIvCameraOne = (ImageView) findViewById(R.id.iv_camera_one);
        mTvUploadOne = (TextView) findViewById(R.id.tv_upload_one);
        mIvSeeOne = (ImageView) findViewById(R.id.iv_see_one);
        mIvCancelOne = (ImageView) findViewById(R.id.iv_cancel_one);
        mRlTwo = (RelativeLayout) findViewById(R.id.rl_two);
        mIvPhotoTwo = (ImageView) findViewById(R.id.iv_photo_two);
        mIvCameraTwo = (ImageView) findViewById(R.id.iv_camera_two);
        mTvUploadTwo = (TextView) findViewById(R.id.tv_upload_two);
        mIvSeeTwo = (ImageView) findViewById(R.id.iv_see_two);
        mIvCancelTwo = (ImageView) findViewById(R.id.iv_cancel_two);
        mTvAdminName = (InputFilteEditText) findViewById(R.id.tv_admin_name);
        mTvAdminId = (InputFilteEditText) findViewById(R.id.tv_admin_id);
        mTvAdminCompany = (InputFilteEditText) findViewById(R.id.tv_admin_company);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    @Override
    protected void initData() {
        urlType = getIntent().getIntExtra("urlType", 0);
        if (urlType == 1) {
            mPresenter.getNumid();
        }
    }

    private long clickTime = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialogTip();
            }
        });
        mRlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoType = 1;
                if (jumpTypeOne == 1) {
                    //弹框选择上传图片
                    initDialogTakePhoto();
                } else if (jumpTypeOne == 2) {
                    //点击查看大图
                    Intent intent = new Intent(MerAuthActivity.this, MerPhotoViewActivity.class);
                    intent.putExtra("imageUrl", img_f);
                    startActivity(intent);
                }
            }
        });
        mRlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoType = 2;
                if (jumpTypeTwo == 1) {
                    //弹框选择上传图片
                    initDialogTakePhoto();
                } else if (jumpTypeTwo == 2) {
                    //点击查看大图
                    Intent intent = new Intent(MerAuthActivity.this, MerPhotoViewActivity.class);
                    intent.putExtra("imageUrl", img_z);
                    startActivity(intent);
                }
            }
        });
        mIvCancelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTypeOne = 1;
                img_f = "";
                mIvCameraOne.setVisibility(View.VISIBLE);
                mTvUploadOne.setVisibility(View.VISIBLE);
                mIvSeeOne.setVisibility(View.GONE);
                mIvCancelOne.setVisibility(View.GONE);
                mIvPhotoOne.setImageResource(R.color.transparency);
            }
        });
        mIvCancelTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTypeTwo = 1;
                img_z = "";
                mIvCameraTwo.setVisibility(View.VISIBLE);
                mTvUploadTwo.setVisibility(View.VISIBLE);
                mIvSeeTwo.setVisibility(View.GONE);
                mIvCancelTwo.setVisibility(View.GONE);
                mIvPhotoTwo.setImageResource(R.color.transparency);
            }
        });
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(img_f)) {
                    showToast("请上传身份证国徽面照片");
                    return;
                }
                if (TextUtils.isEmpty(img_z)) {
                    showToast("请上传身份证人像面照片");
                    return;
                }
                if (TextUtils.isEmpty(mTvAdminName.getText().toString().trim())) {
                    showToast("请填写管理员姓名");
                    return;
                }
                if (TextUtils.isEmpty(mTvAdminId.getText().toString().trim())) {
                    showToast("请填写管理员身份证号");
                    return;
                }
                if (TextUtils.isEmpty(mTvAdminCompany.getText().toString().trim())) {
                    showToast("请填写公司名称");
                    return;
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    mPresenter.getNumidSuccess(img_z, mTvAdminName.getText().toString().trim(), mTvAdminId.getText().toString().trim(), img_f, mTvAdminCompany.getText().toString().trim());
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    private void initDialogTakePhoto() {
        final BottomSheetDialog dialog = new BottomSheetDialog(MerAuthActivity.this);
        View view = LayoutInflater.from(MerAuthActivity.this).inflate(R.layout.dialog_takephoto, null, false);
        dialog.setContentView(view);
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        TextView tvalbum = dialog.findViewById(R.id.tv_album);
        TextView tvcamera = dialog.findViewById(R.id.tv_camera);
        TextView cancel = dialog.findViewById(R.id.tv_cancel);
        dialog.show();
        tvalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                dialog.dismiss();
                //权限
                initPermission();
            }
        });
        tvcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                dialog.dismiss();
                //权限
                initPermission();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        } else {
            initInfo();
        }
    }

    private int REQUEST_TAKE_PHOTO_CODE = 1001;
    private Uri uriForFile;
    private File imageFile;

    private void initInfo() {
        //type为1，相册   type为2，相机
        if (type == 1) {
            //相册数据
            getTakePhoto().onPickFromGallery();
        } else if (type == 2) {
            // 步骤一：创建存储照片的文件
            String cameraPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "51";
            //相册文件夹
            File cameraFolder = new File(cameraPath);
            if (!cameraFolder.exists()) {
                cameraFolder.mkdirs();
            }
            //保存的图片文件
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String imagePath1 = cameraFolder.getAbsolutePath() + File.separator + "IMG_" + simpleDateFormat.format(new Date()) + ".jpg";
            imageFile = new File(imagePath1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //步骤二：Android 7.0及以上获取文件 Uri
                uriForFile = FileProvider.getUriForFile(MerAuthActivity.this, "com.part.jianzhiyi.fileprovider", imageFile);
            } else {
                //步骤三：获取文件Uri
                uriForFile = Uri.fromFile(imageFile);
            }
            //步骤四：调取系统拍照
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO_CODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.CAMERA);
        }
        // 如果需要的权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            initInfo();
        } else {
            // 否则，建议请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1000);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        if (requestCode == 1000) {
            if (requestCode == 1000 && hasAllPermissionsGranted(grantResults)) {
                initInfo();
            } else {
                showToast("请开启必要权限");
            }
        } else {
            PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
            PermissionManager.handlePermissionsResult(MerAuthActivity.this, type, invokeParam, this);
        }
    }

    @Override
    protected AuthPresenter createPresenter() {
        return new AuthPresenter(this);
    }

    @Override
    public void startIntent() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
    }

    @Override
    public void takeSuccess(TResult result) {
        if (result == null) {
            return;
        }
        if (type == 1) {
            TImage image = result.getImage();
            if (image.getCompressPath() != null) {
                imagePath = image.getCompressPath();
            } else {
                imagePath = image.getOriginalPath();
            }
            File file = new File(imagePath);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("token", PreferenceUUID.getInstence().getToken())
                    .addFormDataPart("file", file.getName(),
                            RequestBody.create(MediaType.parse("image/jpg"), file))
                    .build();
            if (photoType == 1) {
                //国徽面
                mPresenter.getUpload(requestBody);
            } else if (photoType == 2) {
                //人像面
                mPresenter.getBaidNumber(requestBody);
            }
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(MerAuthActivity.this, this));
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(500 * 1024).setMaxPixel(800).enableReserveRaw(true).create();
            takePhoto.onEnableCompress(compressConfig, true);
        }
        return takePhoto;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO_CODE) {
            if (resultCode == RESULT_OK) {
                if (imageFile != null && imageFile.exists()) {
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("token", PreferenceUUID.getInstence().getToken())
                            .addFormDataPart("file", imageFile.getName(),
                                    RequestBody.create(MediaType.parse("image/jpg"), imageFile))
                            .build();
                    if (photoType == 1) {
                        //国徽面
                        mPresenter.getUpload(requestBody);
                    } else if (photoType == 2) {
                        //人像面
                        mPresenter.getBaidNumber(requestBody);
                    }
                }
            }
        } else {
            getTakePhoto().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    private long clickTime1 = 0;

    private void initDialogTip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerAuthActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerAuthActivity.this).inflate(R.layout.dialog_tip_auth, null, false);
        TextView tvgiveup = inflate.findViewById(R.id.tv_giveup);
        TextView tvcontinue = inflate.findViewById(R.id.tv_continue);
        alertDialog.setView(inflate);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        tvgiveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    //跳转到商户主页，我的
                    Intent intent = new Intent(MerAuthActivity.this, MerMainActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    MerAuthActivity.this.finish();
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        tvcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void initDialogDistinguish(String name, String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerAuthActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerAuthActivity.this).inflate(R.layout.dialog_tip_auth_distinguish, null, false);
        alertDialog.setView(inflate);
        TextView mTvName = (TextView) inflate.findViewById(R.id.tv_name);
        TextView mTvId = (TextView) inflate.findViewById(R.id.tv_id);
        TextView mTvFou = (TextView) inflate.findViewById(R.id.tv_fou);
        TextView mTvFill = (TextView) inflate.findViewById(R.id.tv_fill);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        mTvName.setText(name);
        mTvId.setText(id);
        mTvFou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        mTvFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mTvAdminName.setText(name);
                mTvAdminId.setText(id);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            initDialogTip();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void updategetBaidNumber(MIDPositiveEntity midPositiveEntity) {
        if (midPositiveEntity != null) {
            showToast(midPositiveEntity.getMsg());
            if (midPositiveEntity.getData() != null && midPositiveEntity.getData().getImg_z() != null && midPositiveEntity.getData().getImg_z() != "") {
                img_z = midPositiveEntity.getData().getImg_z();
                mIvCameraTwo.setVisibility(View.GONE);
                mTvUploadTwo.setVisibility(View.GONE);
                mIvSeeTwo.setVisibility(View.VISIBLE);
                mIvCancelTwo.setVisibility(View.VISIBLE);
                jumpTypeTwo = 2;
                Glide.with(MerAuthActivity.this).load(midPositiveEntity.getData().getImg_z()).transform(new GlideRoundTransformUtil(MerAuthActivity.this, 2)).into(mIvPhotoTwo);
            }
            if (midPositiveEntity.getCode().equals("200")) {
                if (midPositiveEntity.getData() != null && midPositiveEntity.getData().getName() != null && midPositiveEntity.getData().getName() != "" && midPositiveEntity.getData().getNumber() != null && midPositiveEntity.getData().getNumber() != "") {
                    initDialogDistinguish(midPositiveEntity.getData().getName(), midPositiveEntity.getData().getNumber());
                }
            }
        }
    }

    @Override
    public void updategetCheckEnterprise(MAuthSuccessEntity mAuthSuccessEntity) {

    }

    @Override
    public void updategetNumidSuccess(MAuthSuccessEntity mAuthSuccessEntity) {
        if (mAuthSuccessEntity != null) {
            if (mAuthSuccessEntity.getCode().equals("200")) {
                mPresenter.getmdAdd("75");
                MobclickAgent.onEvent(MerAuthActivity.this, "mer_auth_success");
                if (mAuthSuccessEntity.getAudit_status() == 0) {
                    //人工审核
                    Intent intent = new Intent(MerAuthActivity.this, MerAuthSuccessActivity.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                    finish();
                } else if (mAuthSuccessEntity.getAudit_status() == 1) {
                    //审核通过
                    Intent intent = new Intent(MerAuthActivity.this, MerAuthSuccessActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    finish();
                }
            } else {
                showToast(mAuthSuccessEntity.getMsg());
            }
        }
    }

    @Override
    public void updategetEnterprise(MEnterpriseInfoEntity mEnterpriseInfoEntity) {

    }

    @Override
    public void updategetCorporate(MIDFaPositiveEntity midFaPositiveEntity) {

    }

    @Override
    public void updategetNumid(MAuthInfoEntity mAuthInfoEntity) {
        if (mAuthInfoEntity != null) {
            if (mAuthInfoEntity.getCode().equals("200")) {
                img_z = mAuthInfoEntity.getData().getImg_z();
                img_f = mAuthInfoEntity.getData().getImg_f();
                mTvAdminName.setText(mAuthInfoEntity.getData().getName());
                mTvAdminId.setText(mAuthInfoEntity.getData().getNumber());
                mTvAdminCompany.setText(mAuthInfoEntity.getData().getCompany());
                if (mAuthInfoEntity.getData() != null && !mAuthInfoEntity.getData().getImg_f().equals(null) && !mAuthInfoEntity.getData().getImg_f().equals("")) {
                    Glide.with(MerAuthActivity.this).load(mAuthInfoEntity.getData().getImg_f()).transform(new GlideRoundTransformUtil(MerAuthActivity.this, 2)).into(mIvPhotoOne);
                    mIvCameraOne.setVisibility(View.GONE);
                    mTvUploadOne.setVisibility(View.GONE);
                    mIvSeeOne.setVisibility(View.VISIBLE);
                    mIvCancelOne.setVisibility(View.VISIBLE);
                    jumpTypeOne = 2;
                }
                if (mAuthInfoEntity.getData() != null && !mAuthInfoEntity.getData().getImg_z().equals(null) && !mAuthInfoEntity.getData().getImg_z().equals("")) {
                    Glide.with(MerAuthActivity.this).load(mAuthInfoEntity.getData().getImg_z()).transform(new GlideRoundTransformUtil(MerAuthActivity.this, 2)).into(mIvPhotoTwo);
                    mIvCameraTwo.setVisibility(View.GONE);
                    mTvUploadTwo.setVisibility(View.GONE);
                    mIvSeeTwo.setVisibility(View.VISIBLE);
                    mIvCancelTwo.setVisibility(View.VISIBLE);
                    jumpTypeTwo = 2;
                }
                if (mAuthInfoEntity.getData() != null && mAuthInfoEntity.getData().getNumid_status() == 3) {
                    //审核失败
                    mTvReason.setVisibility(View.VISIBLE);
                    mViewReason.setVisibility(View.VISIBLE);
                    mTvReason.setText(mAuthInfoEntity.getData().getHt_reason());
                } else {
                    mTvReason.setVisibility(View.GONE);
                    mViewReason.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void updategetUpload(MFileEntity mFileEntity) {
        if (mFileEntity != null) {
            showToast(mFileEntity.getMsg());
            if (mFileEntity.getPath() != null && mFileEntity.getPath() != "") {
                img_f = mFileEntity.getPath();
                mIvCameraOne.setVisibility(View.GONE);
                mTvUploadOne.setVisibility(View.GONE);
                mIvSeeOne.setVisibility(View.VISIBLE);
                mIvCancelOne.setVisibility(View.VISIBLE);
                jumpTypeOne = 2;
                Glide.with(MerAuthActivity.this).load(mFileEntity.getPath()).transform(new GlideRoundTransformUtil(MerAuthActivity.this, 2)).into(mIvPhotoOne);
            }
        }
    }

    @Override
    public void updategetEnterpriseInfo(MGetEnterpriseInfoEntity mGetEnterpriseInfoEntity) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("个人认证页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("个人认证页面");
        MobclickAgent.onPause(this);
    }
}
