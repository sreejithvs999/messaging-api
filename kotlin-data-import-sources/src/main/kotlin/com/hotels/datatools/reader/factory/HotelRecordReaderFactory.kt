package com.hotels.datatools.reader.factory

import com.hotels.datatools.data.HotelDataException
import com.hotels.datatools.reader.CsvHotelRecordReader
import com.hotels.datatools.reader.HotelRecordReader
import com.hotels.datatools.reader.config.ReaderConfiguration
import java.io.InputStream

class HotelRecordReaderFactory {

    private val inputFormat: String
    private val readerConfiguration: ReaderConfiguration

    private constructor(inputFormat: String, readerConfiguration: ReaderConfiguration) {
        this.inputFormat = inputFormat
        this.readerConfiguration = readerConfiguration
    }

    public fun getHotelRecordReader(sourceStream: InputStream): HotelRecordReader {

        return when (inputFormat) {
            InputFormats.CSV -> CsvHotelRecordReader(sourceStream, readerConfiguration)
            else -> throw HotelDataException("input format not supported: $inputFormat")
        }
    }

    object InputFormats {
        const val CSV = "csv"
        const val XLS = "xls"
    }

    companion object {
        fun getInstance(inputFormat: String, readerConfiguration: ReaderConfiguration) =
                HotelRecordReaderFactory(inputFormat, readerConfiguration)

    }
}

