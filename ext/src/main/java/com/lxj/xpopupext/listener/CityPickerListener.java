package com.lxj.xpopupext.listener;

import android.view.View;

/**
 * Created by xiaosong on 2018/3/20.
 */

public interface CityPickerListener {

    void onCityConfirm(String options1, String options2, String options3, View v);
    void onCityChange(String options1, String options2, String options3);

}
