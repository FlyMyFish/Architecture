package com.shichen.architecture.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shichen.architecture.R;


/**
 * @author shichen 754314442@qq.com
 *         加载框
 *         Created by Administrator on 2018/9/28.
 */
public class LoadingDialog extends DialogFragment {
    private static LoadingDialog mInstance = null;

    public static LoadingDialog getInstance() {
        if (mInstance == null) {
            synchronized (LoadingDialog.class) {
                if (mInstance == null) {
                    mInstance = new LoadingDialog();
                }
            }
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        ImageView ivLoading = view.findViewById(R.id.iv_loading);
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(getContext()).load(R.drawable.loading).apply(options).into(ivLoading);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.gravity = Gravity.CENTER;
        windowParams.dimAmount = 0.0f;
        window.setAttributes(windowParams);
    }
}
