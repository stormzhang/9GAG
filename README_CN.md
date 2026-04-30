# 9GAG Android 客户端

> **⚠️ 本项目已停止维护。** 这是 2014 年的 Android 学习项目，用于展示当时的 Android 开发最佳实践。代码和依赖已过时，仅供参考。

[English](README.md)

一个非官方的 [9GAG](https://9gag.com) Android 客户端，使用 Android Studio + Gradle 构建，遵循 Material Design 设计规范。

## 截图

<img src="http://ww4.sinaimg.cn/mw1024/af63c0e3gw1eg8ahf4b1yj21kw0szqc8.jpg" width="600"/>

## 功能特性

- 9GAG 信息流浏览，卡片式布局
- 侧滑返回手势
- 图片详情页，支持缩放
- 侧边栏分类筛选
- SQLite 本地数据缓存
- Shimmer 加载动画

## 项目结构

```
app/src/main/java/me/storm/ninegag/
├── api/          # 网络请求（Volley）
├── dao/          # 数据访问层（SQLite）
├── data/         # 数据源
├── model/        # 数据模型（Feed、Category）
├── ui/           # Activity、Fragment 和 Adapter
├── util/         # 工具类（缓存、位图、模糊等）
├── view/         # 自定义 View（SwipeBack）
└── App.java      # Application 入口
```

## 技术栈

| 类别 | 库 |
|------|-----|
| 网络请求 | [Volley](https://github.com/google/volley)、[OkHttp](https://github.com/square/okhttp) |
| 图片加载 | [Glide](https://github.com/bumptech/glide) |
| 视图绑定 | [ButterKnife](https://github.com/JakeWharton/butterknife) |
| UI 组件 | [FoldableLayout](https://github.com/alexvasilkov/FoldableLayout)、[AndroidStaggeredGrid](https://github.com/etsy/AndroidStaggeredGrid)、[Shimmer](https://github.com/RomainPiel/Shimmer-android) |
| 图片查看 | [PhotoView](https://github.com/Baseflow/PhotoView) |
| 内存泄漏检测 | [LeakCanary](https://github.com/square/leakcanary) |

## 构建

```bash
git clone https://github.com/stormzhang/9GAG.git
```

用 Android Studio 打开项目并同步 Gradle。

> **注意：** 本项目使用 Gradle 2.2.1 和 Android Gradle Plugin 1.5.0，可能需要较旧版本的 Android Studio 或手动配置 Gradle 才能成功构建。

## 运行环境

- 最低支持：Android 4.0（API 14）
- 目标版本：Android 5.1（API 22）

## 作者

**stormzhang** — [@stormzhang on GitHub](https://github.com/stormzhang)

## 许可证

```
Copyright 2014 stormzhang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
