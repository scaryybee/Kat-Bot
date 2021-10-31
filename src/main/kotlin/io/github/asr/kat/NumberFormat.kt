package io.github.asr.kat

const val koreanNum1 = 10000000000000000L
const val koreanNum2 = 1000000000000L
const val koreanNum3 = 100000000L
const val koreanNum4 = 10000L

fun Boolean.toInt() = if (this) 1 else 0

fun Long.formatNumber() = if (this == 0L) "0" else "${this / koreanNum1}경 ".repeat((this >= koreanNum1).toInt()) +
        "${this % koreanNum1 / koreanNum2}조 ".repeat((this >= koreanNum2).toInt()) +
        "${this % koreanNum2 / koreanNum3}억 ".repeat((this >= koreanNum3).toInt()) +
        "${this % koreanNum3 / koreanNum4}만 ".repeat((this >= koreanNum4).toInt()) +
        "${this % koreanNum4}".repeat((this % koreanNum4 != 0L).toInt())