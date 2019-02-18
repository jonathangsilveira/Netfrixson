package br.com.pagueveloz.tefandroid.utils

import java.text.NumberFormat
import java.util.*

fun Number.toCurrency(locale: Locale = Locale.getDefault(), minimumIntegerDigits: Int = 2, fractionDigits: Int = 2,
                      groupingUsed: Boolean = false): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(locale).apply {
        this.minimumIntegerDigits = minimumIntegerDigits
        this.minimumFractionDigits = fractionDigits
        this.isGroupingUsed = groupingUsed
    }
    return currencyFormat.format(this.toDouble())
}

fun Number.format(locale: Locale = Locale.getDefault(), minimumIntegerDigits: Int = 1, fractionDigits: Int = 2,
                  groupingUsed: Boolean = false): String {
    val currencyFormat = NumberFormat.getNumberInstance(locale).apply {
        this.minimumIntegerDigits = minimumIntegerDigits
        this.minimumFractionDigits = fractionDigits
        this.isGroupingUsed = groupingUsed
    }
    return currencyFormat.format(this.toDouble())
}
