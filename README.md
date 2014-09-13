9GAG-Android (unofficial)
=====================

## Android学习之路

推荐大家一篇博客--[Android学习之路](http://stormzhang.github.io/android/2014/07/07/learn-android-from-rookie/)

## About

遵循Android Design的9GAG客户端，练习之作，主要使用Android方面最新的IDE，新的技术、新特性，以及一些流行的开源库快速开发一个不错的REST Client，教你如何Make A Good App! 

## 安装

apk文件夹下附带安装包，当然你也可以扫描二维码下载，运行环境4.0+

![9GAG](https://raw.github.com/stormzhang/9GAG/master/art/9gag_qrcode.png)

## 导入运行

如果你是直接导入Studio，则应该不会有问题，只需要检查你本地的gradle版本即可。如果你要导入eclipse，则ButterKnife可能会不起作用，需要参考[这里](http://jakewharton.github.io/butterknife/ide-eclipse.html)做一些配置。

有些library是通过gradle直接引用的，具体参见app/build.gradle, 目前通过gradle引用的有support-v4、AndroidStaggeredGrid，没有使用gradle的请手动去对应的官方地址下载并引用。

## 预览

![9GAG](https://raw.github.com/stormzhang/9GAG/master/art/9gag_v1.2.png)

## 开发工具

Android Studio(0.8.9) + Gradle(1.12)

作为一名潮人，怎能不使用最新的IDE来开发呢？

支持命令行进行Gradle编译:（不知道什么是Gradle？见这篇博客[Android Gradle](http://stormzhang.github.io/android/2014/02/28/android-gradle/)）

    gradle clean
    gradle build

## 用到的开源库

目前用到的一些开源库，会持续更新。

#### v1.3

大神RomainPiel的两个动画库

* [Shimmer-android](https://github.com/RomainPiel/Shimmer-android)

* [Titanic](https://github.com/RomainPiel/Titanic)

#### v1.2

* [AndroidStaggeredGrid](https://github.com/etsy/AndroidStaggeredGrid) Etsy公司开源的瀑布流组件，应该算是目前瀑布流最好的开源库了，但仍然有很多需要改进的地方。

* [BlurEffectForAndroidDesign](https://github.com/PomepuyN/BlurEffectForAndroidDesign) 高斯模糊（即毛玻璃）效果，让你的App在细节取胜吧！

#### v1.1

* [SwipeBackLayout](https://github.com/Issacw0ng/SwipeBackLayout) 你的App还没有支持手势滑动返回么？那就有点Out了。

#### v1.0

* [Volley](https://android.googlesource.com/platform/frameworks/volley) Google I/O 2013推出的官方网络请求库，请求快速，使用方便，易于扩展，而且支持图片异步加载。

* [SwipeRefreshLayout](http://stormzhang.github.io/android/2014/03/29/android-swiperefreshlayout/) Google最近推出的下拉刷新组件，原来下拉刷新可以如此简单，如此酷炫！

* [ButterKnife](http://jakewharton.github.io/butterknife/) 是不是很烦各种findViewById, 那么这个库正是你需要的，轻量级View注入框架，从此你的代码不再那么臃肿。

* [UniversalImageLoader](https://github.com/nostra13/Android-Universal-Image-Loader) 目前最流行的图片异步加载库，配置强大，使用简单，绝对能满足你的各种需求！

* [ListViewAnimations](https://github.com/nhaarman/ListViewAnimations) 各种ListView加载动画，从此让你的ListView不再那么单调！

* [NineOldAndroid](http://nineoldandroids.com/) 兼容2.x版本的动画库，如果你是为4.0+平台开发的app，说明你或者你所在公司比较潮，请忽略之！

* [PhotoView](https://github.com/chrisbanes/PhotoView) 图片手势缩放库，很好用，也一直在用！

* [FoldingLayout](https://github.com/tibi1712/Folding-Android) 很轻易的让你的布局实现折叠效果，很Cool！

* [ProgressWheel](https://github.com/Todd-Davies/ProgressWheel) 自定义各种进度条，在[CustomLoading](https://github.com/stormzhang/CustomLoading)项目中也有收录!

## Pull Requests

I will gladly accept pull requests for fixes and feature enhancements but please do them in the develop branch.

## 感谢

此项目在代码结构以及Sqlite的管理上很大部分借鉴了碎总的[Dribble](https://github.com/Issacw0ng/Dribbo)项目

## 关于我

* 新浪微博: [googdev](http://weibo.com/zhangqi8)

分享Android方面的知识、技术、见闻

* [更多信息](http://stormzhang.github.io/about.html)

License
============

    Copyright 2014 Storm Zhang

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.