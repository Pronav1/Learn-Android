package com.example.contact

// ... other imports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button // Correct Button import
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.contact.DB.Event
import com.example.contact.DB.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialog(
    state: State,
    onEvent: (Event) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(modifier = modifier,
        onDismissRequest = { onEvent(Event.HideDialog) }, // Handle dismiss
        title = { Text("Add Contact") },
        confirmButton = { // Use confirmButton
            Button(onClick = { onEvent(Event.SaveContact) }) {
                Text("Save Contact")
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = state.firstName,
                    onValueChange = { onEvent(Event.SetFirstName(it)) },
                    placeholder = { Text("First Name") }
                )
                TextField(
                    value = state.lastName,
                    onValueChange = { onEvent(Event.SetLastName(it)) },
                    placeholder = { Text("Last Name") }
                )
                TextField(
                    value = state.phoneNumber,
                    onValueChange = { onEvent(Event.SetPhoneNumber(it)) }, // Correct event
                    placeholder = { Text("Phone Number") } // Correct placeholder
                )
            }
        }
    )
}