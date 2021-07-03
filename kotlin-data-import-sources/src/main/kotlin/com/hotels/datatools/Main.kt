package com.hotels.datatools

import com.hotels.datatools.reader.factory.HotelRecordReaderFactory
import com.hotels.datatools.writer.factory.HotelRecordWriterFactory
import com.hotels.datatools.support.CommandArguments
import com.hotels.datatools.support.readerConfiguration
import com.hotels.datatools.support.writerConfiguration
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

fun main(args: Array<String>) {

    val command = CommandArguments.parse(args) ?: return

    val hotelDataReaderFactory = HotelRecordReaderFactory
            .getInstance(command.inputFormat, readerConfiguration)

    val hotelRecordWriterFactory = HotelRecordWriterFactory
            .getInstance(command.outputFormat, writerConfiguration)


    hotelDataReaderFactory.getHotelRecordReader(
            BufferedInputStream(FileInputStream(command.input))).use { reader ->

        hotelRecordWriterFactory.getHotelRecordWriter(
                BufferedOutputStream(FileOutputStream(command.output))).use { writer ->

            writer.writePreRecordsSection()
            while (true) {
                reader.read()?.let { record ->
                    writer.write(record)
                    println("wrote: $record")
                } ?: break
            }
            writer.writePostRecordsSection()
        }
    }

    println("End,")
}
