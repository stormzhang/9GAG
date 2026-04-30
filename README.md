# 9GAG for Android

> **⚠️ This project is no longer maintained.** It was built in 2014 as a learning project to demonstrate Android development best practices of that era. The code and dependencies are outdated, but the project remains available for reference.

[中文版](README_CN.md)

An unofficial Android client for [9GAG](https://9gag.com), built with Android Studio + Gradle, following Material Design guidelines.

## Screenshots

<img src="http://ww4.sinaimg.cn/mw1024/af63c0e3gw1eg8ahf4b1yj21kw0szqc8.jpg" width="600"/>

## Features

- 9GAG feeds browsing with card-style layout
- Swipe-back gesture navigation
- Image detail view with zoom support
- Navigation drawer with category filtering
- Local data caching with SQLite
- Shimmer loading animation

## Project Structure

```
app/src/main/java/me/storm/ninegag/
├── api/          # Network requests (Volley)
├── dao/          # Data access layer (SQLite)
├── data/         # Data sources
├── model/        # Data models (Feed, Category)
├── ui/           # Activities, Fragments & Adapters
├── util/         # Utilities (cache, bitmap, blur, etc.)
├── view/         # Custom views (SwipeBack)
└── App.java      # Application entry
```

## Tech Stack

| Category | Library |
|----------|---------|
| Network | [Volley](https://github.com/google/volley), [OkHttp](https://github.com/square/okhttp) |
| Image Loading | [Glide](https://github.com/bumptech/glide) |
| View Binding | [ButterKnife](https://github.com/JakeWharton/butterknife) |
| UI Components | [FoldableLayout](https://github.com/alexvasilkov/FoldableLayout), [AndroidStaggeredGrid](https://github.com/etsy/AndroidStaggeredGrid), [Shimmer](https://github.com/RomainPiel/Shimmer-android) |
| Image Viewer | [PhotoView](https://github.com/Baseflow/PhotoView) |
| Memory Leak Detection | [LeakCanary](https://github.com/square/leakcanary) |

## Build

```bash
git clone https://github.com/stormzhang/9GAG.git
```

Open the project in Android Studio and sync Gradle.

> **Note:** This project uses Gradle 2.2.1 and Android Gradle Plugin 1.5.0. You may need an older version of Android Studio or manual Gradle configuration to build successfully.

## Requirements

- Min SDK: Android 4.0 (API 14)
- Target SDK: Android 5.1 (API 22)

## Author

**stormzhang** — [@stormzhang on GitHub](https://github.com/stormzhang)

## License

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
