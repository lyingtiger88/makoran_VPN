# Makran VPN

Android Kotlin starter and early VPN client implementation.

Open `android/` in Android Studio. Build requires Android SDK and JDK 17.

## Current implementation
- Android UI prototype and TCP endpoint ranking.
- Gaming mode uses the official `com.wireguard.android:tunnel` library.
- Import a standard WireGuard `.conf` file or paste its contents, authorize Android VPN permission, and connect or disconnect.
- Configuration is parsed in memory and is not written to app preferences or logs.

## Important limitations
- A valid WireGuard server configuration and reachable server are required. No production server, account provisioning, subscriptions or payment backend is included.
- Gaming server discovery, automatic game-route optimization, live jitter/packet-loss analytics and per-app game selection are not implemented.
- Xray remains a separate future engine; no Xray tunnel is active in this build.
- Debug APK is for testing, not store distribution.

## Build
From `android/` run `gradle --no-daemon assembleDebug`, or use the Android Debug APK workflow in GitHub Actions.
