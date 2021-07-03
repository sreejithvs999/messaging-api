package com.hotels.datatools.data

enum class HotelRecordField(val header: String, val index: Int) {

    NAME("name", 0),
    ADDRESS("address", 1),
    STARS("stars", 2),
    CONTACT("contact", 3),
    PHONE("phone", 4),
    URL("url", 5)
}