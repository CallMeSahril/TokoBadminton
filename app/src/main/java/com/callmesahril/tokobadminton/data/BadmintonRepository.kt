package com.callmesahril.tokobadminton.data


import com.callmesahril.tokobadminton.model.FakeRewardDataSource
import com.callmesahril.tokobadminton.model.OrderBadminton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class BadmintonRepository {

    private val orderBadmintons = mutableListOf<OrderBadminton>()

    init {
        if (orderBadmintons.isEmpty()) {
            FakeRewardDataSource.dummyBadminton.forEach {
                orderBadmintons.add(OrderBadminton(it, 0))
            }
        }
    }

    fun getAllRewards(): Flow<List<OrderBadminton>> {
        return flowOf(orderBadmintons)
    }


    fun getOrderRewardById(rewardId: Long): OrderBadminton {
        return orderBadmintons.first {
            it.badminton.id == rewardId
        }
    }

    fun searchHeroes(query: String): Flow<List<OrderBadminton>> {
        return flow {
            emit(orderBadmintons.filter {
                it.badminton.title.contains(query, ignoreCase = true)
            })
        }
    }


    fun updateOrderReward(rewardId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderBadmintons.indexOfFirst { it.badminton.id == rewardId }
        val result = if (index >= 0) {
            val orderReward = orderBadmintons[index]
            orderBadmintons[index] =
                orderReward.copy(badminton = orderReward.badminton, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderRewards(): Flow<List<OrderBadminton>> {
        return getAllRewards()
            .map { orderRewards ->
                orderRewards.filter { orderReward ->
                    orderReward.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: BadmintonRepository? = null

        fun getInstance(): BadmintonRepository =
            instance ?: synchronized(this) {
                BadmintonRepository().apply {
                    instance = this
                }
            }
    }
}