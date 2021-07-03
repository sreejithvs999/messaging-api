package com.hotels.datatools.formatter

import java.net.URI

object HotelURLFormatter: RecordFieldFormatter<URI> {

    override fun format(input: URI): String {

        return input.toString()
    }
}