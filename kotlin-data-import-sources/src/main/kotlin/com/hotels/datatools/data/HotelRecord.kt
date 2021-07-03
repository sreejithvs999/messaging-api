package com.hotels.datatools.data

import java.net.URI

data class HotelRecord(val name: String, val address: String, val stars: Int, val contact: String, val phone: String, val url: URI)