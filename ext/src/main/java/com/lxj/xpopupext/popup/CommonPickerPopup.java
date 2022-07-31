package com.lxj.xpopupext.popup;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopupext.R;
import com.lxj.xpopupext.adapter.ArrayWheelAdapter;
import com.lxj.xpopupext.listener.CommonPickerListener;
import com.lxj.xpopupext.listener.ISelectTimeCallback;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.view.WheelTime;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CommonPickerPopup extends BottomPopupView {

    private int itemsVisibleCount = 7;
    private int itemTextSize = 16;
    public int dividerColor = 0xFFd5d5d5; //分割线的颜色
    public float lineSpace = 2.8f; // 条目间距倍数 默认2
    public int textColorOut = 0xFFa8a8a8; //分割线以外的文字颜色
    public int textColorCenter = 0xFF2a2a2a; //分割线之间的文字颜色
    private WheelView wheelView;
    public CommonPickerPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout._xpopup_ext_common_picker;
    }

    TextView btnCancel, btnConfirm;
    @Override
    protected void onCreate() {
        super.onCreate();
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);
        wheelView = findViewById(R.id.commonWheel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(commonPickerListener!=null) commonPickerListener.onCancel();
                dismiss();
            }
        });
        btnConfirm.setTextColor(XPopup.getPrimaryColor());
        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = wheelView.getCurrentItem();
                if(commonPickerListener!=null) commonPickerListener.onItemSelected(index, list.get(index));
                dismiss();
            }
        });
        initWheelData();
    }

    private void initWheelData() {
        wheelView.setItemsVisibleCount(itemsVisibleCount);
        wheelView.setAlphaGradient(true);
        wheelView.setTextSize(itemTextSize);
        wheelView.setCyclic(false);
        wheelView.setDividerColor( popupInfo.isDarkTheme ? Color.parseColor("#444444") : dividerColor);
        wheelView.setDividerType(WheelView.DividerType.FILL);
        wheelView.setLineSpacingMultiplier(lineSpace);
        wheelView.setTextColorOut(textColorOut);
        wheelView.setTextColorCenter(popupInfo.isDarkTheme ? Color.parseColor("#CCCCCC") : textColorCenter);
        wheelView.isCenterLabel(false);
        wheelView.setCurrentItem(currentItem);
        wheelView.setAdapter(new ArrayWheelAdapter<>(list));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
            }
        });
        if(popupInfo.isDarkTheme){
            applyDarkTheme();
        }else {
            applyLightTheme();
        }
    }
    @Override
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        btnCancel.setTextColor(Color.parseColor("#999999"));
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
        getPopupImplView().setBackground(XPopupUtils.createDrawable(getResources().getColor(R.color._xpopup_dark_color),
                popupInfo.borderRadius, popupInfo.borderRadius, 0,0));
    }

    @Override
    protected void applyLightTheme() {
        super.applyLightTheme();
        btnCancel.setTextColor(Color.parseColor("#666666"));
        btnConfirm.setTextColor(Color.parseColor("#222222"));
        getPopupImplView().setBackground(XPopupUtils.createDrawable(getResources().getColor(R.color._xpopup_light_color),
                popupInfo.borderRadius, popupInfo.borderRadius, 0,0));
    }
    private CommonPickerListener commonPickerListener;
    public CommonPickerPopup setCommonPickerListener(CommonPickerListener commonPickerListener){
        this.commonPickerListener = commonPickerListener;
        return this;
    }
    public CommonPickerPopup setItemTextSize(int textSize){
        this.itemTextSize = textSize;
        return this;
    }

    public CommonPickerPopup setItemsVisibleCount(int itemsVisibleCount){
        this.itemsVisibleCount = itemsVisibleCount;
        return this;
    }
    public CommonPickerPopup setLineSpace(float lineSpace){
        this.lineSpace = lineSpace;
        return this;
    }
    List<String> list = new ArrayList<>();
    /**
     * 设置选项数据
     */
    public CommonPickerPopup setPickerData(List<String> list) {
        this.list = list;
        return this;
    }

    int currentItem = 0;
    /**
     * 设置默认选中
     */
    public CommonPickerPopup setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
        return this;
    }


}
