package com.hotels.datatools.validator

import com.hotels.datatools.data.HotelDataValidationException

object HotelNameLengthValidator : RecordFieldValidator<String> {

    private val HOTEL_NAME_MAX_LENGTH = 100

    override fun validate(input: String) {

        if (input.length > HOTEL_NAME_MAX_LENGTH)
            throw HotelDataValidationException(input, "hotel name '$input' exceeds max length $HOTEL_NAME_MAX_LENGTH characters.")
    }
}