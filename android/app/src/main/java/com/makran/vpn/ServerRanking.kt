package com.makran.vpn
import java.net.URI
import java.net.Socket
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

data class ServerProfile(val name: String, val host: String, val port: Int)
data class ProbeResult(val server: ServerProfile, val latencyMs: Long?, val successRate: Double, val score: Int)
object ServerRanking {
 fun parse(raw: String): ServerProfile? = try {
  val uri = URI(raw.trim()); if (uri.scheme?.lowercase() !in setOf("vless","trojan","ss")) null else if (uri.host == null || uri.port !in 1..65535) null else ServerProfile(uri.fragment ?: uri.host, uri.host, uri.port)
 } catch (_: Exception) { null }
 fun probe(server: ServerProfile): ProbeResult {
  val samples = mutableListOf<Long>()
  repeat(3) { try { val start = System.nanoTime(); Socket().use { it.connect(InetSocketAddress(server.host,server.port),1500) }; samples.add(TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-start)) } catch (_: Exception) {} }
  val median = samples.sorted().let { if (it.isEmpty()) null else it[it.size/2] }
  val ratio = samples.size / 3.0
  return ProbeResult(server,median,ratio,if (median == null) 0 else (ratio*75+25.0*150/(median+150)).toInt().coerceIn(0,100))
 }
 fun rank(servers: List<ServerProfile>): List<ProbeResult> {
  val pool = Executors.newFixedThreadPool(4)
  return try { servers.take(30).map { pool.submit<ProbeResult> { probe(it) } }.mapNotNull { runCatching { it.get(8,TimeUnit.SECONDS) }.getOrNull() }.sortedByDescending { it.score } } finally { pool.shutdownNow() }
 }
}
