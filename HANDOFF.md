# Makoran VPN - Development Handoff

## Brand identity update
- Established a shared Makoran VPN visual identity in `branding/makoran-icon.svg`.
- Visual language: deep navy, electric blue/turquoise, shield + M, Balochi-inspired embroidery geometry, Makran mountain/sea and sailing-vessel references.
- Android launcher resource `android/app/src/main/res/drawable/ic_makran.xml` was updated to match the brand direction.
- Android manifest continues to reference the vector launcher resource. Future platform clients must derive their icons from the shared SVG and preserve the same visual language.
- See `branding/README.md` for platform export and integration requirements.

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
- Native iOS, Windows, macOS, Linux and web clients do not yet exist; branding assets are prepared for those future targets, not a claim that those apps have been implemented.

## Build and test
- CI build status and artifact are available from the Android Debug APK workflow.
- A successful compile does not prove a live tunnel works; test on a device with a valid server configuration.
- Never commit real WireGuard private keys, preshared keys or server credentials.
