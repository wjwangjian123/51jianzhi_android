package com.part.jianzhiyi.corecommon.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.view.ViewGroup;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.part.jianzhiyi.corecommon.utils.UtilManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.DrawableRes;

/**
 * Created by jyx on 2019/3/28
 *
 * @author jyx
 * @describe 图片加载工具类
 */
public class FrescoUtil {

    public static void initialize(Context context) {
        Fresco.initialize(context);
    }

    private ExecutorService executeBackgroundTask = Executors.newSingleThreadExecutor();

    public static void init(Context context) {
        Fresco.initialize(context, ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context)
                                .setBaseDirectoryName("freimages")
                                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                                .build()
                ).build());
    }

    /**
     * 加载网络图片
     */
    public static void setHttpPic(String url, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse(url);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载本地图片
     *
     * @param path
     * @param simpleDraweeView
     */
    public static void setFilePic(String path, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse("file://" + path);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载Res图片
     *
     * @param resId
     * @param simpleDraweeView
     */
    public static void setResPic(@DrawableRes int resId, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse("res:///" + resId);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载ContentProvider图片
     */
    public static void setContentProviderPic(String path, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse("content://" + path);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载Assets图片
     */
    public static void setAssetsPic(String path, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse("asset://" + path);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载GIF动态图片
     */
    public static void setGifPic(String url, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse(url);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 设置SimpleDrawViewLayout的宽高
     *
     * @param simpleDraweeViewLayout
     * @param width
     * @param height
     */
    public static void setSimpleDraweeViewLayout(SimpleDraweeView simpleDraweeViewLayout, int width, int height) {
        ViewGroup.LayoutParams layoutParams = simpleDraweeViewLayout.getLayoutParams();
        if (layoutParams == null) {
            //在没有设置宽高的条件下
            layoutParams = new ViewGroup.LayoutParams(width, height);
            simpleDraweeViewLayout.setLayoutParams(layoutParams);
        } else {
            //已设置重新获取，并赋值
            simpleDraweeViewLayout.getLayoutParams().width = width;
            simpleDraweeViewLayout.getLayoutParams().height = height;
            simpleDraweeViewLayout.requestLayout();
        }
    }

    /**
     * Fresco高斯模糊
     * iterations:迭代次数
     * blurRadius:模糊半径
     */
    public static void setSimpleDrawViewBlur(SimpleDraweeView simpleDrawViewBlur, String url, int iterations, int blurRadius) {
        Uri uri = Uri.parse(url);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(iterations, blurRadius))
                .build();
        AbstractDraweeController builder = Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDrawViewBlur.getController())
                .setImageRequest(imageRequest)
                .build();
        simpleDrawViewBlur.setController(builder);
    }

    /**
     * 加载图片成bitmap
     */
    public static void LoadToBitmap(String imageUrl, BaseBitmapDataSubscriber baseBitmapDataSubscriber) {
        Uri uri = Uri.parse(imageUrl);
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, UtilManager.getContext());
        dataSource.subscribe(baseBitmapDataSubscriber, CallerThreadExecutor.getInstance());
    }

    /**
     * 渐进式加载
     *
     * @param url
     * @param simpleDraweeView
     */
    public static void setGraduaModelSimpleDraweeView(String url, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 圆角图片
     * radius 角度
     *
     * @param url
     * @param simpleDraweeView
     * @param topLeft
     * @param topRight
     * @param bottomRight
     * @param bottomLeft
     * @param color            描边线颜色
     * @param width            描边线粗细
     */
    public static void setCirclePic(String url, SimpleDraweeView simpleDraweeView, float topLeft, float topRight, float bottomRight, float bottomLeft, int color, float width) {
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(0f);
        if (width > 0f) {
            roundingParams.setBorder(color, width);
        }
        roundingParams.setCornersRadii(topLeft, topRight, bottomRight, bottomLeft);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 圆形图片
     *
     * @param url
     * @param simpleDraweeView
     * @param color            描边线颜色
     * @param width            描边线粗细
     */
    public static void setRoundnessSimpleDraweeView(String url, SimpleDraweeView simpleDraweeView, int color, float width) {
        if (url == null) {
            simpleDraweeView.setImageURI(url);
            return;
        }
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(0f);
        if (width > 0f) {
            roundingParams.setBorder(color, width);
        }
        roundingParams.setRoundAsCircle(true);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 保存图片到sd卡并转换
     *
     * @return
     */
    public static String savePhoto(Bitmap photoBitmap, String path, String photoName) {
        String localPath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File photoFile = new File(path, photoName + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        //转换完成
                        localPath = photoFile.getPath();
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localPath;
    }

    /**
     * 转换图片成圆形
     *
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
}
