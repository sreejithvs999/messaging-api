package com.hotels.datatools.writer

import com.hotels.datatools.data.HotelRecord
import com.hotels.datatools.support.writerConfiguration
import org.junit.Assert
import java.io.ByteArrayOutputStream
import java.net.URI
import kotlin.test.Asserter
import kotlin.test.Test

class JsonHotelRecordWriterTest {

    private val hotelRecords = listOf(
            HotelRecord("Perrot Weiss", "3, avenue Dufour, 30 039 Parentnec", 2, "Thibault Traore", "0117183681", URI("http://fouquet.net/category/main/")),
            HotelRecord("Mans Hotel", "Weitzelplatz 3, 18923 Amberg", 3, "Torin Ackermann-Henk", "+49(0)3092 055488", URI("http://the.de/author/"))
    )

    private val exoectedJsonText = "[{\"name\":\"Perrot Weiss\",\"address\":\"3, avenue Dufour, 30 039 Parentnec\",\"stars\":2,\"contact\":\"Thibault Traore\",\"phone\":\"0117183681\",\"url\":\"http://fouquet.net/category/main/\"},{\"name\":\"Mans Hotel\",\"address\":\"Weitzelplatz 3, 18923 Amberg\",\"stars\":3,\"contact\":\"Torin Ackermann-Henk\",\"phone\":\"+49(0)3092 055488\",\"url\":\"http://the.de/author/\"}]";

    @Test
    fun testJsonHotelRecordWriter_write_records_as_json() {

        val outputTarget = ByteArrayOutputStream()

        val writer = JsonHotelRecordWriter(outputTarget, writerConfiguration)
        writer.writePreRecordsSection()
        hotelRecords.forEach { writer.write(it) }
        writer.writePostRecordsSection()

        val jsonText = outputTarget.toString(Charsets.UTF_8)
        println(jsonText)
        Assert.assertEquals(jsonText, exoectedJsonText)
    }
}
