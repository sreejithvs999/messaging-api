package com.hotels.datatools.converter

interface RecordFieldConverter<in S, out T>  {

    fun convert(input: S): T
}

