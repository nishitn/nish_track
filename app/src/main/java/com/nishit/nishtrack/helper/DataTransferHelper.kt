package com.nishit.nishtrack.helper

import com.nishit.nishtrack.model.enums.InputType

interface DataTransferHelper {
    fun <T : Any> transferData(data: T, inputType: InputType)
}