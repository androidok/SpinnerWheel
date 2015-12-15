package cn.lry.spinnerwheel;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lry.library.AbstractWheel;
import cn.lry.library.OnWheelChangedListener;
import cn.lry.library.OnWheelScrollListener;
import cn.lry.library.adapters.ArrayWheelAdapter;
import cn.lry.library.datepicker.DatePicker;
import cn.lry.library.datepicker.Sound;

/**
 * Created by liruiyuan on 2015/12/15.
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private int currItem;
    private int select;

    private  TextView text_wheel;
    private  TextView num_wheel;

    private ImageView arrow_down_one, arrow_down_two;
    private ObjectAnimator animationArrowDownOne, animationArrowDownTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        currItem = -1;
        text_wheel = (TextView) findViewById(R.id.text_wheel);
        text_wheel.setOnClickListener(this);
        num_wheel = (TextView) findViewById(R.id.num_wheel);
        num_wheel.setOnClickListener(this);

        arrow_down_one = (ImageView) findViewById(R.id.arrow_down_one);
        arrow_down_two = (ImageView) findViewById(R.id.arrow_down_two);
        animationArrowDownOne = ObjectAnimator.ofFloat(arrow_down_one, "rotation", 0, 180);
        animationArrowDownOne.setDuration(500);
        animationArrowDownTwo = ObjectAnimator.ofFloat(arrow_down_two, "rotation", 0, 180);
        animationArrowDownTwo.setDuration(500);
    }



    /**
     * Initializes spinnerwheel
     */
    private void initWheel(View root, String[] types, int currItem) {
        if (-1 == currItem)
            currItem = 0;
        AbstractWheel wheel = (AbstractWheel) root.findViewById(R.id.wheel);
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(this, types);
        adapter.setTextSize(20);
        adapter.setTextColor(getResources().getColor(R.color.colorAccent));
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem(currItem);
        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setVisibleItems(3);
        wheel.setCyclic(false);
        wheel.setInterpolator(new AnticipateOvershootInterpolator());
        select = currItem;
    }

    // Wheel scrolled listener
    private OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(AbstractWheel wheel) {
        }
        public void onScrollingFinished(AbstractWheel wheel) {
        }
    };

    // Wheel changed listener
    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
            select = newValue;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_wheel:
                final String[] items = new String[]{"测试数据1","测试数据2","测试数据3","测试数据4","测试数据5"};
                View view = View.inflate(this, R.layout.layout_actionsheet_common, null);
                final Dialog dialog = UIManager.getActionSpSheet(this, view, Gravity.BOTTOM);
                view.findViewById(R.id.action_sheet_done).setOnClickListener(new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text_wheel.setText(items[select]);
                        dialog.dismiss();
                    }
                });
                initWheel(view, items, select);
                dialog.show();
                animationArrowDownOne.start();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        animationArrowDownOne.reverse();
                    }
                });
                break;
            case R.id.num_wheel:
                Sound sound = new Sound(this);
                view = View.inflate(this, R.layout.layout_actionsheet_datepicker, null);
                final Dialog dateDialog = UIManager.getActionSpSheet(this, view, Gravity.BOTTOM);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datepicker);
                datePicker.setSoundEffect(sound)
                        .setTextColor(Color.RED)
                        .setFlagTextColor(Color.RED)
                        .setSoundEffectsEnabled(true);
                view.findViewById(R.id.action_sheet_done).setOnClickListener(new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        num_wheel.setText(datePicker.getYear() + "-" + datePicker.getMonth() +
                                "-" + datePicker.getDayOfMonth());
                        dateDialog.dismiss();
                    }
                });
                dateDialog.show();
                animationArrowDownTwo.start();
                dateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        animationArrowDownTwo.reverse();
                    }
                });
                break;
        }
    }

}
