package com.lxg.work.retrofit.re.util;

import androidx.fragment.app.FragmentManager;
import android.view.View;

import com.lxg.work.retrofit.re.widget.MyDialogFragment;

/**
 * Created by Lxg on 2018/6/12 0012.
 */
public class DialogFragmentUtil {

    private static MyDialogFragment myDialogFragment;

    public static void getInstance() {
        myDialogFragment = new MyDialogFragment();
    }

    public MyDialogFragment show(FragmentManager fragmentManager, String tag, String title, String content, View.OnClickListener click_yes, View.OnClickListener click_no) {
        myDialogFragment.getDialog().setCanceledOnTouchOutside(true);
//        myDialogFragment.setinfo(title, content, click_yes, click_no);
        myDialogFragment.show(fragmentManager, tag);
        return myDialogFragment;
    }

    /*public MyDialogFragment setDialogInfo(String title, String content, View.OnClickListener click_yes, View.OnClickListener click_no) {
        return myDialogFragment;
    }*/
}
