# Makoran VPN - Gaming Mode

## New proposal: WireGuard for gaming

Add a dedicated Gaming mode using WireGuard alongside the existing Xray connection option.

### User experience
- Add two modes on the home screen: Standard and Gaming.
- Show a separate list of gaming locations.
- Display measured latency (ms), jitter (ms), packet loss (%) and server load when real measurements are available.
- Add automatic server selection based on repeated measurements, not a single ping.
- Allow the user to select which installed apps use the VPN, where Android supports this safely.
- Show connection state and reconnect after network changes.

### Technical design
- Keep WireGuard and Xray as separate connection engines.
- Android should have one active VPN tunnel per app session; switching engines must stop and release the previous tunnel before starting the next.
- Use a maintained, appropriately licensed WireGuard Android implementation.
- Generate and store client private keys securely on-device. Never commit keys or credentials to the repository.
- Obtain server configurations from an authenticated backend.
- Add real server health checks and monitor capacity.

### Gaming server selection
Compare repeated latency measurements, jitter, packet loss, server load and route stability. A nearby VPN server does not guarantee a better route to every game server. Do not promise lower ping unless measurements prove it.

### Implementation order
1. Check Android SDK, minSdk and Gradle compatibility.
2. Define a common connection interface with separate Xray and WireGuard implementations.
3. Implement VPN lifecycle and safe engine switching.
4. Add secure configuration provisioning through the backend.
5. Test a real WireGuard server, DNS handling, IPv4/IPv6 and network changes.
6. Add latency, jitter and packet-loss measurements.
7. Add per-app routing and test supported games.
8. Run battery, stability and security tests before release.

### Current status
This is a proposal only. WireGuard gaming support is not implemented. Existing project documentation states that VPN tunneling, real server availability and traffic statistics are not yet live.

### Development rules
Keep Makoran VPN independent from unrelated projects. Record changed files and actual build/test results in this handoff. Never describe mock data as live service data.
