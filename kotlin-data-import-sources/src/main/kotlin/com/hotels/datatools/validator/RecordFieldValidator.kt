package com.hotels.datatools.validator

interface RecordFieldValidator<in T> {

    fun validate(input: T)
}


