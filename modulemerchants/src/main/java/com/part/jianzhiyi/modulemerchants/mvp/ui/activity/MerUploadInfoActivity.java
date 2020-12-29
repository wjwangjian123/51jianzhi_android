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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.part.jianzhiyi.corecommon.utils.KeyBoardHelperUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MerUploadInfoActivity extends BaseActivity<AuthPresenter> implements AuthContract.IAuthView, TakePhoto.TakeResultListener, InvokeListener {

    private ImageView mIvReturn;
    private TextView mTvReason;
    private View mViewReason;
    private RelativeLayout mRlLicense;
    private ImageView mIvPhotoLicense;
    private ImageView mIvCameraLicense;
    private TextView mTvUploadLicense;
    private ImageView mIvSeeLicense;
    private ImageView mIvCancelLicense;

//    private RelativeLayout mRlOne;
//    private ImageView mIvPhotoOne;
//    private ImageView mIvCameraOne;
//    private TextView mTvUploadOne;
//    private ImageView mIvSeeOne;
//    private ImageView mIvCancelOne;
//    private RelativeLayout mRlTwo;
//    private ImageView mIvPhotoTwo;
//    private ImageView mIvCameraTwo;
//    private TextView mTvUploadTwo;
//    private ImageView mIvSeeTwo;
//    private ImageView mIvCancelTwo;

    private InputFilteEditText mEtJigou;
    private InputFilteEditText mEtId;
    private InputFilteEditText mEtName;
    private TextView mTvNext;
    private LinearLayout mLayoutBottom;
    private LinearLayout mLlLayout;

    private String scope;
    private String create_time;
    private String create_money;
    private String address;
    private String times;
    private String type;
    private String f_numid;
    private KeyBoardHelperUtil boardHelper;
    private int bottomHeight = 0;

    //mtype:1相册 2相机
    private int mtype = 1;
    //photoType:1营业执照 2国徽面 3人像面
//    private int photoType = 1;
    //jumpType:1选择上传图片 2查看大图
    private int jumpTypeLicense = 1;
    //    private int jumpTypeOne = 1;
//    private int jumpTypeTwo = 1;
    private String imagePath;
    private String img_license;
    private String img_z;
    private String img_f;
    private int urlType;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mPresenter.getmdAdd("78");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mer_upload_info;
    }

    @Override
    protected void initView() {
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mTvReason = (TextView) findViewById(R.id.tv_reason);
        mViewReason = (View) findViewById(R.id.view_reason);
        mRlLicense = (RelativeLayout) findViewById(R.id.rl_license);
        mIvPhotoLicense = (ImageView) findViewById(R.id.iv_photo_license);
        mIvCameraLicense = (ImageView) findViewById(R.id.iv_camera_license);
        mTvUploadLicense = (TextView) findViewById(R.id.tv_upload_license);
        mIvSeeLicense = (ImageView) findViewById(R.id.iv_see_license);
        mIvCancelLicense = (ImageView) findViewById(R.id.iv_cancel_license);
//        mRlOne = (RelativeLayout) findViewById(R.id.rl_one);
//        mIvPhotoOne = (ImageView) findViewById(R.id.iv_photo_one);
//        mIvCameraOne = (ImageView) findViewById(R.id.iv_camera_one);
//        mTvUploadOne = (TextView) findViewById(R.id.tv_upload_one);
//        mIvSeeOne = (ImageView) findViewById(R.id.iv_see_one);
//        mIvCancelOne = (ImageView) findViewById(R.id.iv_cancel_one);
//        mRlTwo = (RelativeLayout) findViewById(R.id.rl_two);
//        mIvPhotoTwo = (ImageView) findViewById(R.id.iv_photo_two);
//        mIvCameraTwo = (ImageView) findViewById(R.id.iv_camera_two);
//        mTvUploadTwo = (TextView) findViewById(R.id.tv_upload_two);
//        mIvSeeTwo = (ImageView) findViewById(R.id.iv_see_two);
//        mIvCancelTwo = (ImageView) findViewById(R.id.iv_cancel_two);
        mEtJigou = (InputFilteEditText) findViewById(R.id.et_jigou);
        mEtId = (InputFilteEditText) findViewById(R.id.et_id);
        mEtName = (InputFilteEditText) findViewById(R.id.et_name);
        mTvNext = (TextView) findViewById(R.id.tv_next);
        mLayoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);
        mLlLayout = (LinearLayout) findViewById(R.id.ll_layout);
        setToolBarVisible(false);
        setImmerseLayout(findViewById(R.id.rl_title));
    }

    @Override
    protected void initData() {
        urlType = getIntent().getIntExtra("urlType", 0);
        if (urlType == 1) {
            mPresenter.getEnterpriseInfo();
        }
        //为 Activity 指定 windowSoftInputMode
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        boardHelper = new KeyBoardHelperUtil(MerUploadInfoActivity.this);
        boardHelper.onCreate();
        boardHelper.setOnKeyBoardStatusChangeListener(onKeyBoardStatusChangeListener);
        mLayoutBottom.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = mLayoutBottom.getHeight();
            }
        });
    }

    private KeyBoardHelperUtil.OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener = new KeyBoardHelperUtil.OnKeyBoardStatusChangeListener() {
        @Override
        public void OnKeyBoardPop(int keyBoardheight) {
            if (bottomHeight > keyBoardheight) { //底部空白区域高度大于软键盘高度，没遮住
                mLayoutBottom.setVisibility(View.GONE);
            } else {
                int offset = bottomHeight - keyBoardheight;
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mLlLayout.getLayoutParams();
                lp.topMargin = offset;
                mLayoutBottom.setLayoutParams(lp);
            }
        }

        @Override
        public void OnKeyBoardClose(int oldKeyBoardheight) {
            if (View.VISIBLE != mLayoutBottom.getVisibility()) {
                mLayoutBottom.setVisibility(View.VISIBLE);
            }
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mLlLayout.getLayoutParams();
            if (lp.topMargin != 0) {
                lp.topMargin = 0;
                mLayoutBottom.setLayoutParams(lp);
            }
        }
    };

    private long clickTime = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialogExitTip();
            }
        });
        mRlLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                photoType = 1;
                if (jumpTypeLicense == 1) {
                    //弹框选择上传图片
                    initDialogTakePhoto();
                } else if (jumpTypeLicense == 2) {
                    //点击查看大图
                    Intent intent = new Intent(MerUploadInfoActivity.this, MerPhotoViewActivity.class);
                    intent.putExtra("imageUrl", img_license);
                    startActivity(intent);
                }

            }
        });
