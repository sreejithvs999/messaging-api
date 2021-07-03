package com.hotels.datatools.support

import com.hotels.datatools.data.HotelRecordField
import com.hotels.datatools.converter.HotelRatingConverter
import com.hotels.datatools.converter.HotelURLConverter
import com.hotels.datatools.formatter.HotelURLFormatter
import com.hotels.datatools.reader.config.ReaderConfiguration
import com.hotels.datatools.validator.HotelNameLengthValidator
import com.hotels.datatools.validator.HotelRatingRangeValidator
import com.hotels.datatools.writer.config.WriterConfiguration


val readerConfiguration = ReaderConfiguration()
        .setConverter(HotelRecordField.STARS, HotelRatingConverter)
        .setConverter(HotelRecordField.URL, HotelURLConverter)
        .addValidator(HotelRecordField.NAME, HotelNameLengthValidator)
        .addValidator(HotelRecordField.STARS, HotelRatingRangeValidator)

val writerConfiguration = WriterConfiguration()
        .setFormatter(HotelRecordField.URL, HotelURLFormatter)