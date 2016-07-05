![Banner](https://github.com/AigeStudio/WheelPicker/blob/1.1.0/Previews/main/Banner.jpg)

***

# Function
[![Version](https://img.shields.io/badge/%20Beta-1.1.0-blue.svg)](https://github.com/AigeStudio/WheelPicker) [![API](https://img.shields.io/badge/API-1%2B-brightgreen.svg)](https://github.com/AigeStudio/WheelPicker)

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

### 1.1.0 beta

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