//        mRlOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                photoType = 2;
//                if (jumpTypeOne == 1) {
//                    //弹框选择上传图片
//                    initDialogTakePhoto();
//                } else if (jumpTypeOne == 2) {
//                    //点击查看大图
//                    Intent intent = new Intent(MerUploadInfoActivity.this, MerPhotoViewActivity.class);
//                    intent.putExtra("imageUrl", img_f);
//                    startActivity(intent);
//                }
//            }
//        });
//        mRlTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                photoType = 3;
//                if (jumpTypeTwo == 1) {
//                    //弹框选择上传图片
//                    initDialogTakePhoto();
//                } else if (jumpTypeTwo == 2) {
//                    //点击查看大图
//                    Intent intent = new Intent(MerUploadInfoActivity.this, MerPhotoViewActivity.class);
//                    intent.putExtra("imageUrl", img_z);
//                    startActivity(intent);
//                }
//            }
//        });
        mIvCancelLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTypeLicense = 1;
                img_license = "";
                mIvCameraLicense.setVisibility(View.VISIBLE);
                mTvUploadLicense.setVisibility(View.VISIBLE);
                mIvSeeLicense.setVisibility(View.GONE);
                mIvCancelLicense.setVisibility(View.GONE);
                mIvPhotoLicense.setImageResource(R.color.transparency);
            }
        });
