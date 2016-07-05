![Banner](https://github.com/AigeStudio/WheelPicker/blob/1.1.0/Previews/main/Banner.jpg)

***

# Overview
[![Version](https://img.shields.io/badge/%20Stable-1.1.0-blue.svg)](https://github.com/AigeStudio/WheelPicker) [![API](https://img.shields.io/badge/API-1%2B-brightgreen.svg)](https://github.com/AigeStudio/WheelPicker)

# Contact
[![QQ](https://img.shields.io/badge/QQ-1994099479-red.svg)](http://sighttp.qq.com/authd?IDKEY=404d62c783d5c76e312f4c9fa65819d75ce648bff94b8cd6) [![QQGroup](https://img.shields.io/badge/QQ%E7%BE%A4-361739851-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=a62502df9a8f2f24f20f978070a9c93238c1fe91db8888dda78214cb83dc6002) [![Mail](https://img.shields.io/badge/mail-aigestudio%40qq.com-orange.svg)](http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=i_ri7O74--7v4uTL_vql6OTm)

# Include
### Compile
```Gradle
compile "cn.aigestudio.wheelpicker:WheelPicker:1.0.3"
```

### Import aar
[WheelPicker-1.0.3.aar](https://bintray.com/artifact/download/aigestudio/maven/cn/aigestudio/wheelpicker/WheelPicker/1.0.3/WheelPicker-1.0.3.aar)

### Import Module
1.Import moudle WheelPicker in your project.

2.Add module like below in your settings.gradle file of project:
```gradle
include ':YourMoudle',':WheelPicker'
```

Notably, in some version of gradle you need to add module with comma-separated:
```gradle
include ':WheelPicker'
```

click the "sycn now" when it appear on the top-right of IDE window.

3.Compile project like below in the dependencies in your build.gradle file of application module:
```gradle
compile project(':WheelPicker')
```

# Usage
[WIKI](https://github.com/AigeStudio/WheelPicker/wiki) | [帮助文档](https://github.com/AigeStudio/WheelPicker/wiki)

# Versions
### 1.0.0 beta
* Preview for WheelPicker and support few function.

### 1.0.1 beta
* BugFix:Cache in WheelCuredPicker can not clean...a stupid mistake.

### 1.0.2 beta
* BugFix:MotionEvent lost when point outside of view in WheelCurvedPicker

### 1.0.3 beta
* BugFix:Error parameter after scroll using setXXX method

### 1.1.0 Stable
* Refactor project fix all known bug and some function additions
* I will create a tag for old beta version but not be updated anymore
* 重构项目发布稳定版修复测试版所有BUG以及新增功能
* Beta版本会打上TAG方便老版本过渡但不再更新

# Function
* Data circulation display
* Set visible item count
* Get the current item data straight in stationary
* Monitor status of scroll get selected item data and other parameter when wheel stop
* Dynamic update data
* Set text color of selected or non-selected item
* Set item space
* Support display indicator and set the indicator's size and color
* Support display curtain and set the curtain's color
* Enable atmospheric effect
* Enable perspective effect
* Curl the items base on mathematic models
* Support item align when perspective or atmospheric enable
* 循环显示数据项
* 设置可见数据项条数
* 在滚轮静止状态直接获取选中数据项
* 滚动监听获取滚轮停止后选中项以及滚动各项参数
* 动态更新数据源
* 设置当前选中项文本颜色和非选中项文本颜色
* 设置数据项之间间隔
* 支持绘制指示器以及指定指示器颜色、尺寸
* 支持绘制幕布以及指定幕布颜色
* 可开启数据项空气感模拟
* 可开启数据项模拟真实透视效果
* 根据严格数学建模模拟滚轮弯曲效果
* 在开启透视或弯曲效果后支持让数据项左右对齐

# Preview
![](https://github.com/AigeStudio/WheelPicker/blob/master/Previews/DemoPreview.png)
### Views
**In Straight Style**

![](https://github.com/AigeStudio/WheelPicker/blob/master/Previews/StraightStyle.gif)

**In Curved Style**

![](https://github.com/AigeStudio/WheelPicker/blob/master/Previews/CurvedStyle.gif)

**In Horizontal Orientation** 

![](https://github.com/AigeStudio/WheelPicker/blob/master/Previews/WheelPickerHor.gif)

### Widgets
**WheelDatePicker**

![](https://github.com/AigeStudio/WheelPicker/blob/master/Previews/WheelDatePicker.gif)

**WheelTimePicker**

![](https://github.com/AigeStudio/WheelPicker/blob/master/Previews/WheelTimePicker.gif)

***

# LICENSE
Copyright 2015-2016 [AigeStudio](https://github.com/AigeStudio)

Licensed under the Apache License, Version 2.0 (the "License");you may not use this file except in compliance with the License.

You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
