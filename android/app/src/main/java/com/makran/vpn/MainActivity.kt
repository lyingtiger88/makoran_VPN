package com.makran.vpn

import android.app.Activity
import android.os.Bundle
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.text.InputType
import kotlin.concurrent.thread

/**
 * Makran VPN visual application — the screens are functional navigation prototypes.
 * The VPN connection, payment provider and subscription APIs are NOT live.
 */
class MainActivity : Activity() {
    private val ink = Color.rgb(20, 31, 65)
    private val navy = Color.rgb(14, 27, 66)
    private val violet = Color.rgb(106, 90, 238)
    private val blue = Color.rgb(59, 111, 246)
    private val muted = Color.rgb(112, 125, 156)
    private val paper = Color.rgb(246, 248, 254)
    private val pale = Color.rgb(236, 240, 255)
    private var selectedTab = 0
    private var selectedServer = "انتخاب خودکار"
    private var routingSmart = true
    private var bypassIran = true
    private var quotaBypass = true
    private lateinit var content: LinearLayout

    private fun dp(n: Int) = (n * resources.displayMetrics.density + 0.5f).toInt()
    private fun bg(color: Int, radius: Int = 20, border: Int? = null) =
        GradientDrawable().apply {
            setColor(color); cornerRadius = dp(radius).toFloat()
            if (border != null) setStroke(dp(1), border)
        }
    private fun gradient() = GradientDrawable(
        GradientDrawable.Orientation.TL_BR, intArrayOf(navy, Color.rgb(39, 49, 124), violet)
    ).apply { cornerRadius = dp(28).toFloat() }
    private fun lp(h: Int = -2) = LinearLayout.LayoutParams(-1, h)
    private fun gap(h: Int = 14) = View(this).also { content.addView(it, lp(dp(h))) }
    private fun text(value: String, size: Float = 15f, color: Int = ink, bold: Boolean = false) =
        TextView(this).apply {
            text = value; textSize = size; setTextColor(color)
            gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
            textDirection = View.TEXT_DIRECTION_RTL
            if (bold) typeface = Typeface.create("sans-serif", Typeface.BOLD)
            setLineSpacing(dp(3).toFloat(), 1f)
        }
    private fun vertical(bgColor: Int = Color.TRANSPARENT, padding: Int = 0) =
        LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            if (padding > 0) setPadding(dp(padding), dp(padding), dp(padding), dp(padding))
            if (bgColor != Color.TRANSPARENT) background = bg(bgColor)
        }
    private fun row(vararg views: View): LinearLayout =
        LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
            views.forEach { addView(it, LinearLayout.LayoutParams(0, -2, 1f)) }
        }
    private fun addCard(title: String, subtitle: String, symbol: String = "✦", click: (() -> Unit)? = null) {
        val c = vertical(Color.WHITE, 17)
        c.addView(text("$symbol  $title", 17f, ink, true))
        val d = text(subtitle, 13f, muted); d.setPadding(0, dp(7), 0, 0); c.addView(d)
        c.elevation = dp(2).toFloat()
        if (click != null) c.setOnClickListener { click() }
        content.addView(c, lp()); gap(12)
    }
    private fun heading(title: String, subtitle: String = "") {
        val t = text(title, 25f, ink, true)
        content.addView(t)
        if (subtitle.isNotEmpty()) {
            val s = text(subtitle, 13f, muted)
            s.setPadding(0, dp(4), 0, 0); content.addView(s)
        }
        gap(22)
    }
    private fun button(caption: String, action: () -> Unit, primary: Boolean = true) {
        val b = TextView(this).apply {
            text = caption; textSize = 16f; gravity = Gravity.CENTER
            setTextColor(if (primary) Color.WHITE else blue)
            typeface = Typeface.DEFAULT_BOLD
            background = if (primary) GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(blue, violet)
            ).apply { cornerRadius = dp(17).toFloat() } else bg(pale, 17)
            setOnClickListener { action() }
        }
        content.addView(b, lp(dp(53))); gap(12)
    }
    private fun note(value: String) {
        val n = text(value, 12f, muted)
        n.setPadding(dp(12), dp(8), dp(12), dp(8))
        content.addView(n)
    }
    private fun switchRow(label: String, summary: String, checked: Boolean, callback: (Boolean) -> Unit) {
        val box = vertical(Color.WHITE, 16)
        val switch = Switch(this).apply {
            text = label; textSize = 15f; isChecked = checked
            setTextColor(ink); layoutDirection = View.LAYOUT_DIRECTION_RTL
            setOnCheckedChangeListener { _, state -> callback(state) }
        }
        box.addView(switch)
        box.addView(text(summary, 12f, muted))
        content.addView(box); gap(12)
    }
    private fun info(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = navy
        window.navigationBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        show("home")
    }

    private fun show(page: String) {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; setBackgroundColor(paper)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
        val toolbar = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(navy)
            setPadding(dp(22), dp(15), dp(22), dp(15))
        }
        val brand = text("◈  مکران VPN", 21f, Color.WHITE, true)
        toolbar.addView(brand, LinearLayout.LayoutParams(0, dp(48), 1f))
        val account = text("☻", 30f, Color.WHITE)
        account.setOnClickListener { show("account") }
        toolbar.addView(account)
        root.addView(toolbar)
        content = vertical()
        content.setPadding(dp(19), dp(23), dp(19), dp(24))
        val scroller = ScrollView(this).apply {
            clipToPadding = false; fillViewport = true
            addView(content)
        }
        root.addView(scroller, LinearLayout.LayoutParams(-1, 0, 1f))
        val nav = LinearLayout(this).apply {
            setBackgroundColor(Color.WHITE); gravity = Gravity.CENTER
        }
        listOf(Triple("⌂", "خانه", "home"), Triple("◉", "سرورها", "servers"),
            Triple("◇", "اشتراک", "plans"), Triple("⚙", "تنظیمات", "settings"))
            .forEach { (symbol, caption, destination) ->
                val active = (page == destination)
                val tab = TextView(this).apply {
                    text = "$symbol\n$caption"; gravity = Gravity.CENTER
                    textSize = 13f; setTextColor(if (active) blue else muted)
                    setPadding(0, dp(7), 0, dp(6))
                    setOnClickListener { show(destination) }
                }
                nav.addView(tab, LinearLayout.LayoutParams(0, dp(62), 1f))
            }
        root.addView(nav)
        setContentView(root)

        when (page) {
            "splash" -> splash()
            "onboarding" -> onboarding()
            "login" -> login()
            "home" -> home()
            "servers" -> servers()
            "scan" -> scan()
            "results" -> results()
            "plans" -> plans()
            "payment" -> payment()
            "account" -> account()
            "settings" -> settings()
            "usage" -> usage()
            "activation" -> activation()
            else -> home()
        }
    }

    private fun splash() {
        gap(65); heading("مکران VPN", "اینترنت سریع، پایدار و قابل اعتماد")
        addCard("اتصال هوشمند", "سرور مناسب براساس کیفیت ارتباط انتخاب می‌شود.", "✦")
        button("شروع") { show("onboarding") }
    }
    private fun onboarding() {
        heading("آزادی در ارتباط", "یک تجربه ساده برای اتصال مطمئن")
        addCard("سرورهای هوشمند", "بررسی کیفیت و پیشنهاد سرورهای مناسب", "✧")
        addCard("حفاظت از حریم خصوصی", "تنظیمات تفکیک ترافیک و کنترل مسیر", "◇")
        addCard("مدیریت اشتراک", "مشاهده اعتبار و وضعیت حساب", "▣")
        button("ادامه") { show("login") }
    }
    private fun login() {
        heading("خوش آمدید", "ورود و ثبت‌نام در حساب کاربری")
        val email = EditText(this).apply {
            hint = "ایمیل یا شماره موبایل"; textDirection = View.TEXT_DIRECTION_LTR
            background = bg(Color.WHITE); setPadding(dp(15),dp(15),dp(15),dp(15))
        }
        content.addView(email, lp()); gap()
        val password = EditText(this).apply {
            hint = "رمز عبور"; inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            background = bg(Color.WHITE); setPadding(dp(15),dp(15),dp(15),dp(15))
        }
        content.addView(password, lp()); gap()
        button("ورود به حساب") { info("ورود واقعی پس از اتصال به SiteBackend فعال می‌شود.") }
        button("ادامه در حالت مهمان", { show("home") }, false)
    }
    private fun home() {
        heading("سلام، خوش آمدید 👋", "وضعیت اتصال و سرور انتخابی")
        val hero = vertical(padding = 23).apply { background = gradient() }
        hero.addView(text("وضعیت اتصال", 15f, Color.rgb(204,212,255)))
        val status = text("◉  قطع اتصال", 26f, Color.WHITE, true)
        status.setPadding(0, dp(22), 0, dp(19)); hero.addView(status)
        hero.addView(text("برای اتصال واقعی، ادغام هسته Xray لازم است.", 13f, Color.WHITE))
        val connect = TextView(this).apply {
            text = "⏻   اتصال"; textSize = 19f; setTextColor(navy)
            gravity = Gravity.CENTER; typeface = Typeface.DEFAULT_BOLD
            background = bg(Color.WHITE, 20); setOnClickListener {
                info("اتصال VPN هنوز پیاده‌سازی نشده است؛ ترافیک دستگاه تغییر نکرد.")
            }
        }
        hero.addView(connect, lp(dp(65)))
        content.addView(hero); gap(18)
        addCard("سرور انتخابی", selectedServer + "  ←  تغییر سرور", "🌐") { show("servers") }
        addCard("انتخاب هوشمند", "اندازه‌گیری دسترسی TCP به سرورهای واردشده", "⚡") { show("scan") }
        addCard("اشتراک من", "فعلاً هیچ اشتراک تأییدشده‌ای ثبت نشده است.", "✧") { show("plans") }
        addCard("گزارش مصرف", "نمایش مصرف پس از اتصال سامانه واقعی", "▥") { show("usage") }
    }
    private fun servers() {
        heading("انتخاب سرور", "سرورهای نمونه برای نمایش رابط کاربری هستند")
        addCard("انتخاب خودکار", "براساس امتیاز کیفیت سرورها", "⚡") {
            selectedServer = "انتخاب خودکار"; show("home")
        }
        listOf("آلمان" to "🇩🇪", "هلند" to "🇳🇱", "ترکیه" to "🇹🇷",
            "فرانسه" to "🇫🇷", "فنلاند" to "🇫🇮").forEach { (name, flag) ->
            addCard(name, "بدون کانفیگ فعال • سرعت نامشخص", flag) {
                selectedServer = name; show("home")
            }
        }
        button("بررسی هوشمند سرورها") { show("scan") }
    }
    private fun scan() {
        heading("انتخاب هوشمند", "فهرست لینک‌ها را وارد کن؛ هر خط یک کانفیگ")
        note("پشتیبانی فعلی آزمون: VLESS / Trojan / SS با آدرس قابل استخراج. این تست فقط اتصال TCP است.")
        val input = EditText(this).apply {
            hint = "vless://...\ntrojan://..."; minLines = 6
            setSingleLine(false); gravity = Gravity.TOP
            background = bg(Color.WHITE); setPadding(dp(16),dp(16),dp(16),dp(16))
            textDirection = View.TEXT_DIRECTION_LTR
        }
        content.addView(input, lp()); gap(15)
        val output = text("هنوز آزمایشی انجام نشده است.", 14f, ink)
        button("شروع بررسی") {
            val items = input.text.toString().lines().mapNotNull(ServerRanking::parse)
            if (items.isEmpty()) { output.text = "لینک قابل بررسی پیدا نشد."; return@button }
            output.text = "در حال آزمایش ${items.size.coerceAtMost(30)} سرور..."
            thread {
                val ranking = ServerRanking.rank(items)
                runOnUiThread {
                    output.text = ranking.take(10).joinToString("\n\n") { r ->
                        "${r.server.name}  •  ${r.latencyMs?.let { \"$it ms\" } ?: \"بدون پاسخ\"}  •  ${r.score}/100"
                    }
                }
            }
        }
        content.addView(output)
    }
    private fun results() {
        heading("نتایج بررسی", "نتایج واقعی در صفحه انتخاب هوشمند نمایش داده می‌شوند")
        addCard("سنجش کیفیت", "برای اجرای آزمون، کانفیگ وارد کنید", "⚡") { show("scan") }
        button("شروع تست") { show("scan") }
    }
    private fun plans() {
        heading("پلن‌های اشتراک", "اشتراک مناسب خود را انتخاب کنید")
        listOf(Triple("اقتصادی", "۳۰ روز • ۳۰ گیگابایت", "◈"),
            Triple("استاندارد", "۳۰ روز • ۶۰ گیگابایت", "✦"),
            Triple("حرفه‌ای", "۹۰ روز • ۲۰۰ گیگابایت", "◆"),
            Triple("سالانه", "۳۶۵ روز • شرایط قابل تنظیم", "◇")).forEach { (name, description, glyph) ->
            addCard(name, description + "\nقیمت هنوز تنظیم نشده است", glyph) { show("payment") }
        }
        button("کد فعال‌سازی دارم", { show("activation") }, false)
    }
    private fun payment() {
        heading("پرداخت اشتراک", "ثبت سفارش و درگاه پرداخت")
        addCard("صورت‌حساب", "برای ثبت سفارش واقعی ابتدا باید بک‌اند و قیمت‌ها متصل شوند.", "▤")
        addCard("روش‌های پرداخت", "هیچ درگاه پرداختی در این نسخه فعال نیست.", "◇")
        button("پرداخت (غیرفعال)") { info("پرداخت فعال نیست و مبلغی دریافت نمی‌شود.") }
    }
    private fun activation() {
        heading("فعال‌سازی اشتراک", "کد دریافت‌شده را وارد کنید")
        val input = EditText(this).apply {
            hint = "MAKRAN-XXXX-XXXX"
            background = bg(Color.WHITE); setPadding(dp(16),dp(16),dp(16),dp(16))
        }
        content.addView(input, lp()); gap()
        button("اعتبارسنجی کد") { info("اعتبارسنجی باید در سرور انجام شود؛ هنوز متصل نیست.") }
    }
    private fun account() {
        heading("حساب کاربری", "مدیریت حساب و دسترسی‌ها")
        addCard("حساب مهمان", "برای ورود واقعی سرویس حساب کاربری لازم است.", "☻") { show("login") }
        addCard("وضعیت اشتراک", "اشتراک فعال ثبت نشده است.", "◇") { show("plans") }
        addCard("فعال‌سازی کد", "ورود کد اشتراک یا تمدید", "✦") { show("activation") }
        addCard("گزارش مصرف", "آمار و میزان حجم مصرف‌شده", "▥") { show("usage") }
    }
    private fun settings() {
        heading("تنظیمات", "شخصی‌سازی نحوه اتصال")
        switchRow("مسیریابی هوشمند", "تفکیک مسیر ترافیک داخلی و خارجی (در انتظار Xray)", routingSmart) { routingSmart = it }
        switchRow("اتصال مستقیم سایت‌های ایران", "براساس قواعد دامنه و IP (در انتظار Xray)", bypassIran) { bypassIran = it }
        switchRow("محاسبه‌نشدن مصرف داخلی", "منوط به پیکربندی صحیح مسیریابی و شمارنده سرور", quotaBypass) { quotaBypass = it }
        addCard("محافظت در برابر نشت DNS", "پس از پیاده‌سازی تونل VPN فعال خواهد شد.", "▣")
        addCard("درباره مکران VPN", "نسخه پیش‌نمایش رابط کاربری 0.3.0", "ⓘ")
        note("تغییر این گزینه‌ها فعلاً فقط در صفحه اعمال می‌شود و مسیر شبکه دستگاه را تغییر نمی‌دهد.")
    }
    private fun usage() {
        heading("آمار مصرف", "گزارش ارتباط و وضعیت اشتراک")
        addCard("مصرف ثبت‌شده VPN", "۰ مگابایت • موتور اتصال غیرفعال", "▥")
        addCard("ترافیک داخلی", "اطلاعاتی وجود ندارد.", "⌂")
        addCard("ترافیک خارجی", "اطلاعاتی وجود ندارد.", "🌐")
        addCard("اعتبار اشتراک", "هنوز به بک‌اند متصل نشده است.", "◇") { show("plans") }
    }
}
