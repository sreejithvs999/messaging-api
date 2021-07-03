package com.hotels.datatools.converter

import com.hotels.datatools.data.HotelDataConversionException
import java.net.URI

object HotelURLConverter: RecordFieldConverter<String, URI> {

    override fun convert(input: String): URI {

        try {
           return URI.create(input)
        } catch (e: Exception) {
            throw HotelDataConversionException(input, "failed to convert to URI. '$input'")
        }
    }
}