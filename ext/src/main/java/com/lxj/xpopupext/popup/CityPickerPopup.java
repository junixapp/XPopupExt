package com.lxj.xpopupext.popup;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.contrarywind.view.WheelView;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopupext.R;
import com.lxj.xpopupext.bean.JsonBean;
import com.lxj.xpopupext.listener.CityPickerListener;
import com.lxj.xpopupext.listener.OnOptionsSelectListener;
import com.lxj.xpopupext.view.WheelOptions;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CityPickerPopup extends BottomPopupView {

    private List<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private CityPickerListener cityPickerListener;
    private WheelOptions wheelOptions;
    public int dividerColor = 0xFFd5d5d5; //分割线的颜色
    public float lineSpace = 2.4f; // 条目间距倍数 默认2
    public int textColorOut = 0xFFa8a8a8; //分割线以外的文字颜色
    public int textColorCenter = 0xFF2a2a2a; //分割线之间的文字颜色
    public CityPickerPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout._xpopup_ext_city_picker;
    }

    TextView btnCancel, btnConfirm;
    @Override
    protected void onCreate() {
        super.onCreate();
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnConfirm.setTextColor(XPopup.getPrimaryColor());
        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityPickerListener != null) {
                    int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                    int options1 = optionsCurrentItems[0];
                    int options2 = optionsCurrentItems[1];
                    int options3 = optionsCurrentItems[2];
                    cityPickerListener.onCityConfirm(options1Items.get(options1),
                            options2Items.get(options1).get(options2),
                            options3Items.get(options1).get(options2).get(options3), v);
                }
                dismiss();
            }
        });

        wheelOptions = new WheelOptions(findViewById(R.id.citypicker),false);
        if (cityPickerListener != null) {
            wheelOptions.setOptionsSelectChangeListener(new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3) {
                    if(options1>=options1Items.size())return;
                    if(options1>=options2Items.size() || options2>=options2Items.get(options1).size())return;
                    if(options1>=options3Items.size() || options2>=options3Items.get(options1).size()
                    || options3>=options3Items.get(options1).get(options2).size())return;
                    String province = options1Items.get(options1);
                    String city = options2Items.get(options1).get(options2);
                    String area = options3Items.get(options1).get(options2).get(options3);
                    cityPickerListener.onCityChange(province,city, area);
                }
            });
        }
        wheelOptions.setTextContentSize(18);
        wheelOptions.setItemsVisible(7);
        wheelOptions.setAlphaGradient(true);
        wheelOptions.setCyclic(false);

        wheelOptions.setDividerColor( popupInfo.isDarkTheme ? Color.parseColor("#444444") : dividerColor);
        wheelOptions.setDividerType(WheelView.DividerType.FILL);
        wheelOptions.setLineSpacingMultiplier(lineSpace);
        wheelOptions.setTextColorOut(textColorOut);
        wheelOptions.setTextColorCenter(popupInfo.isDarkTheme ? Color.parseColor("#CCCCCC") : textColorCenter);
        wheelOptions.isCenterLabel(false);

        if(!options1Items.isEmpty() && !options2Items.isEmpty() && !options3Items.isEmpty()){
            //有数据直接显示
            if(wheelOptions!=null){
                wheelOptions.setPicker(options1Items, options2Items, options3Items);
                wheelOptions.setCurrentItems(0, 0, 0);
            }
        }else {
            initJsonData();
        }
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

    public CityPickerPopup setCityPickerListener(CityPickerListener listener){
        this.cityPickerListener = listener;
        return this;
    }

    private void initJsonData() {//解析数据
        String JsonData = readJson(getContext(), "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
//        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            options1Items.add(jsonBean.get(i).getName());
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        wheelOptions.setPicker(options1Items, options2Items, options3Items);
        wheelOptions.setCurrentItems(0, 0, 0);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    public String readJson(Context context,String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
