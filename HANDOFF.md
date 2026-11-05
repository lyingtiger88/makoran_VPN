# Makran VPN - Development Handoff

## Implemented in 0.4.0
- Added the official WireGuard Android tunnel library.
- Added Android's required WireGuard VpnService declaration.
- Added a Gaming VPN screen with configuration paste/import, Android VPN permission flow, connect and disconnect controls.
- Configuration text is parsed in memory and is not stored in preferences or logged.
- Debug APK is built by GitHub Actions.

## Not implemented
- No production WireGuard servers or backend provisioning.
- No Xray engine or engine-switching UI.
- No game-specific server directory, automatic route optimizer, live jitter/packet-loss measurements or per-app game picker.
- No subscription/payment integration.

## Build and test
- CI build status and artifact are available from the Android Debug APK workflow.
- A successful compile does not prove a live tunnel works; test on a device with a valid server configuration.
- Never commit real WireGuard private keys, preshared keys or server credentials.
