package br.com.pagueveloz.tefandroid.utils

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


private const val API_DATE_FORMAT = "yyyy-MM-dd"

private const val API_TIME_FORMAT = "HH:mm:ss"

private const val CLISITEF_DATE_FORMAT = "yyyyMMdd"

private const val CLISITEF_TIME_FORMAT = "HHmmss"

fun Date.differenceInMinutes(other: Date) = Math.abs(TimeUnit.MILLISECONDS.toMinutes(this.time - other.time).toInt())

@SuppressLint("SimpleDateFormat")
fun tryParseDateOrNull(value: String?, pattern: String): Date? {
    return try {
        if (value == null) null else SimpleDateFormat(pattern).parse(value)
    } catch (e: ParseException) {
        null
    }
}

@SuppressLint("SimpleDateFormat")
fun Date.formatApiDateTime(): String {
    return SimpleDateFormat("$API_DATE_FORMAT $API_TIME_FORMAT").format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.format(pattern: String): String = SimpleDateFormat(pattern).format(this)

fun Date.formatDateTime(locale: Locale = Locale.getDefault()): String =
    DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, locale).format(this)

fun Date.formatDate(locale: Locale = Locale.getDefault()): String =
        DateFormat.getDateInstance(DateFormat.SHORT).format(this)

fun Date.truncate(): Date {
    return Calendar.getInstance().apply {
        time = this@truncate
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
}

fun Date.isToday(): Boolean = truncate() == Date(System.currentTimeMillis()).truncate()

enum class Format { DATE, TIME, DATE_TIME }