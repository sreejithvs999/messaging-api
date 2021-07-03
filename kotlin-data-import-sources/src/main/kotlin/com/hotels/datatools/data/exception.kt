package com.hotels.datatools.data

open class HotelDataException(message: String): RuntimeException(message)

class HotelDataValidationException(val value: Any?, message: String): HotelDataException(message)

class HotelDataConversionException(val value: Any?, message: String): HotelDataException(message)