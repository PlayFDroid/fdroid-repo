# 🛠️ PlayFDroid Revival Toolkit

Collection of shared templates, compatibility overlays, and CI/CD pipelines to modernize open-source Android games and revive classic Android 1.x–4.x games for modern 64-bit devices (Android 14, 15, 16+ on ARMv9).

---

## 📦 What's Inside

### 1. `overlay/`
- **`LegacyMenuOverlay.java`**: Injects a lightweight floating semi-transparent `[≡]` MENU button for games built for hardware MENU keys (HTC Dream, Nexus One) without modifying XML layouts.
- **`FrameLimiter.java`**: 60 FPS cap helper for games whose physics loops were hardcoded to 60Hz displays, preventing double-speed gameplay on 90Hz/120Hz/144Hz displays.

### 2. `templates/`
- **`app-build.gradle.template`**: Golden SDK configuration (`compileSdk 35` + `targetSdk 24`), legacy Ant/Eclipse source sets mapping, Apache HTTP legacy library flag, and 16 KB page-aligned NDK build templates.
- **`root-build.gradle.template`**, **`settings.gradle.template`**, **`gradle.properties.template`**, **`gradle-wrapper.properties.template`**: Modern Gradle 8.x + AGP 8.x bootstrap files.

### 3. `ci/`
- **`build-release.yml`**: Full CI/CD workflow with hardware-accelerated KVM Android 14 smoke testing, automated APK signing, GitHub Release creation, and F-Droid repository webhook dispatch.

---

## 🎯 The Golden SDK Strategy

- `compileSdk 35`: Enables compiling against the latest toolchains and APIs.
- `minSdk 16`: Compatible with older Android versions.
- `targetSdk 24`: Bypasses Android 14/15/16/17 installation blocking (`INSTALL_FAILED_DEPRECATED_SDK_VERSION`), while instructing modern Android to run in backwards compatibility mode (no forced Scoped Storage or edge-to-edge UI regressions).

---

## 📜 Porting Playbook

1. Fork/clone the legacy game repo.
2. Copy Gradle templates from `templates/` and update `namespace` and `applicationId` (append `.revived`).
3. Replace deprecated `android.util.FloatMath` with standard `(float) Math.*`.
4. Ensure `android:exported="true"` is declared on all activities with `<intent-filter>`.
5. If the game needs a hardware MENU key, call `LegacyMenuOverlay.inject(this)` in the main activity.
6. Copy `ci/build-release.yml` to `.github/workflows/build-release.yml`.
7. Push tag `v1.0.0` to trigger build, smoke-test, release, and automated F-Droid indexing!
