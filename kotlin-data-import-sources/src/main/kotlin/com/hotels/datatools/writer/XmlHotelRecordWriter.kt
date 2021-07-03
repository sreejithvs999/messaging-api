package com.hotels.datatools.writer

import com.fasterxml.jackson.core.JsonEncoding
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonGenerator
import com.hotels.datatools.data.HotelRecord
import com.hotels.datatools.data.HotelRecordField
import com.hotels.datatools.writer.config.WriterConfiguration
import java.io.FileOutputStream
import java.io.OutputStream
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamWriter


class XmlHotelRecordWriter(targetStream: OutputStream, configuration: WriterConfiguration):
        AbstractHotelRecordWriter(targetStream, configuration) {

    var xmlStreamWriter: XMLStreamWriter = XMLOutputFactory.newInstance()
            .createXMLStreamWriter(targetStream, "UTF-8")

    override fun writePreRecordsSection() {

        xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
        xmlStreamWriter.writeCharacters("\n");
        xmlStreamWriter.writeStartElement("HotelRecords")
    }

    override fun beforeEachRecord() {
        xmlStreamWriter.writeStartElement("HotelRecord")
    }

    override fun writeField(field: HotelRecordField, value: Any) {

        xmlStreamWriter.writeStartElement(field.header)
        xmlStreamWriter.writeCData(getFormattedValue(field, value).toString())
        xmlStreamWriter.writeEndElement()
    }

    override fun afterEachRecord() {
        xmlStreamWriter.writeEndElement()
    }

    override fun writePostRecordsSection() {

        xmlStreamWriter.writeEndElement()
        xmlStreamWriter.writeEndDocument();
        xmlStreamWriter.flush()
        xmlStreamWriter.close()
    }
}