package com.part.jianzhiyi.modulemerchants.mvp.ui.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.meiqia.core.MQManager;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.FrescoUtil;
import com.part.jianzhiyi.modulemerchants.R;
import com.part.jianzhiyi.modulemerchants.base.BaseFragment;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MMineContract;
import com.part.jianzhiyi.modulemerchants.mvp.presenter.MMinePresenter;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.ChooseIdentityActivity;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerAuthActivity;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerAuthHtmlActivity;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerFeedbackActivity;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerMyCompanyActivity;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerMyWalletActivity;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerRecruitActivity;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerSettingActivity;
import com.part.jianzhiyi.modulemerchants.mvp.ui.activity.MerUploadInfoActivity;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jyx on 2020/11/23
 *
 * @author jyx
 * @describe 我的页面
 */
public class MerMineFragment extends BaseFragment<MMinePresenter> implements MMineContract.IMMineView, TakePhoto.TakeResultListener, InvokeListener {

    private SimpleDraweeView mMineIvImg;
    private TextView mMineTvPhone;
    private TextView mMineTvId;
    private TextView mMineTvAuth;
    private TextView mMineTvMoney;
    private TextView mMineTvSee;
    private LinearLayout mMineLlExtension;
    private RelativeLayout mMineRlSwitch;
    private RelativeLayout mMineRlService;
    private RelativeLayout mMineRlFeedback;
    private RelativeLayout mMineRlSet;
    private int mcertStatus = 1;
    private int type = 1;
    private String imagePath;
    private String url;
    private String company;
    private int isSing;
    private String avatar;
    private String username;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mMineIvImg = view.findViewById(R.id.mine_iv_img);
        mMineTvPhone = view.findViewById(R.id.mine_tv_phone);
        mMineTvId = view.findViewById(R.id.mine_tv_id);
        mMineTvAuth = view.findViewById(R.id.mine_tv_auth);
        mMineTvMoney = view.findViewById(R.id.mine_tv_money);
        mMineTvSee = view.findViewById(R.id.mine_tv_see);
        mMineLlExtension = view.findViewById(R.id.mine_ll_extension);
        mMineRlSwitch = view.findViewById(R.id.mine_rl_switch);
        mMineRlService = view.findViewById(R.id.mine_rl_service);
        mMineRlFeedback = view.findViewById(R.id.mine_rl_feedback);
        mMineRlSet = view.findViewById(R.id.mine_rl_set);
        setToolbarVisible(false);
    }

    @Override
    protected MMinePresenter createPresenter() {
        return new MMinePresenter(this);
    }

    private long clickTime = 0;
    private long clickTime1 = 0;
    private long clickTime2 = 0;
    private long clickTime3 = 0;
    private long clickTime4 = 0;
    private long clickTime5 = 0;
    private long clickTime6 = 0;
    private long clickTime7 = 0;

    @Override
    protected void setListener() {
        super.setListener();
        mMineIvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime > 3000) {
                    clickTime = System.currentTimeMillis();
                    //弹框选择
                    initDialogTakePhoto();
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mMineTvAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - clickTime1 > 3000) {
                    clickTime1 = System.currentTimeMillis();
                    //认证状态 0未完成个人认证 1个人商户审核 2未认证企业信息 3个人认证失败4未认证企业信息 5企业认证审核中 6企业认证成功 7企业认证失败
                    if (mcertStatus == 0) {
                        //前去个人认证
                        if (isSing == 0) {
                            //未签署进入协议
                            Intent intent = new Intent(getActivity(), MerAuthHtmlActivity.class);
                            startActivity(intent);
                        } else if (isSing == 1) {
                            Intent intent = new Intent(getActivity(), MerAuthActivity.class);
                            intent.putExtra("urlType", 0);
                            startActivity(intent);
                        }
                    }
                    if (mcertStatus == 2) {
                        //前去企业认证
                        mPresenter.getmdAdd("77");
                        Intent intent = new Intent(getActivity(), MerMyCompanyActivity.class);
                        intent.putExtra("type", 0);
                        intent.putExtra("url", url);
                        intent.putExtra("company", company);
                        startActivity(intent);
                    }
                    if (mcertStatus == 3) {
                        //前去个人认证修改
                        Intent intent = new Intent(getActivity(), MerAuthActivity.class);
                        intent.putExtra("urlType", 1);
                        startActivity(intent);
                    }
                    if (mcertStatus == 4) {
                        //前去企业认证
                        mPresenter.getmdAdd("77");
                        Intent intent = new Intent(getActivity(), MerMyCompanyActivity.class);
                        intent.putExtra("type", 0);
                        intent.putExtra("url", url);
                        intent.putExtra("company", company);
                        startActivity(intent);
                    }
                    if (mcertStatus == 6) {
                        //跳转到我的企业
                        mPresenter.getmdAdd("77");
                        Intent intent = new Intent(getActivity(), MerMyCompanyActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("url", url);
                        intent.putExtra("company", company);
                        startActivity(intent);
                    }
                    if (mcertStatus == 7) {
                        //前去企业认证
                        Intent intent = new Intent(getActivity(), MerUploadInfoActivity.class);
                        intent.putExtra("urlType", 1);
                        startActivity(intent);
                    }
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mMineTvSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "mer_mine_wallet");
                if (System.currentTimeMillis() - clickTime2 > 3000) {
                    clickTime2 = System.currentTimeMillis();
                    //跳转到钱包
                    Intent intent = new Intent(getActivity(), MerMyWalletActivity.class);
                    startActivity(intent);
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mMineLlExtension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "mer_mine_extension");
                if (System.currentTimeMillis() - clickTime3 > 3000) {
                    clickTime3 = System.currentTimeMillis();
                    //跳转到招聘推广
                    mPresenter.getmdAdd("83");
                    Intent intent = new Intent(getActivity(), MerRecruitActivity.class);
                    startActivity(intent);
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mMineRlSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "mer_mine_switch");
                if (System.currentTimeMillis() - clickTime4 > 3000) {
                    clickTime4 = System.currentTimeMillis();
                    //跳转到切换身份
                    mPresenter.getmdAdd("68");
                    Intent intent = new Intent(getActivity(), ChooseIdentityActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mMineRlService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "mer_mine_service");
                if (System.currentTimeMillis() - clickTime5 > 3000) {
                    clickTime5 = System.currentTimeMillis();
                    //跳转到美洽客服
                    //联系客服
                    checkPermission();
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mMineRlFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "mer_mine_feedback");
                if (System.currentTimeMillis() - clickTime6 > 3000) {
                    clickTime6 = System.currentTimeMillis();
                    //跳转到意见反馈
                    Intent intent = new Intent(getActivity(), MerFeedbackActivity.class);
                    startActivity(intent);
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
        mMineRlSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "mer_mine_set");
                if (System.currentTimeMillis() - clickTime7 > 3000) {
                    clickTime7 = System.currentTimeMillis();
                    //跳转到设置
                    Intent intent = new Intent(getActivity(), MerSettingActivity.class);
                    startActivity(intent);
                } else {
                    showToast("点击过于频繁请稍后再试");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mer_mine;
    }

    @Override
    protected void afterCreate() {

    }

    private void initDialogTakePhoto() {
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_takephoto, null, false);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermission();
        } else {
            initInfo();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        // 快手SDK所需相关权限，存储权限，此处配置作用于流量分配功能，关于流量分配，详情请咨询商务;如果您的APP不需要快手SDK的流量分配功能，则无需申请SD卡权限
        if (!(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        // 如果需要的权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            HashMap<String, String> clientInfo = new HashMap<>();
            clientInfo.put("name", username);
            clientInfo.put("avatar", avatar);
            clientInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            clientInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            clientInfo.put("身份", "商户");
            HashMap<String, String> updateInfo = new HashMap<>();
            updateInfo.put("name", username);
            updateInfo.put("avatar", avatar);
            updateInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            updateInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            updateInfo.put("身份", "商户");
            Intent intent = new MQIntentBuilder(getActivity())
                    .setClientInfo(clientInfo)
                    .updateClientInfo(updateInfo)
                    .setCustomizedId(PreferenceUUID.getInstence().getUserId())
                    .build();
            startActivity(intent);
        } else {
            // 否则，建议请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!(getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
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
        } else if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            HashMap<String, String> clientInfo = new HashMap<>();
            clientInfo.put("name", username);
            clientInfo.put("avatar", avatar);
            clientInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            clientInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            clientInfo.put("身份", "商户");
            HashMap<String, String> updateInfo = new HashMap<>();
            updateInfo.put("name", username);
            updateInfo.put("avatar", avatar);
            updateInfo.put("tel", PreferenceUUID.getInstence().getUserPhone());
            updateInfo.put("userId", PreferenceUUID.getInstence().getUserId());
            updateInfo.put("身份", "商户");
            Intent intent = new MQIntentBuilder(getActivity())
                    .setClientInfo(clientInfo)
                    .updateClientInfo(updateInfo)
                    .setCustomizedId(PreferenceUUID.getInstence().getUserId())
                    .build();
            startActivity(intent);
        } else {
            PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
            PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
        }
    }

    private CropOptions cropOptions;

    private void initInfo() {
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
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
            File imageFile = new File(imagePath1);
            getTakePhoto().onPickFromCaptureWithCrop(Uri.fromFile(imageFile), cropOptions);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMerUserinfo();
        MobclickAgent.onPageStart("商户端我的页面");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mPresenter != null) {
                mPresenter.getMerUserinfo();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
    }

    @Override
    public void takeSuccess(TResult result) {
        if (result == null) {
            return;
        }
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
        mPresenter.getAvatar(requestBody);
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
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(getActivity(), this));
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).enableReserveRaw(true).create();
            takePhoto.onEnableCompress(compressConfig, true);
        }
        return takePhoto;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    @Override
    public void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity) {
        if (mUserInfoEntity != null) {
            isSing = mUserInfoEntity.getUserinfo().getIs_sing();
            url = mUserInfoEntity.getUserinfo().getImg();
            company = mUserInfoEntity.getUserinfo().getCompany();
            mcertStatus = mUserInfoEntity.getUserinfo().getCert_status();
            mMineTvId.setText("ID：" + mUserInfoEntity.getUserinfo().getId());
            avatar = mUserInfoEntity.getUserinfo().getImg();
            username = mUserInfoEntity.getUserinfo().getName();
            if (!mUserInfoEntity.getUserinfo().getName().equals(null) && !mUserInfoEntity.getUserinfo().getName().equals("")) {
                mMineTvPhone.setText(mUserInfoEntity.getUserinfo().getName());
            } else {
                mMineTvPhone.setText(PreferenceUUID.getInstence().getUserPhone());
            }
            mMineTvMoney.setText(mUserInfoEntity.getUserinfo().getMoney() + "元");
            FrescoUtil.setHttpPic(mUserInfoEntity.getUserinfo().getImg(), mMineIvImg);
            mMineTvAuth.setText(mUserInfoEntity.getUserinfo().getMsg());
            if (mUserInfoEntity.getUserinfo().getCert_status() == 1 || mUserInfoEntity.getUserinfo().getCert_status() == 5) {
                mMineTvAuth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                mMineTvAuth.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_mer_auth_right, 0);
            }
        }
    }

    @Override
    public void updategetAvatar(ResponseData responseData) {
        if (responseData != null) {
            showToast(responseData.getMsg());
            if (responseData.getCode().equals("200")) {
                mPresenter.getMerUserinfo();
            }
        }
    }

    @Override
    public void updategetOpinion(ResponseData responseData) {

    }

    @Override
    public void updategetmdAdd(ResponseData responseData) {

    }

    @Override
    public void updategetCheck(MCheckVersionEntity mCheckVersionEntity) {

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("商户端我的页面");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MQManager.getInstance(getActivity()).closeMeiqiaService();
    }
}
