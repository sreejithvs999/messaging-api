package com.hotels.datatools.writer

import com.fasterxml.jackson.core.JsonEncoding
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonGenerator
import com.hotels.datatools.data.HotelRecordField
import com.hotels.datatools.writer.config.WriterConfiguration
import java.io.OutputStream

class JsonHotelRecordWriter(targetStream: OutputStream, configuration: WriterConfiguration) :
        AbstractHotelRecordWriter(targetStream, configuration) {

    private val generator: JsonGenerator = JsonFactory().createGenerator(targetStream, JsonEncoding.UTF8)

    override fun writePreRecordsSection() {

        generator.writeStartArray();
    }

    override fun beforeEachRecord() {
        generator.writeStartObject()
    }

    override fun writeField(field: HotelRecordField, value: Any) {

        generator.writeFieldName(field.header)
        when (val formatted = getFormattedValue(field, value)) {
            is Int -> generator.writeNumber(formatted)
            is String -> generator.writeString(formatted)
            else -> AssertionError("json write for type ${formatted.javaClass.name} not supported.")
        }
    }

    override fun afterEachRecord() {
        generator.writeEndObject()
    }

    override fun writePostRecordsSection() {
        generator.writeEndArray();
        generator.flush()
    }
}