package org.kenuki.securityservice.core
const val SECOND_IN_MS: Long = 1000L
const val MINUTE_IN_MS: Long = 60L * SECOND_IN_MS
const val HOUR_IN_MS: Long = 60L * MINUTE_IN_MS
const val DAY_IN_MS: Long = 24L * HOUR_IN_MS
const val MONTH_IN_MS: Long = 30L * DAY_IN_MS

const val ACCESS_LIFETIME_IN_MS: Long = 5L * MINUTE_IN_MS
const val REFRESH_LIFETIME_IN_MS: Long = MONTH_IN_MS