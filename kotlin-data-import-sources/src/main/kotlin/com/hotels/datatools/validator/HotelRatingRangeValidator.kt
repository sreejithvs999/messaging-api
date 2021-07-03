package com.hotels.datatools.validator

import com.hotels.datatools.data.HotelDataValidationException

object HotelRatingRangeValidator : RecordFieldValidator<Int> {

    private val HOTEL_STARS_RANGE = 0..5

    override fun validate(input: Int) {

        if (input !in HOTEL_STARS_RANGE)
            throw HotelDataValidationException(input, "hotel stars '$input' not within range $HOTEL_STARS_RANGE")
    }
}