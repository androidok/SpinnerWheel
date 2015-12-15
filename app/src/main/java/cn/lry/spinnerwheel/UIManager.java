package cn.lry.spinnerwheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by liruiyuan on 2015/12/15.
 */
public class UIManager {

    /**
     * action sheet dialog
     *
     * @param context
     * @param view
     * @param gravity
     * @return
     */
    public static Dialog getActionSpSheet(Context context, View view, int gravity) {
        final Dialog dialog = new Dialog(context, R.style.action_sheet);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int screen[] = getScreenSize(context);
        lp.width = screen[0];
        lp.height = screen[1]/3;
        window.setGravity(gravity); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.action_sheet_animation); // 添加动画
        return dialog;
    }

    public static int[] getScreenSize(Context context) {
        int[] screenSize = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenSize[0] = dm.widthPixels;
        screenSize[1] = dm.heightPixels;
        return screenSize;
    }
}
