package com.hotels.datatools.converter

import com.hotels.datatools.data.HotelDataConversionException

object HotelRatingConverter: RecordFieldConverter<String, Int> {

    override fun convert(input: String): Int {

        try {
            return Integer.parseInt(input)
        } catch (e: Exception) {
            throw HotelDataConversionException(input, "failed to convert to number. '$input'")
        }
    }
}
