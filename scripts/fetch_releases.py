#!/usr/bin/env python3
import os
import sys
import urllib.request
import json

os.makedirs("fdroid/repo", exist_ok=True)
obtainium_apps = []

apps_file = "apps.txt"
if not os.path.exists(apps_file):
    print(f"Warning: {apps_file} not found. Creating empty file.")
    with open(apps_file, "w") as f:
        f.write("# PlayFDroid Games Catalog\n")

with open(apps_file) as f:
    repos = [line.strip() for line in f if line.strip() and not line.startswith("#")]

print(f"Discovered {len(repos)} repositories in {apps_file}")

headers = {
    "Accept": "application/vnd.github+json",
    "User-Agent": "PlayFDroid-Repo-Updater"
}
if os.environ.get("GITHUB_TOKEN"):
    headers["Authorization"] = f"Bearer {os.environ['GITHUB_TOKEN']}"

for repo in repos:
    url = f"https://api.github.com/repos/{repo}/releases/latest"
    req = urllib.request.Request(url, headers=headers)
    try:
        with urllib.request.urlopen(req) as resp:
            data = json.loads(resp.read().decode())
            obtainium_apps.append({"id": repo, "url": f"https://github.com/{repo}"})
            assets = data.get("assets", [])
            downloaded = 0
            for asset in assets:
                name = asset.get("name", "")
                if name.endswith(".apk"):
                    apk_url = asset["browser_download_url"]
                    dest = os.path.join("fdroid/repo", name)
                    print(f"Downloading {name} from {repo}...")
                    asset_req = urllib.request.Request(apk_url, headers={"User-Agent": "PlayFDroid-Repo-Updater"})
                    with urllib.request.urlopen(asset_req) as apk_resp, open(dest, "wb") as out_file:
                        out_file.write(apk_resp.read())
                    downloaded += 1
            if downloaded == 0:
                print(f"Notice: No APK assets found in latest release for {repo}")
    except Exception as e:
        print(f"Skipping {repo}: {e}")

obtainium_path = os.path.join("fdroid/repo", "obtainium-pack.json")
with open(obtainium_path, "w") as out:
    json.dump({"apps": obtainium_apps}, out, indent=2)

print(f"Generated {obtainium_path} with {len(obtainium_apps)} entries.")
