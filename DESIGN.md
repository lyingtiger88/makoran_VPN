# Makran VPN — UI implementation guide

Visual palette: Navy #0E1B42, Blue #3B6FF6, Violet #6A5AEE, Background #F6F8FE.
RTL Persian interface, rounded white surface cards, gradient hero and CTA.

## Navigable screens
Splash, Onboarding, Login, Home, Servers, Smart Probe, Results, Plans, Payment, Account, Settings, Usage, Activation.
The initial entry screen is Home for development; other flows can be opened from the UI.

## Truthful feature state
- Live: navigation, color/card system, local switches, parsing of selected proxy-URI formats, TCP endpoint probe.
- Mock / disabled: VPN connection, server availability, user authentication, subscription purchases, payment processing, actual traffic stats and split-tunnel routing.
- The example server country entries are placeholders; they are not measured or provisioned servers.

## Design assets
Original shield brand artwork is in `res/drawable/ic_makran.xml`. Additional reusable XML background resources are in `res/drawable`.
The previous AI-generated contact sheet was a **concept**, not pixel-identical Android assets. The UI here is a programmatic native interpretation of that visual direction.

## Before production
Separate Activity into screens/components, provide accessibility and TalkBack labels, store user settings persistently, integrate Android VpnService + licensed Xray core, add real config validation and per-tunnel measurements, connect verified backend subscriptions and payments, handle secure updates, add unit/instrumentation tests and build a signed release.
