package com.callmesahril.tokobadminton.di

import com.callmesahril.tokobadminton.data.BadmintonRepository

object Injection {
    fun provideRepository(): BadmintonRepository {
        return BadmintonRepository.getInstance()
    }
}