package com.makran.vpn
import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.*
import kotlin.concurrent.thread

/** Twelve-screen visual MVP. VPN and checkout are intentionally disabled. */
class MainActivity: Activity() {
 private val navy=Color.rgb(9,23,52)
 private val blue=Color.rgb(70,98,249)
 private val light=Color.rgb(243,246,252)
 private lateinit var body:LinearLayout
 private var routing=true
 private var noDomesticQuota=true
 private val names=listOf("شروع","معرفی","ورود و ثبت نام","خانه","سرورها","انتخاب هوشمند","نتایج تست","اشتراک","پرداخت","حساب کاربری","تنظیمات","آمار مصرف")
 override fun onCreate(savedInstanceState:Bundle?) { super.onCreate(savedInstanceState); render(3) }
 private fun dp(x:Int)=(x*resources.displayMetrics.density).toInt()
 private fun background(c:Int)=GradientDrawable().apply {setColor(c);cornerRadius=dp(20).toFloat()}
 private fun label(value:String, size:Float=16f, foreground:Int=navy)=TextView(this).apply {text=value;textSize=size;setTextColor(foreground);setPadding(dp(10),dp(11),dp(10),dp(11));textDirection=android.view.View.TEXT_DIRECTION_RTL}
 private fun space(){body.addView(android.view.View(this),LinearLayout.LayoutParams(1,dp(12)))}
 private fun item(name:String,detail:String,action:(()->Unit)?=null) {val c=LinearLayout(this).apply {orientation=LinearLayout.VERTICAL;setPadding(dp(10),dp(5),dp(10),dp(5));background=background(Color.WHITE);elevation=dp(2).toFloat()};c.addView(label(name,18f));c.addView(label(detail,13f,Color.GRAY));if(action!=null)c.setOnClickListener{action()};body.addView(c,LinearLayout.LayoutParams(-1,-2));space()}
 private fun btn(name:String,click:()->Unit){ val b=Button(this).apply{text=name;isAllCaps=false;setTextColor(Color.WHITE);background=background(blue);setOnClickListener{click()}};body.addView(b,LinearLayout.LayoutParams(-1,dp(54)));space()}
 private fun demo(name:String){Toast.makeText(this,name+" هنوز به سرویس واقعی متصل نشده است",Toast.LENGTH_LONG).show()}
 private fun toggle(name:String,checked:Boolean,cb:(Boolean)->Unit){ val s=Switch(this).apply{text=name;isChecked=checked;setPadding(dp(12),dp(15),dp(12),dp(15));setOnCheckedChangeListener{_,v->cb(v)}};body.addView(s);space() }
 private fun render(p:Int){
 val outer=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(light)}
 val head=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(17),dp(24),dp(17),dp(12));setBackgroundColor(navy)}
 head.addView(label("◈ مکران VPN",26f,Color.WHITE));head.addView(label(names[p],16f,Color.rgb(180,201,255)));outer.addView(head)
 body=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(18),dp(20),dp(18),dp(22))}
 outer.addView(ScrollView(this).apply{addView(body)},LinearLayout.LayoutParams(-1,0,1f))
 val nav=LinearLayout(this).apply{setBackgroundColor(Color.WHITE)}
 listOf("خانه" to 3,"سرورها" to 4,"اشتراک" to 7,"تنظیمات" to 10).forEach{(s,id)->nav.addView(Button(this).apply{text=s;textSize=12f;isAllCaps=false;setOnClickListener{render(id)}},LinearLayout.LayoutParams(0,dp(55),1f))}
 outer.addView(nav);setContentView(outer)
 when(p){
 0->{item("مکران VPN","اتصال امن، سریع و پایدار");btn("شروع"){render(1)}}
 1->{item("دسترسی به اینترنت","انتخاب هوشمند سرور، مدیریت اشتراک و تفکیک ترافیک");btn("شروع کنید"){render(2)}}
 2->{body.addView(EditText(this).apply{hint="ایمیل یا شماره موبایل"});body.addView(EditText(this).apply{hint="رمز عبور";inputType=129});btn("ورود"){demo("ورود")};btn("ورود مهمان"){render(3)}}
 3->{item("● قطع اتصال","هسته Xray هنوز ادغام نشده است");btn("اتصال VPN"){demo("VPN")};item("بهترین سرور","برای بررسی کانفیگ‌ها ضربه بزنید"){render(5)};item("اشتراک من","اشتراک فعال ندارید"){render(7)};item("آمار مصرف","بدون داده واقعی"){render(11)}}
 4->{item("سرور آلمان 🇩🇪","ارزیابی پس از ورود کانفیگ");item("سرور هلند 🇳🇱","ارزیابی پس از ورود کانفیگ");item("سرور ترکیه 🇹🇷","ارزیابی پس از ورود کانفیگ");btn("بررسی سرورها"){render(5)}}
 5->{item("تست هوشمند","هر خط یک لینک VLESS / Trojan / SS. این آزمون TCP است، نه سرعت واقعی تونل.")
 val input=EditText(this).apply{hint="vless://...";minLines=5;setSingleLine(false)};body.addView(input)
 val results=label("");body.addView(results)
 btn("شروع بررسی"){val parsed=input.text.toString().lines().mapNotNull(ServerRanking::parse);if(parsed.isEmpty())results.text="کانفیگ معتبر یافت نشد" else {results.text="در حال بررسی...";thread{val rank=ServerRanking.rank(parsed);runOnUiThread{results.text=rank.take(10).joinToString("\n"){it.server.name+" - "+(it.latencyMs?.toString()?:"ناموفق")+" ms / "+it.score}}}}}
 btn("صفحه نتایج"){render(6)}}
 6->{item("نتایج تست","نتایج واقعی در صفحه انتخاب هوشمند نمایش داده می‌شوند");btn("بازگشت به تست"){render(5)}}
 7->{item("اشتراک یک‌ماهه","قیمت توسط مدیر تعیین می‌شود"){render(8)};item("اشتراک سه‌ماهه","قیمت توسط مدیر تعیین می‌شود"){render(8)};item("اشتراک سالانه","قیمت توسط مدیر تعیین می‌شود"){render(8)}}
 8->{item("پرداخت","درگاه بانکی پس از اتصال به بک‌اند فعال می‌شود");btn("پرداخت"){demo("پرداخت")}}
 9->{item("حساب کاربری","مدیریت اطلاعات و احراز هویت");item("دستگاه‌های مجاز","به‌زودی");item("وضعیت اشتراک","به‌زودی");btn("مشاهده پلن‌ها"){render(7)}}
 10->{item("تفکیک ترافیک داخلی و خارجی","مسیریابی مستقیم برای دامنه‌ها و IPهای داخلی بعد از ادغام Xray فعال می‌شود");toggle("مسیریابی هوشمند",routing){routing=it};toggle("عدم محاسبه مصرف داخلی",noDomesticQuota){noDomesticQuota=it};item("استثناهای دامنه و IP","در نسخه بعدی");btn("حساب کاربری"){render(9)}}
 11->{item("مصرف VPN","۰ مگابایت اندازه‌گیری‌شده؛ تونل غیرفعال");item("مصرف داخلی","پس از اتصال مسیریابی قابل اندازه‌گیری است");item("مصرف خارجی","با شمارنده سرور محاسبه خواهد شد");btn("اشتراک"){render(7)}}
 }
 }
}
