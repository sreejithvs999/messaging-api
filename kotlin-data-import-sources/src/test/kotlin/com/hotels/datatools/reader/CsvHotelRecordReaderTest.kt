package com.hotels.datatools.reader

import com.hotels.datatools.data.HotelDataConversionException
import com.hotels.datatools.data.HotelRecord
import com.hotels.datatools.reader.CsvHotelRecordReader
import com.hotels.datatools.support.readerConfiguration
import org.junit.Assert
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URI
import kotlin.test.Test
import kotlin.test.assertEquals

class CsvHotelRecordReaderTest {

    @Test
    fun testCsvHotelRecordReader_read_HotelRecord_from_csv_input() {

        val reader = CsvHotelRecordReader(CSV_RECORDS.byteInputStream(), readerConfiguration)

         reader.read()?.let {
             assertEquals(it.name, "The Gibson")
             assertEquals(it.contact, "Dr. Sinda Wyman")
         }?: Assert.fail("record is null")

        reader.read()?.let {
            assertEquals(it.address, "Stretto Bernardi 004, Quarto Mietta nell'emilia, 07958 Torino (OG)")
            assertEquals(it.phone, "+39 627 68225719")
        }?: Assert.fail("record is null")

        reader.read()?.let {
            assertEquals(it.stars, 1)
            assertEquals(it.url, URI.create("http://www.garden.com/list/home.html"))
        }?: Assert.fail("record is null")
    }


    @Test
    fun testCsvHotelRecordReader_throw_error_when_wrong_hotel_URL() {

        val reader = CsvHotelRecordReader(CSV_RECORDS_WRONG_URL.byteInputStream(), readerConfiguration)

        val exception = Assert.assertThrows(HotelDataConversionException::class.java){
            reader.read()
        }

        assertEquals(exception.value, "gfgkh%^^&" )
        assertEquals(exception.message, "failed to convert to URI. 'gfgkh%^^&'")
    }

}

private const val CSV_RECORDS = """name,address,stars,contact,phone,url
The Gibson,"63847 Lowe Knoll, East Maxine, WA 97030-4876",5,Dr. Sinda Wyman,1-270-665-9933x1626,http://www.paucek.com/search.htm
Martini Cattaneo,"Stretto Bernardi 004, Quarto Mietta nell'emilia, 07958 Torino (OG)",5,Rosalino Marchetti,+39 627 68225719,http://www.farina.org/blog/categories/tags/about.html
Apartment Dörr,"Bolzmannweg 451, 05116 Hannover",1,Scarlet Kusch-Linke,08177354570,http://www.garden.com/list/home.html

""";

private const val CSV_RECORDS_WRONG_URL = """name,address,stars,contact,phone,url
The Gibson,"63847 Lowe Knoll, East Maxine, WA 97030-4876",5,Dr. Sinda Wyman,1-270-665-9933626,gfgkh%^^&

""";