<p align="center">
  <img src="https://i.imgur.com/w0GCwPS.png" width="300" height="500" alt="MyScheduler Mockup Image"/>
</p>


## [!] Notice: If you want to contribute, please read below. As of now, anyone is free to open any issue. 👍
# 📱 MyScheduler

**MyScheduler** is a sleek, Kotlin-based Android app designed to enhance university life. It fetches real-time academic information like schedules, campus room locations, news, and more — all powered by a robust FastAPI backend.

---

## ✨ Features

- 📅 **Current Schedule**  
  Instantly access your university schedule using your group number.

- 📰 **News Feed**  
  Stay informed with the latest university updates.

- 🏫 **Room Locator**  
  Search for room names and navigate directly using Google Maps.

- 📊 **Grades** (coming soon!)  
  Track academic performance — currently in development.

- 🔒 **Privacy Policy**  
  Built with user privacy in mind.

---

## 🔌 API Integration

This app communicates with a FastAPI-based backend. Here are some of the main endpoints used:

| Endpoint            | Purpose                |
|---------------------|------------------------|
| `/rescan`           | Rescan the schedule    |
| `/orar/{grupa}`     | Fetch schedule by group |
| `/news`             | Get university news    |
| `/rooms`            | Get room information   |
| `/`                 | General info/home      |

---

## 🛠️ Tech Stack

### 📱 Android App
- **Language:** Kotlin
- **IDE:** Android Studio (Latest version)
- **UI:** Material Design 3 + ConstraintLayout
- **Networking:** OkHttp + Gson
- **Architecture:** MVVM + Jetpack Components
- **Image Loading:** Glide
- **Background Work:** WorkManager

### 🧰 Dependencies

```toml
# Versions
kotlin = "2.0.21"
appcompat = "1.7.0"
navigation = "2.8.9"
glide = "4.12.0"
workmanager = "2.10.0"
```

## 🚀 Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
```


## 🧭 Getting Started
### 🔨 Prerequisites
- Android Studio Meerkat
- Android SDK 33+
- Kotlin 2.0.21

## 📦 Installation
Clone the repo:
```bash
git clone https://github.com/lates-codrin/MyScheduler.git
```
Open in Android Studio

Sync Gradle and run the app!

# 🧑‍💻 Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you’d like to change.
