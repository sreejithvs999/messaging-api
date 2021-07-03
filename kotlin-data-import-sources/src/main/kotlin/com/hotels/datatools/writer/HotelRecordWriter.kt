package com.hotels.datatools.writer

import com.hotels.datatools.data.HotelRecord

interface HotelRecordWriter : AutoCloseable {

    fun writePreRecordsSection();

    fun write(record: HotelRecord)

    fun writePostRecordsSection()
}