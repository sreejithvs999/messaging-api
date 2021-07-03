package com.hotels.datatools.writer

import com.hotels.datatools.data.HotelRecord
import com.hotels.datatools.support.writerConfiguration
import org.junit.Assert
import java.io.ByteArrayOutputStream
import java.net.URI
import kotlin.test.Asserter
import kotlin.test.Test

class XmlHotelRecordWriterTest {
    
    private val hotelRecords = listOf(
            HotelRecord("Perrot Weiss", "3, avenue Dufour, 30 039 Parentnec", 2, "Thibault Traore", "0117183681", URI("http://fouquet.net/category/main/")),
            HotelRecord("Mans Hotel", "Weitzelplatz 3, 18923 Amberg", 3, "Torin Ackermann-Henk", "+49(0)3092 055488", URI("http://the.de/author/"))
    )

    private val expectedXmlText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<HotelRecords><HotelRecord><name><![CDATA[Perrot Weiss]]></name><address><![CDATA[3, avenue Dufour, 30 039 Parentnec]]></address><stars><![CDATA[2]]></stars><contact><![CDATA[Thibault Traore]]></contact><phone><![CDATA[0117183681]]></phone><url><![CDATA[http://fouquet.net/category/main/]]></url></HotelRecord><HotelRecord><name><![CDATA[Mans Hotel]]></name><address><![CDATA[Weitzelplatz 3, 18923 Amberg]]></address><stars><![CDATA[3]]></stars><contact><![CDATA[Torin Ackermann-Henk]]></contact><phone><![CDATA[+49(0)3092 055488]]></phone><url><![CDATA[http://the.de/author/]]></url></HotelRecord></HotelRecords>"

    @Test
    fun testXmlHotelRecordWriter_write_records_as_xml() {

        val outputTarget = ByteArrayOutputStream()

        val writer = XmlHotelRecordWriter(outputTarget, writerConfiguration)
        writer.writePreRecordsSection()
        hotelRecords.forEach { writer.write(it) }
        writer.writePostRecordsSection()

        val xmlText = outputTarget.toString(Charsets.UTF_8)
        println(xmlText)
        Assert.assertEquals(xmlText, expectedXmlText)
    }
}