//        mIvCancelOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jumpTypeOne = 1;
//                img_f = "";
//                mIvCameraOne.setVisibility(View.VISIBLE);
//                mTvUploadOne.setVisibility(View.VISIBLE);
//                mIvSeeOne.setVisibility(View.GONE);
//                mIvCancelOne.setVisibility(View.GONE);
//                mIvPhotoOne.setImageResource(R.color.transparency);
//            }
//        });
//        mIvCancelTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jumpTypeTwo = 1;
//                img_z = "";
//                mIvCameraTwo.setVisibility(View.VISIBLE);
//                mTvUploadTwo.setVisibility(View.VISIBLE);
//                mIvSeeTwo.setVisibility(View.GONE);
//                mIvCancelTwo.setVisibility(View.GONE);
//                mIvPhotoTwo.setImageResource(R.color.transparency);
//            }
//        });
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(img_license)) {
                    showToast("请上传营业执照");
                    return;
                }
                if (TextUtils.isEmpty(mEtJigou.getText().toString().trim())) {
                    showToast("请填写机构名称");
                    return;
                }
                if (TextUtils.isEmpty(mEtId.getText().toString().trim())) {
                    showToast("请填写社会信用代码");
                    return;
                }
                if (TextUtils.isEmpty(mEtName.getText().toString().trim())) {
                    showToast("请填写法人姓名");
                    return;
                }
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    MGetEnterpriseInfoEntity.DataBean dataBean = new MGetEnterpriseInfoEntity.DataBean();
                    dataBean.setCompany_num(mEtId.getText().toString().trim());
                    dataBean.setNames(mEtName.getText().toString().trim());
                    dataBean.setCompany(mEtJigou.getText().toString().trim());
                    dataBean.setYy_img(img_license);
                    MGetEnterpriseInfoEntity.CompanyInfoBean companyInfoBean = new MGetEnterpriseInfoEntity.CompanyInfoBean();
                    companyInfoBean.setCompany_num(mEtId.getText().toString().trim());
                    companyInfoBean.setScope(scope);
                    companyInfoBean.setCreate_time(create_time);
                    companyInfoBean.setName(mEtName.getText().toString().trim());
                    companyInfoBean.setCreate_money(create_money);
                    companyInfoBean.setAddress(address);
                    companyInfoBean.setCompany(mEtJigou.getText().toString().trim());
                    companyInfoBean.setTimes(times);
                    companyInfoBean.setType(type);
                    MGetEnterpriseInfoEntity.CorporateBean corporateBean = new MGetEnterpriseInfoEntity.CorporateBean();
                    corporateBean.setF_name(mEtName.getText().toString().trim());
                    corporateBean.setLdentity_imgz(img_z);
                    corporateBean.setF_numid(f_numid);
                    corporateBean.setLdentity_imgf(img_f);
                    Map<String, Object> requestBody = new HashMap<>();
                    requestBody.put("data", dataBean);
                    requestBody.put("company_info", companyInfoBean);
                    requestBody.put("token", PreferenceUUID.getInstence().getToken());
                    requestBody.put("corporate", corporateBean);
                    mPresenter.getCheckEnterprise(requestBody);
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    private void initDialogExitTip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerUploadInfoActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerUploadInfoActivity.this).inflate(R.layout.dialog_tip_auth, null, false);
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
                //跳转到商户主页，我的
                Intent intent = new Intent(MerUploadInfoActivity.this, MerMainActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                MerUploadInfoActivity.this.finish();
            }
        });
        tvcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void initDialogDistinguish(String company, String id, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerUploadInfoActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerUploadInfoActivity.this).inflate(R.layout.dialog_tip_upload_distinguish, null, false);
        alertDialog.setView(inflate);
        TextView mTvCompany = (TextView) inflate.findViewById(R.id.tv_company);
        TextView mTvId = (TextView) inflate.findViewById(R.id.tv_id);
        TextView mTvName = (TextView) inflate.findViewById(R.id.tv_name);
        TextView mTvFou = (TextView) inflate.findViewById(R.id.tv_fou);
        TextView mTvFill = (TextView) inflate.findViewById(R.id.tv_fill);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        mTvCompany.setText(company);
        mTvId.setText(id);
        mTvName.setText(name);
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
                mEtJigou.setText(company);
                mEtId.setText(id);
                mEtName.setText(name);
            }
        });
    }

    private void initDialogTakePhoto() {
        final BottomSheetDialog dialog = new BottomSheetDialog(MerUploadInfoActivity.this);
        View view = LayoutInflater.from(MerUploadInfoActivity.this).inflate(R.layout.dialog_takephoto, null, false);
        dialog.setContentView(view);
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        TextView tvalbum = dialog.findViewById(R.id.tv_album);
        TextView tvcamera = dialog.findViewById(R.id.tv_camera);
        TextView cancel = dialog.findViewById(R.id.tv_cancel);
        dialog.show();
        tvalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtype = 1;
                dialog.dismiss();
                //权限
                initPermission();
            }
        });
        tvcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtype = 2;
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
        if (mtype == 1) {
            //相册数据
            getTakePhoto().onPickFromGallery();
        } else if (mtype == 2) {
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
                uriForFile = FileProvider.getUriForFile(MerUploadInfoActivity.this, "com.part.jianzhiyi.fileprovider", imageFile);
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
            PermissionManager.handlePermissionsResult(MerUploadInfoActivity.this, type, invokeParam, this);
        }
    }

    @Override
    protected AuthPresenter createPresenter() {
        return new AuthPresenter(this);
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
        if (mtype == 1) {
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
            //营业执照
            mPresenter.getEnterprise(requestBody);
//            if (photoType == 1) {
//                //营业执照
//                mPresenter.getEnterprise(requestBody);
//            } else if (photoType == 2) {
//                //国徽面
//                mPresenter.getUpload(requestBody);
//            } else if (photoType == 3) {
//                //人像面
//                mPresenter.getCorporate(requestBody);
//            }
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
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(MerUploadInfoActivity.this, this));
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).enableReserveRaw(true).create();
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
                    //营业执照
                    mPresenter.getEnterprise(requestBody);
