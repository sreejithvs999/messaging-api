package com.hotels.datatools.formatter

interface RecordFieldFormatter<T> {

    fun format(input: T): String
}

