package com.hotels.datatools.writer.config

import com.hotels.datatools.data.HotelRecordField
import com.hotels.datatools.formatter.RecordFieldFormatter

class WriterConfiguration {

     val hotelRecordFormatters: MutableMap<HotelRecordField, RecordFieldFormatter<in Any>> = HashMap()

     fun setFormatter(field: HotelRecordField, formatter: RecordFieldFormatter<*>): WriterConfiguration {
        hotelRecordFormatters[field] = formatter as RecordFieldFormatter<in Any>
        return this
    }
}