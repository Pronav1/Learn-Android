package com.example.contact.DB

data class State (
    val contacts: List<Data> = emptyList(),
    val firstName:  String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME

)

