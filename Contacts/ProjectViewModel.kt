package com.example.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.DB.DaoDB
import com.example.contact.DB.Data
import com.example.contact.DB.Event
import com.example.contact.DB.SortType
import com.example.contact.DB.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProjectViewModel(private val dao: DaoDB): ViewModel() {
private val _state = MutableStateFlow(State())

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _contacts = _sortType.flatMapLatest { sortType ->
            when (sortType){
             SortType.FIRST_NAME -> dao.getContactOrderByFirstName()
                SortType.LAST_NAME -> dao.getContactOrderByLastName()
               SortType.PHONE_NUMBER -> dao.getContactsOrderedByPhoneNumber()
        }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _sortType, _contacts){ state, sortType, contacts ->
        state.copy(contacts = contacts, sortType = sortType )
    }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5000), State())


    fun onEvent(event: Event){
    when (event){
        is Event.DeleteContact ->{
            viewModelScope.launch(Dispatchers.IO){
                dao.deleteData(event.contact)
            }
        }
        is Event.HideDialog ->{
            _state.update{it.copy(isAddingContact = false)}
        }
        is Event.SaveContact -> {
            val firstName = state.value.firstName
            val lastName = state.value.lastName
            val phoneNumber = state.value.phoneNumber

            if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                return
            }

            val contact  = Data(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber)

            viewModelScope.launch(Dispatchers.IO){
                dao.insertData(contact)
            }
            _state.update{it.copy(isAddingContact = false, firstName = "", lastName = "", phoneNumber = "")}
        }
        is Event.SetFirstName ->{
            _state.update{it.copy(firstName = event.firstName)}
        }

        is Event.SetLastName ->
            _state.update{it.copy(lastName = event.lastName)}

        is Event.SetPhoneNumber ->
            _state.update{it.copy(phoneNumber = event.phoneNumber)}

        is Event.ShowDialog ->{
            _state.update{it.copy(isAddingContact = true)}
        }
       is Event.SortContacts -> {
       _sortType.value = event.sortType
       }
    }
}


}