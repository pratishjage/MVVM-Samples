package com.example.mvvmsample.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey var id: Int,
    @field:SerializedName("name")
    var name: String?,
    @field:SerializedName("username")
    var username: String?,
    @field:SerializedName("email")
    var email: String?,
    @field:SerializedName("phone")
    var phone: String?,
    @field:SerializedName("address")
    @field:Embedded(prefix = "address")
    var address: UserAddress?,
    @field:SerializedName("company")
    @field:Embedded(prefix = "company")
    var company: UserCompany?,
    var isFavourite: Boolean = false


)

data class UserCompany(
    @field:SerializedName("name")
    var name: String?,
    @field:SerializedName("catchPhrase")

    var catchPhrase: String?,
    @field:SerializedName("bs")
    var bs: String?
)

data class UserAddress(
    @field:SerializedName("street")
    var street: String?,
    @field:SerializedName("suite")
    var suite: String?,
    @field:SerializedName("city")
    var city: String?, var zipcode: String?,
    @field:SerializedName("geo")
    @field:Embedded(prefix = "geo_")

    var geo: UserGeo?
)

data class UserGeo(
    @field:SerializedName("lat")

    var lat: String?,
    @field:SerializedName("lng")

    var lng: String?
)


