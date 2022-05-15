package com.nishit.nishtrack.data

import com.nishit.nishtrack.dtos.impl.Chapters
import com.nishit.nishtrack.dtos.impl.Transactions

interface DataStore {
    fun readChapters(): Chapters

    fun readTransactions(): Transactions
}