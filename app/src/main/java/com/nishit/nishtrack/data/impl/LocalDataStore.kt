package com.nishit.nishtrack.data.impl

import com.nishit.nishtrack.data.DataStore
import com.nishit.nishtrack.dtos.impl.Chapters
import com.nishit.nishtrack.dtos.impl.Transactions

object LocalDataStore : DataStore {
    override fun readChapters(): Chapters {
        TODO("Not yet implemented")
    }

    override fun readTransactions(): Transactions {
        TODO("Not yet implemented")
    }
}