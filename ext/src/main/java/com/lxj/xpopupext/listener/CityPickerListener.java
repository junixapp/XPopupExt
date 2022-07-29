package com.lxj.xpopupext.listener;

import android.view.View;

/**
 * Created by xiaosong on 2018/3/20.
 */

public interface CityPickerListener {

    void onCityConfirm(String province, String city, String area, View v);
    void onCityChange(String province, String city, String area);
    void onCancel();

}
