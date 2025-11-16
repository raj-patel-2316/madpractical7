package com.example.madpractical7

import org.json.JSONObject
import java.io.Serializable

data class Person(
    val id: String,
    val name: String,
    val emailId: String,
    val phoneNo: String,
    val address: String,
    val latitude: Double?,
    val longitude: Double?
) : Serializable {
    companion object {
        fun fromJson(obj: JSONObject): Person {
            val id = obj.optString("id")
            val emailId = obj.optString("email")
            val phoneNo = obj.optString("phone")
            val profile = obj.optJSONObject("profile")
            val name = profile?.optString("name") ?: obj.optString("name")
            val address = profile?.optString("address") ?: obj.optString("address")
            val loc = profile?.optJSONObject("location") ?: obj.optJSONObject("location")
            val lat = loc?.optDouble("lat")?.let { if (it.isNaN()) null else it }
            val lon = loc?.optDouble("long")?.let { if (it.isNaN()) null else it }
            return Person(id, name, emailId, phoneNo, address, lat, lon)
        }
    }
}
