package com.hotels.datatools.validator

import com.hotels.datatools.data.HotelDataValidationException
import org.junit.Assert
import org.mockito.Mockito
import kotlin.test.Asserter
import kotlin.test.Test


class RecordFieldValidatorTest {


    @Test
    fun testHotelNameLengthValidator_throws_HotelDataValidationException() {

        val validator = HotelNameLengthValidator

        val exception = Assert.assertThrows(HotelDataValidationException::class.java) {
            HotelNameLengthValidator.validate(HOTEL_NAME_WITH_100_PLUS_CHARS)
        }

        Assert.assertEquals(exception.message,
                "hotel name '$HOTEL_NAME_WITH_100_PLUS_CHARS' exceeds max length 100 characters.")

    }

    @Test
    fun testHotelRatingRangeValidator_throws_HotelDataValidationException() {

        val validator = HotelRatingRangeValidator

        val exception1 = Assert.assertThrows(HotelDataValidationException::class.java) {
            HotelRatingRangeValidator.validate(HOTEL_RATING_HIGHER_INVALID)
        }

        val exception2 = Assert.assertThrows(HotelDataValidationException::class.java) {
            HotelRatingRangeValidator.validate(HOTEL_RATING_LOWER_INVALID)
        }

        Assert.assertEquals(exception1.message, "hotel stars '$HOTEL_RATING_HIGHER_INVALID' not within range 0..5")
        Assert.assertEquals(exception2.message, "hotel stars '$HOTEL_RATING_LOWER_INVALID' not within range 0..5")
    }
}

private const val HOTEL_NAME_WITH_100_PLUS_CHARS = "A Really Extremely Long Name for a nice and comfort Hotel in a Town of North Rhine-Westphalia in Germany,";
private const val HOTEL_RATING_LOWER_INVALID = -1
private const val HOTEL_RATING_HIGHER_INVALID = 6