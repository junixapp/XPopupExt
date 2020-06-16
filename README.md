# XPopupExt
XPopupExt是XPopup的扩展功能库，基础XPopup强大的弹窗能力，封装的一些时间选择器弹窗。

## 演示
![](gif/preview.gif)

## 使用
- 添加依赖
```
implementation 'com.lxj:xpopup-ext:0.0.1'
//由于扩展库依赖XPopup才能正常使用，所以还必须添加XPopup依赖
implementation 'com.lxj:xpopup:2.0.2'
```

- 提供了TimerPickerPopup弹窗，使用示例:
```java
new XPopup.Builder(MainActivity.this)
        .asCustom(new TimePickerPopup(MainActivity.this)
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
        })
        )
        .show();
```
`TimerPickerPopup`还提供了一些设置方法，具体看方法说明即可。


## TODO
- 增加城市选择器