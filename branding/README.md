# Makoran VPN Brand Assets

## Source of truth
Use `makoran-icon.svg` as the scalable source of truth. Do not redraw the logo separately for each platform. Keep the navy / electric-blue / turquoise palette, shield-and-M mark, and Makran/Balochi visual references together.

## Recommended exports
- Android: adaptive icon foreground/background plus legacy launcher PNGs at mdpi, hdpi, xhdpi, xxhdpi and xxxhdpi.
- iOS/iPadOS: 1024x1024 App Store icon and the Xcode asset-catalog app-icon set. Keep the full composition inside the central safe area.
- Windows: multi-resolution ICO (16, 24, 32, 48, 64, 128, 256 px); use PNG for MSIX package assets.
- macOS: ICNS generated from 16 through 1024 px PNG sizes; keep the source SVG in the repository.
- Linux: 16, 24, 32, 48, 64, 128, 256 and 512 px PNGs plus an hicolor icon theme installation.
- Web/PWA: 192x192 and 512x512 PNGs, maskable 512x512 icon, and favicon set.

## Usage rules
- No wordmark text inside the launcher icon.
- Do not stretch, recolor, crop away the embroidery, or add platform-specific decoration.
- For tiny sizes, simplify fine textile and landscape details only; preserve the shield, M and cyan orbit.
- The Android drawable is a native vector implementation of the shared composition. Native clients for other platforms still need their own packaging/build integration when those apps are created.
