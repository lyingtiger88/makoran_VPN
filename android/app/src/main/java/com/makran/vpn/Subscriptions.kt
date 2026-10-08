package com.makran.vpn
import java.time.Instant
data class SubscriptionPlan(val id: String, val days: Int, val title: String)
data class SubscriptionStatus(val expiresAt: Instant?) { fun isActive(now: Instant=Instant.now()): Boolean = expiresAt?.isAfter(now) == true }
object SubscriptionCatalog { val plans = listOf(SubscriptionPlan("monthly",30,"اشتراک یک ماهه"),SubscriptionPlan("quarterly",90,"اشتراک سه ماهه")) }
