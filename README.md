# XPopupExt
XPopupExt是XPopup的扩展功能库，由于对PickerView自身的UI和交互不满意，就结合XPopup强大的弹窗能力和PickerView的选择器逻辑，封装的了新的选择器弹窗和城市选择器弹窗。

## 演示
![](gif/preview.gif)

## 使用
![Download](https://api.bintray.com/packages/li-xiaojun/jrepo/xpopup-ext/images/download.svg)
- 添加依赖
```
implementation 'com.lxj:xpopup-ext:最新版本'
//由于扩展库依赖XPopup才能正常使用，所以还必须添加XPopup依赖
implementation 'com.lxj:xpopup:2.0.2'
```

- 时间选择器TimerPickerPopup弹窗，使用示例:
```java
Calendar date = Calendar.getInstance();
date.set(2000, 5,1);
Calendar date2 = Calendar.getInstance();
date2.set(2020, 5,1);
TimePickerPopup popup = new TimePickerPopup(MainActivity.this)
//                        .setDefaultDate(date)  //设置默认选中日期
//                        .setYearRange(1990, 1999) //设置年份范围
//                        .setDateRang(date, date2) //设置日期范围
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
        .asCustom(popup)
        .show();
```
`TimerPickerPopup`还提供了一些设置方法，具体看方法说明即可。

- 城市选择器，CityPickerPopup弹窗使用示例：
```java
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
        .asCustom(popup)
        .show();
```
