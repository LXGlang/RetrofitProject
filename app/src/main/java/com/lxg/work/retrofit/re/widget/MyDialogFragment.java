package com.lxg.work.retrofit.re.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lxg.work.retrofit.R;
import com.lxg.work.retrofit.re.util.LogUtils;


/**
 * Created by Lxg on 2018/6/11 0011.
 */
public class MyDialogFragment extends DialogFragment {

    private TextView tv_title;
    private TextView tv_content;
    private Button bt_yes;
    private Button bt_no;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0;
        lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(lp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_mydialogfragment, container, false);
        tv_title = view.findViewById(R.id.tv_title);
        tv_content = view.findViewById(R.id.tv_content);
        bt_yes = view.findViewById(R.id.bt_yes);
        bt_no = view.findViewById(R.id.bt_no);
        return view;
    }

    /**
     * 设置所展示的信息
     *
     * @param title
     * @param content
     * @param click_yes
     * @param click_no
     * @return
     */
    public MyDialogFragment setinfo(String title, String content, View.OnClickListener click_yes, View.OnClickListener click_no) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            tv_content.setText(content);
        }
        if (click_yes != null) {
            bt_yes.setOnClickListener(click_yes);
        }
        if (click_no != null) {
            bt_no.setOnClickListener(click_no);
        }
        return this;
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        getDialog().setCanceledOnTouchOutside(true);

        return super.show(transaction, tag);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        LogUtils.e("DialogFragment关闭了!" + getTag());
        super.onDismiss(dialog);
    }

}
