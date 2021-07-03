package com.hotels.datatools.reader

import com.hotels.datatools.data.HotelRecord
import com.hotels.datatools.data.HotelRecordField
import com.hotels.datatools.reader.config.ReaderConfiguration
import java.io.InputStream

abstract class AbstractHotelRecordReader(private val sourceStream: InputStream, private val configuration: ReaderConfiguration) : HotelRecordReader {


    override fun close() {

        sourceStream.close()
    }

    override fun read(): HotelRecord? {

        val hotelRecord = readInternal() ?: return null

        validate(hotelRecord)

        return hotelRecord
    }

    protected abstract fun readInternal(): HotelRecord?

    private fun validate(hotelRecord: HotelRecord) {

        validate(hotelRecord.name, HotelRecordField.NAME)
        validate(hotelRecord.address, HotelRecordField.ADDRESS)
        validate(hotelRecord.stars, HotelRecordField.STARS)
        validate(hotelRecord.contact, HotelRecordField.CONTACT)
        validate(hotelRecord.phone, HotelRecordField.PHONE)
        validate(hotelRecord.url, HotelRecordField.URL)
    }

    private inline fun <reified T> validate(value: T, field: HotelRecordField) {
        configuration.validators[field.header]?.forEach { it.validate(value as Any) }
    }
}