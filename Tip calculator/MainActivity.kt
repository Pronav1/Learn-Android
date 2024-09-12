package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TIpCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TIpCalculatorTheme {
                TipCalculator()
            }
        }
    }
}

@Composable
fun TipCalculator() {
    Column(modifier = Modifier.fillMaxWidth()) {
        val amount = remember { mutableStateOf("") }
        var personCounter by remember { mutableStateOf(1) }
        val tipPercentage = remember { mutableStateOf(0f) }

        TotalHeader(getTotalHeaderAmount(amount.value, personCounter, tipPercentage.value))

        UserInputArea(
            amount = amount.value,
            amountChange = { amount.value = it },
            personCounter = personCounter, // Pass the state
            onAddOrReducePerson = {
                if (it < 0) {
                    if (personCounter != 1) {
                        personCounter--
                    }
                } else {
                    personCounter++
                }
            },
            tipPercentage = tipPercentage.value,
            tipPercentageChange = { tipPercentage.value = it }
        )
    }
}

@Composable
fun TotalHeader(amt: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        shape = RoundedCornerShape(8.dp),
        color = colorResource(id = R.color.teal_700)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Per Person",
                style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${amt}",
                style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        }}
}

@Composable
fun UserInputArea(
    amount: String,
    amountChange: (String) -> Unit,
    personCounter: Int, // Receive the state
    onAddOrReducePerson: (Int) -> Unit,
    tipPercentage: Float = 0f,
    tipPercentageChange: (Float) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 20.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = amountChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Enter Bill") },
                placeholder = { Text(text = "Enter Bill") },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Split", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.fillMaxWidth(.50f))

                CustomButton(imageVector = Icons.Default.KeyboardArrowUp) {
                    onAddOrReducePerson.invoke(1)
                }

                Text(
                    text = "$personCounter", // Use the state directly
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                CustomButton(imageVector = Icons.Default.KeyboardArrowDown) {
                    onAddOrReducePerson.invoke(-1)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Tip", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.fillMaxWidth(.70f))
                Text(
                    text = "$ ${getTipAmount(amount, tipPercentage)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$tipPercentage %", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = tipPercentage,
                onValueChange = { tipPercentageChange.invoke(it) },
                valueRange = 0f..100f,
                steps = 5,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun CustomButton(imageVector: ImageVector, onclick: () -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(12.dp),
        shape = CircleShape
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .size(30.dp)
        )
    }
}

@Composable
fun getTipAmount(userAmount: String, tipPercentage: Float): Float {
    return when {
        userAmount.isEmpty() -> 0f
        else -> {
            val amount = userAmount.toFloat()
            amount * (tipPercentage / 100)
        }
    }
}

@Composable
fun getTotalHeaderAmount(amount: String, personCounter: Int, tipPercentage: Float): String {
    return when {
        amount.isEmpty() -> "0"
        else -> {
            val userAmount = amount.toFloat()
            val tipAmount = userAmount * tipPercentage.div(100)
            val perHeadAmount = (userAmount + tipAmount).div(personCounter)
            perHeadAmount.toString()
        }
    }
}