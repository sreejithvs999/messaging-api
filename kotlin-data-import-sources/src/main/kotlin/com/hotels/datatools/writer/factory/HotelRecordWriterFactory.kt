package com.hotels.datatools.writer.factory

import com.hotels.datatools.data.HotelDataException
import com.hotels.datatools.writer.HotelRecordWriter
import com.hotels.datatools.writer.JsonHotelRecordWriter
import com.hotels.datatools.writer.XmlHotelRecordWriter
import com.hotels.datatools.writer.config.WriterConfiguration
import java.io.OutputStream

class HotelRecordWriterFactory {

    private val outputFormat: String
    private val configuration: WriterConfiguration

    private constructor(outputFormat: String, configuration: WriterConfiguration) {
        this.outputFormat = outputFormat
        this.configuration = configuration
    }

    fun getHotelRecordWriter(targetStream: OutputStream): HotelRecordWriter {

        return when (outputFormat) {

            OutputFormats.JSON -> JsonHotelRecordWriter(targetStream, configuration)
            OutputFormats.XML -> XmlHotelRecordWriter(targetStream, configuration)
            else -> throw HotelDataException("output format not supported: $outputFormat")
        }
    }

    object OutputFormats {
        const val JSON = "json"
        const val XML = "xml"
    }

    companion object {

        fun getInstance(outputFormat: String, configuration: WriterConfiguration) =
                HotelRecordWriterFactory(outputFormat, configuration)

    }
}