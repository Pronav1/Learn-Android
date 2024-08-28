package com.example.contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contact.DB.Event
import com.example.contact.DB.SortType
import com.example.contact.DB.State

@Composable
fun ContactScreen(
    state: State,
    onEvent: (Event) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {onEvent(Event.ShowDialog)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Contact")
            }
        },
        modifier = Modifier.padding(16.dp)
    ) { padding ->if(state.isAddingContact){
        AddContactDialog(state , onEvent = onEvent, Modifier)
    }
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(Event.SortContacts(sortType))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = { onEvent(Event.SortContacts(sortType)) }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.contacts){ contact ->
                Row(modifier = Modifier.fillMaxSize()){
                    Column(modifier = Modifier.weight(1f)){
                        Text(text = "${contact.firstName} ${contact.lastName}",
                            fontSize = 20.sp)
                        Text(text = contact.phoneNumber, fontSize = 12.sp)
                    }
                    IconButton(onClick = { onEvent(Event.DeleteContact(contact))}){
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "DeleteContact")
                    }
                }


            }

        }
    }
}