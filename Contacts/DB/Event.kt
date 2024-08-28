package com.example.contact.DB

sealed interface Event {
    object SaveContact : Event
    data class SetFirstName(val firstName : String) : Event
    data class SetLastName(val lastName : String) : Event
    data class SetPhoneNumber(val phoneNumber: String) : Event
    object ShowDialog : Event
    object HideDialog : Event
    data class SortContacts(val sortType: SortType) : Event
    data class DeleteContact(val contact: Data): Event
}