//                    if (photoType == 1) {
//                        //营业执照
//                        mPresenter.getEnterprise(requestBody);
//                    } else if (photoType == 2) {
//                        //国徽面
//                        mPresenter.getUpload(requestBody);
//                    } else if (photoType == 3) {
//                        //人像面
//                        mPresenter.getCorporate(requestBody);
//                    }
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

    @Override
    public void startIntent() {

    }

    @Override
    public void updategetBaidNumber(MIDPositiveEntity midPositiveEntity) {

    }

    @Override
    public void updategetCheckEnterprise(MAuthSuccessEntity mAuthSuccessEntity) {
        if (mAuthSuccessEntity != null) {
            if (mAuthSuccessEntity.getCode().equals("200")) {
                //提交成功
                MobclickAgent.onEvent(MerUploadInfoActivity.this, "mer_submit_success");
                mPresenter.getmdAdd("79");
//                initDialogTip(mAuthSuccessEntity.getMsg());
                if (mAuthSuccessEntity.getAudit_status() == 1) {
                    //审核通过
                    Intent intent = new Intent(MerUploadInfoActivity.this, MerAuthSuccessActivity.class);
                    intent.putExtra("type", 3);
                    startActivity(intent);
                    finish();
                } else if (mAuthSuccessEntity.getAudit_status() == 0) {
                    //人工审核
                    Intent intent = new Intent(MerUploadInfoActivity.this, MerAuthSuccessActivity.class);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                    finish();
                }
            } else {
                showToast(mAuthSuccessEntity.getMsg());
            }
        }
    }

    private void initDialogTip(String mtip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MerUploadInfoActivity.this);
        AlertDialog alertDialog = builder.create();
        View inflate = LayoutInflater.from(MerUploadInfoActivity.this).inflate(R.layout.dialog_tip_update, null, false);
        TextView tip = inflate.findViewById(R.id.tv_tip);
        TextView ok = inflate.findViewById(R.id.tv_ok);
        alertDialog.setView(inflate);
        tip.setText(mtip);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparency);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //显示
        alertDialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    public void updategetNumidSuccess(MAuthSuccessEntity mAuthSuccessEntity) {

    }

    @Override
    public void updategetEnterprise(MEnterpriseInfoEntity mEnterpriseInfoEntity) {
        if (mEnterpriseInfoEntity != null) {
            MobclickAgent.onEvent(MerUploadInfoActivity.this, "mer_upload_success");
            showToast(mEnterpriseInfoEntity.getMsg());
            if (mEnterpriseInfoEntity.getData() != null && mEnterpriseInfoEntity.getData().getYy_img() != null && mEnterpriseInfoEntity.getData().getYy_img() != "") {
                img_license = mEnterpriseInfoEntity.getData().getYy_img();
                mIvCameraLicense.setVisibility(View.GONE);
                mTvUploadLicense.setVisibility(View.GONE);
                mIvSeeLicense.setVisibility(View.VISIBLE);
                mIvCancelLicense.setVisibility(View.VISIBLE);
                jumpTypeLicense = 2;
                Glide.with(MerUploadInfoActivity.this).load(mEnterpriseInfoEntity.getData().getYy_img()).transform(new GlideRoundTransformUtil(MerUploadInfoActivity.this, 2)).into(mIvPhotoLicense);
            }
            if (mEnterpriseInfoEntity.getCode().equals("200")) {
                if (mEnterpriseInfoEntity.getCompany_info() != null) {
                    scope = mEnterpriseInfoEntity.getCompany_info().getScope();
                    create_time = mEnterpriseInfoEntity.getCompany_info().getCreate_time();
                    create_money = mEnterpriseInfoEntity.getCompany_info().getCreate_money();
                    address = mEnterpriseInfoEntity.getCompany_info().getAddress();
                    times = mEnterpriseInfoEntity.getCompany_info().getTimes();
                    type = mEnterpriseInfoEntity.getCompany_info().getType();
                }
                if (mEnterpriseInfoEntity.getData() != null && mEnterpriseInfoEntity.getData().getCompany() != null && mEnterpriseInfoEntity.getData().getCompany() != "" && mEnterpriseInfoEntity.getData().getCompany_num() != null && mEnterpriseInfoEntity.getData().getCompany_num() != "" && mEnterpriseInfoEntity.getData().getNames() != null && mEnterpriseInfoEntity.getData().getNames() != "") {
                    initDialogDistinguish(mEnterpriseInfoEntity.getData().getCompany(), mEnterpriseInfoEntity.getData().getCompany_num(), mEnterpriseInfoEntity.getData().getNames());
                }
            }
        }
    }

    @Override
    public void updategetCorporate(MIDFaPositiveEntity midFaPositiveEntity) {
//        if (midFaPositiveEntity != null) {
//            MobclickAgent.onEvent(MerUploadInfoActivity.this, "mer_upload_success");
//            showToast(midFaPositiveEntity.getMsg());
//            if (midFaPositiveEntity.getCorporate() != null && midFaPositiveEntity.getCorporate().getLdentity_imgz() != null && midFaPositiveEntity.getCorporate().getLdentity_imgz() != "") {
//                img_z = midFaPositiveEntity.getCorporate().getLdentity_imgz();
//                mIvCameraTwo.setVisibility(View.GONE);
//                mTvUploadTwo.setVisibility(View.GONE);
//                mIvSeeTwo.setVisibility(View.VISIBLE);
//                mIvCancelTwo.setVisibility(View.VISIBLE);
//                jumpTypeTwo = 2;
//                Glide.with(MerUploadInfoActivity.this).load(midFaPositiveEntity.getCorporate().getLdentity_imgz()).transform(new GlideRoundTransformUtil(MerUploadInfoActivity.this, 2)).into(mIvPhotoTwo);
//            }
//            if (midFaPositiveEntity.getCode().equals("200")) {
//                f_numid = midFaPositiveEntity.getCorporate().getF_numid();
//            }
//        }
    }

    @Override
    public void updategetNumid(MAuthInfoEntity mAuthInfoEntity) {

    }

    @Override
    public void updategetUpload(MFileEntity mFileEntity) {
//        if (mFileEntity != null) {
//            MobclickAgent.onEvent(MerUploadInfoActivity.this, "mer_upload_success");
//            showToast(mFileEntity.getMsg());
//            if (mFileEntity.getPath() != null && mFileEntity.getPath() != "") {
//                img_f = mFileEntity.getPath();
//                mIvCameraOne.setVisibility(View.GONE);
//                mTvUploadOne.setVisibility(View.GONE);
//                mIvSeeOne.setVisibility(View.VISIBLE);
//                mIvCancelOne.setVisibility(View.VISIBLE);
//                jumpTypeOne = 2;
//                Glide.with(MerUploadInfoActivity.this).load(mFileEntity.getPath()).transform(new GlideRoundTransformUtil(MerUploadInfoActivity.this, 2)).into(mIvPhotoOne);
//            }
//        }
    }

    @Override
    public void updategetEnterpriseInfo(MGetEnterpriseInfoEntity mGetEnterpriseInfoEntity) {
        if (mGetEnterpriseInfoEntity != null) {
            if (mGetEnterpriseInfoEntity.getCode().equals("200")) {
                img_license = mGetEnterpriseInfoEntity.getData().getYy_img();
                img_z = mGetEnterpriseInfoEntity.getCorporate().getLdentity_imgz();
                img_f = mGetEnterpriseInfoEntity.getCorporate().getLdentity_imgf();
                scope = mGetEnterpriseInfoEntity.getCompany_info().getScope();
                create_time = mGetEnterpriseInfoEntity.getCompany_info().getCreate_time();
                create_money = mGetEnterpriseInfoEntity.getCompany_info().getCreate_money();
                address = mGetEnterpriseInfoEntity.getCompany_info().getAddress();
                times = mGetEnterpriseInfoEntity.getCompany_info().getTimes();
                type = mGetEnterpriseInfoEntity.getCompany_info().getType();
                f_numid = mGetEnterpriseInfoEntity.getCorporate().getF_numid();
                mEtName.setText(mGetEnterpriseInfoEntity.getData().getNames());
                mEtId.setText(mGetEnterpriseInfoEntity.getData().getCompany_num());
                mEtJigou.setText(mGetEnterpriseInfoEntity.getData().getCompany());
                if (mGetEnterpriseInfoEntity.getData() != null) {
                    if (mGetEnterpriseInfoEntity.getData().getYy_img() != null && mGetEnterpriseInfoEntity.getData().getYy_img() != "") {
                        Glide.with(MerUploadInfoActivity.this).load(mGetEnterpriseInfoEntity.getData().getYy_img()).transform(new GlideRoundTransformUtil(MerUploadInfoActivity.this, 2)).into(mIvPhotoLicense);
                        mIvCameraLicense.setVisibility(View.GONE);
                        mTvUploadLicense.setVisibility(View.GONE);
                        mIvSeeLicense.setVisibility(View.VISIBLE);
                        mIvCancelLicense.setVisibility(View.VISIBLE);
                        jumpTypeLicense = 2;
                    }
                }
//                if (mGetEnterpriseInfoEntity.getCorporate() != null) {
//                    if (mGetEnterpriseInfoEntity.getCorporate().getLdentity_imgf() != null && mGetEnterpriseInfoEntity.getCorporate().getLdentity_imgf() != "") {
//                        Glide.with(MerUploadInfoActivity.this).load(mGetEnterpriseInfoEntity.getCorporate().getLdentity_imgf()).transform(new GlideRoundTransformUtil(MerUploadInfoActivity.this, 2)).into(mIvPhotoOne);
//                        mIvCameraOne.setVisibility(View.GONE);
//                        mTvUploadOne.setVisibility(View.GONE);
//                        mIvSeeOne.setVisibility(View.VISIBLE);
//                        mIvCancelOne.setVisibility(View.VISIBLE);
//                        jumpTypeOne = 2;
//                    }
//                    if (mGetEnterpriseInfoEntity.getCorporate().getLdentity_imgz() != null && mGetEnterpriseInfoEntity.getCorporate().getLdentity_imgz() != "") {
//                        Glide.with(MerUploadInfoActivity.this).load(mGetEnterpriseInfoEntity.getCorporate().getLdentity_imgz()).transform(new GlideRoundTransformUtil(MerUploadInfoActivity.this, 2)).into(mIvPhotoTwo);
//                        mIvCameraTwo.setVisibility(View.GONE);
//                        mTvUploadTwo.setVisibility(View.GONE);
//                        mIvSeeTwo.setVisibility(View.VISIBLE);
//                        mIvCancelTwo.setVisibility(View.VISIBLE);
//                        jumpTypeTwo = 2;
//                    }
//                }
                if (mGetEnterpriseInfoEntity.getReason() != null) {
                    if (mGetEnterpriseInfoEntity.getReason().getEnterprise_status() == 3) {
                        //审核失败
                        mTvReason.setVisibility(View.VISIBLE);
                        mViewReason.setVisibility(View.VISIBLE);
                        mTvReason.setText(mGetEnterpriseInfoEntity.getReason().getHt_reason());
                    } else {
                        mTvReason.setVisibility(View.GONE);
                        mViewReason.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            initDialogExitTip();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("企业认证页面");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("企业认证页面");
        MobclickAgent.onPause(this);
    }
}
