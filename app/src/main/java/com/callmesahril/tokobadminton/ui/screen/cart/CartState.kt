package com.callmesahril.tokobadminton.ui.screen.cart

import com.callmesahril.tokobadminton.model.OrderBadminton


data class CartState(
    val orderBadminton: List<OrderBadminton>,
    val totalRequiredPoint: Int,
)