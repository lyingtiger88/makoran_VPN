package com.makran.vpn
import android.app.Activity
import android.os.Bundle
import android.widget.*
import kotlin.concurrent.thread
class MainActivity: Activity() {
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
  val layout=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(28,40,28,20) }
  val heading=TextView(this).apply { text="مکران VPN"; textSize=28f }
  val input=EditText(this).apply { hint="کانفیگ های VLESS / Trojan / SS، هر خط یک کانفیگ"; minLines=5 }
  val scan=Button(this).apply { text="پیشنهاد هوشمند سرورها" }
  val connect=Button(this).apply { text="اتصال VPN (هنوز پیاده سازی نشده)"; isEnabled=false }
  val subscribe=Button(this).apply { text="خرید اشتراک (در دست توسعه)"; isEnabled=false }
  val status=TextView(this).apply { text="آزمون TCP نشان دهنده سرعت واقعی تونل نیست." }
  listOf(heading,input,scan,connect,subscribe,status).forEach(layout::addView)
  setContentView(ScrollView(this).apply { addView(layout) })
  scan.setOnClickListener { val servers=input.text.toString().lines().mapNotNull(ServerRanking::parse)
   if (servers.isEmpty()) { status.text="لینک معتبر پیدا نشد"; return@setOnClickListener }
   scan.isEnabled=false; status.text="در حال آزمون TCP..."
   thread { val ranking=ServerRanking.rank(servers); runOnUiThread { status.text=ranking.take(10).joinToString("\n") { r -> r.server.name+" — "+(r.latencyMs?.toString() ?: "unreachable")+"ms — "+r.score+"/100" }; scan.isEnabled=true } }
  }
 }
}
