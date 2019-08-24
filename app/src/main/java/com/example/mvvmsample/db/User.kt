package com.example.mvvmsample.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var id: Int,
    var name: String?,
    var username: String?,
    var email: String?,
    var phone: String?,
    @Embedded
    var address: UserAddress?,
    @Embedded
    var company: UserCompany?


)

data class UserCompany(
    var name: String?,
    var catchPhrase: String?,
    var bs: String?
)

data class UserAddress(
    var street: String?,
    var suite: String?,
    var city: String?, var zipcode: String?,
    @Embedded
    var geo: UserGeo?
)

data class UserGeo(
    var lat: String?,
    var lng: String?
)


