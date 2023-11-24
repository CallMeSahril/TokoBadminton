package com.callmesahril.tokobadminton.utils

import java.text.NumberFormat
import java.util.Locale

fun formatAsCurrency(value: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
    return formatter.format(value)
}
