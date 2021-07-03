package com.hotels.datatools.reader.config

import com.hotels.datatools.data.HotelRecordField
import com.hotels.datatools.converter.RecordFieldConverter
import com.hotels.datatools.validator.RecordFieldValidator

class ReaderConfiguration {

    val validators: MutableMap<String, MutableList<RecordFieldValidator<Any>>> = HashMap()
    val converters: MutableMap<String, RecordFieldConverter<Any, Any>> = HashMap()

    fun addValidator(field: HotelRecordField, validator: RecordFieldValidator<*>): ReaderConfiguration {

        validators[field.header] = (validators[field.header] ?: ArrayList()).also { it.add(validator as RecordFieldValidator<Any>) }
        return this
    }

    fun setConverter(field: HotelRecordField, converter: RecordFieldConverter<*, *>): ReaderConfiguration {

        converters[field.header] = converter as RecordFieldConverter<in Any, out Any>
        return this
    }
}