package com.lxg.work.retrofit.re.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lxg.work.retrofit.R;
import com.lxg.work.retrofit.re.util.LogUtils;
import com.lxg.work.retrofit.re.util.LoginInput;


/**
 * Created by Lxg on 2018/6/11 0011.
 */
public class MyDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_content;
    private Button bt_yes;
    private Button bt_no;
    private EditText ed_username;
    private EditText ed_password;
    private String title;
    private String content;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_mydialogfragment, container, false);
        tv_title = view.findViewById(R.id.tv_title);
        tv_content = view.findViewById(R.id.tv_content);
        bt_yes = view.findViewById(R.id.bt_yes);
        bt_no = view.findViewById(R.id.bt_no);
        ed_username = view.findViewById(R.id.ed_username);
        ed_password = view.findViewById(R.id.ed_password);
        bt_no.setOnClickListener(this);
        bt_yes.setOnClickListener(this);
        return view;
    }

    public void show(FragmentManager manager, String tag, String title, String content) {
//
        this.title = title;
        this.content = content;
        show(manager, tag);
        LogUtils.e("show方法执行"+"赋值信息为:title:"+this.title+"        content:"+this.content);
    }

    @Override
    public void onStart() {

        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0;
        lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(lp);
//        getDialog().setCanceledOnTouchOutside(true);
/*        tv_title.setText(title);
        tv_content.setText(content);*/
        LogUtils.e("onstart方法执行"+"赋值信息为:title:"+title+"        content:"+content);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        LogUtils.e("DialogFragment关闭了!" + getTag());
        super.onDismiss(dialog);
    }

    @Override
    public Dialog getDialog() {
        return super.getDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_yes:
                LoginInput loginInput = (LoginInput) getActivity();
                loginInput.userLogin(ed_username.getText().toString(), ed_password.getText().toString());
                break;
            case R.id.bt_no:
                MyDialogFragment.this.dismiss();
                break;
        }
    }
}
