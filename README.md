# Makoran VPN

Android Kotlin starter and early VPN client implementation.

## Brand identity
Makoran VPN uses one consistent visual identity across current and future clients:
- Deep navy base with electric blue and turquoise highlights.
- A geometric shield and the letter M for privacy and secure connectivity.
- Balochi-inspired embroidery geometry, Makran's mountain-and-sea landscape, and a small traditional sailing-vessel motif.
- No platform-specific redesigns; platform exports must preserve the same composition and palette.

The scalable source artwork is `branding/makoran-icon.svg`. Platform integration notes and export guidance are in `branding/README.md`.

## Android
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
