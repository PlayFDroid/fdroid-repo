# 🎮 PlayFDroid F-Droid Repository

[![Update F-Droid Repository](https://github.com/PlayFDroid/fdroid-repo/actions/workflows/update-fdroid.yml/badge.svg)](https://github.com/PlayFDroid/fdroid-repo/actions/workflows/update-fdroid.yml)
[![License](https://img.shields.io/badge/License-GPL%20v3%2FMIT%2FApache-blue.svg)](LICENSE)

An automated, decentralized, 100% GitHub-hosted F-Droid repository dedicated to **open-source Android games and classic retro revivals**, updated to run smoothly on modern Android versions (Android 14, 15, 16, 17+ on 64-bit ARMv9 architecture).

---

## 📱 How to Add to F-Droid Client

### Method 1: Scan QR Code (Fastest)
Scan this QR code using the F-Droid app or your phone camera:

<p align="center">
  <img src="qr.png" width="260" alt="F-Droid Repo QR Code" />
</p>

### Method 2: One-Click Add (On your Android Device)
Tap this link from your phone or tablet:
👉 **[Add PlayFDroid Repository to F-Droid](fdroidrepos://raw.githubusercontent.com/PlayFDroid/fdroid-repo/gh-pages/fdroid/repo?fingerprint=077873B95749B78FA46971CC117F6F6086639D785208F0DDF3DA2143CB1E8DDD)**

### Method 3: Manual Add in F-Droid Settings
1. Open **F-Droid** -> **Settings** -> **Repositories**.
2. Tap the **+** (Add) button.
3. Paste the repository URL:
   ```text
   https://raw.githubusercontent.com/PlayFDroid/fdroid-repo/gh-pages/fdroid/repo
   ```
4. Enter the cryptographic signing fingerprint:
   ```text
   077873B95749B78FA46971CC117F6F6086639D785208F0DDF3DA2143CB1E8DDD
   ```

---

## ⚡ Using Obtainium

If you prefer [Obtainium](https://github.com/ImranR98/Obtainium) for direct GitHub releases updates:
1. Download or import [`obtainium-pack.json`](https://raw.githubusercontent.com/PlayFDroid/fdroid-repo/gh-pages/fdroid/repo/obtainium-pack.json) into Obtainium.
2. All games in the catalog will be tracked directly from their respective source repositories.

---

## 🏗️ Architecture & Technical Design

- **Zero-Cost 100% GitHub Infrastructure:** Runs entirely on GitHub Actions, GitHub Releases, and `raw.githubusercontent.com` / GitHub Pages CDN.
- **Direct HTTP 200 Delivery:** Bypasses F-Droid client redirect limitations (`followRedirects = false`) by hosting APKs and signed index files directly on Fastly CDN via GitHub Pages/Raw CDN.
- **Zero History Bloat:** The repository deployment uses a stateless orphan branch (`gh-pages`), ensuring Git history never retains deleted or superseded APK versions.
- **Modern Compatibility:**
  - `compileSdk 35` + `targetSdk 24-28` Golden SDK pattern.
  - 64-bit (`arm64-v8a`) native libraries with 16 KB ELF page alignment for Android 15+.
  - Injected modern input overlays for games built for trackballs/physical MENU buttons.

---

## 🕹️ Catalog

Games are automatically tracked from repositories listed in [`apps.txt`](apps.txt).
When any game repo in the organization publishes a tagged release, a GitHub Actions dispatch automatically rebuilds and signs this index.
