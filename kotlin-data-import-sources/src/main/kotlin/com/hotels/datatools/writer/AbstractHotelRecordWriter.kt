package com.hotels.datatools.writer

import com.hotels.datatools.data.HotelRecord
import com.hotels.datatools.data.HotelRecordField
import com.hotels.datatools.writer.config.WriterConfiguration
import java.io.InputStream
import java.io.OutputStream

abstract class AbstractHotelRecordWriter(private val targetStream: OutputStream, private val configuration: WriterConfiguration): HotelRecordWriter {


    override fun write(record: HotelRecord) {

        beforeEachRecord()

        writeField(HotelRecordField.NAME, record.name)
        writeField(HotelRecordField.ADDRESS, record.address)
        writeField(HotelRecordField.STARS, record.stars)
        writeField(HotelRecordField.CONTACT, record.contact)
        writeField(HotelRecordField.PHONE, record.phone)
        writeField(HotelRecordField.URL, record.url)

        afterEachRecord()
    }

    override fun close() {

        targetStream.close();
    }

    protected abstract fun beforeEachRecord();
    protected abstract fun writeField(field: HotelRecordField, value: Any)
    protected abstract fun afterEachRecord()

    protected fun getFormattedValue(field: HotelRecordField, value: Any): Any =
            configuration.hotelRecordFormatters[field]?.format(value) ?: value
}