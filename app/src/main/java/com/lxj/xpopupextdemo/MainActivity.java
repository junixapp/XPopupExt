package com.lxj.xpopupextdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopupext.listener.CityPickerListener;
import com.lxj.xpopupext.listener.CommonPickerListener;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.popup.CityPickerPopup;
import com.lxj.xpopupext.popup.CommonPickerPopup;
import com.lxj.xpopupext.popup.TimePickerPopup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XPopup.setPrimaryColor(getResources().getColor(R.color.colorPrimary));
        findViewById(R.id.btnTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar date = Calendar.getInstance();
                date.set(2000, 5,1);
                Calendar date2 = Calendar.getInstance();
                date2.set(2020, 5,1);
                TimePickerPopup popup = new TimePickerPopup(MainActivity.this)
//                        .setMode(TimePickerPopup.Mode.YMDHMS)
                        .setDefaultDate(date)  //设置默认选中日期
//                        .setYearRange(1990, 1999) //设置年份范围
                        .setDateRange(date, date2) //设置日期范围
                        .setTimePickerListener(new TimePickerListener() {
                            @Override
                            public void onTimeChanged(Date date) {
                                //时间改变
                            }
                            @Override
                            public void onTimeConfirm(Date date, View view) {
                                //点击确认时间
                                Toast.makeText(MainActivity.this, "选择的时间："+date.toLocaleString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                new XPopup.Builder(MainActivity.this)
                        .borderRadius(30)
//                        .isDarkTheme(true)
                        .asCustom(popup)
                        .show();
            }
        });

        findViewById(R.id.btnCity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPickerPopup popup = new CityPickerPopup(MainActivity.this);
                popup.setCityPickerListener(new CityPickerListener() {
                    @Override
                    public void onCityConfirm(String province, String city, String area, View v) {
                        Log.e("tag", province +" - " +city+" - " +area);
                        Toast.makeText(MainActivity.this, province +" - " +city+" - " +area, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCityChange(String province, String city, String area) {
                        Log.e("tag", province +" - " +city+" - " +area);
                        Toast.makeText(MainActivity.this, province +" - " +city+" - " +area, Toast.LENGTH_SHORT).show();
                    }
                });
                new XPopup.Builder(MainActivity.this)
                        .borderRadius(30)
//                        .isDarkTheme(true)
                        .asCustom(popup)
                        .show();
            }
        });

        findViewById(R.id.btnCommon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonPickerPopup popup = new CommonPickerPopup(MainActivity.this);
                ArrayList<String> list = new ArrayList<String>();
                list.add("小猫");
                list.add("小狗");
                list.add("小羊");
                popup.setPickerData(list)
                        .setCurrentItem(1);
                popup.setCommonPickerListener(new CommonPickerListener() {
                    @Override
                    public void onItemSelected(int index, String data) {
                        Toast.makeText(MainActivity.this, "选中的是 "+ data, Toast.LENGTH_SHORT).show();
                    }
                });
                new XPopup.Builder(MainActivity.this)
                        .borderRadius(30)
                        .isDarkTheme(true)
                        .asCustom(popup)
                        .show();
            }
        });
    }
}