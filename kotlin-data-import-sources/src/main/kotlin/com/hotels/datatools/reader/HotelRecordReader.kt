package com.hotels.datatools.reader

import com.hotels.datatools.data.HotelRecord

interface HotelRecordReader : AutoCloseable {

    fun read(): HotelRecord?
}