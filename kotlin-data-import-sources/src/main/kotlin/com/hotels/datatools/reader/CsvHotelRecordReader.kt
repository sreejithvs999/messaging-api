package com.hotels.datatools.reader

import com.hotels.datatools.data.HotelRecord
import com.hotels.datatools.data.HotelRecordField
import com.hotels.datatools.reader.config.ReaderConfiguration
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader

class CsvHotelRecordReader(sourceStream: InputStream, private val configuration: ReaderConfiguration) :
        AbstractHotelRecordReader(sourceStream, configuration) {

    private val csvReader: CSVReader = CSVReader(InputStreamReader(sourceStream))

    private val csvHeaders: Array<String>

    init {
        csvHeaders = csvReader.readNext()

    }

    override fun readInternal(): HotelRecord? {

        val fields = csvReader.readNext() ?: return null

        return HotelRecord(
                name = getValue(fields, HotelRecordField.NAME),
                address = getValue(fields, HotelRecordField.ADDRESS),
                stars = getValue(fields, HotelRecordField.STARS),
                contact = getValue(fields, HotelRecordField.CONTACT),
                phone = getValue(fields, HotelRecordField.PHONE),
                url = getValue(fields, HotelRecordField.URL)
        )
    }

    private inline fun <reified T> getValue(record: Array<String>, field: HotelRecordField): T {
        return (configuration.converters[field.header]?.convert(record[field.index]) ?: record[field.index]) as T

    }

    override fun close() {
        csvReader.close();
    }

}