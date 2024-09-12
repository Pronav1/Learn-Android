package com.example.portfoliopranav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.portfoliopranav.ui.theme.PortfolioPranavTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PortfolioPranavTheme {
               Portfolio()

            }
        }
    }

    @Composable
    fun Portfolio() {
        var isOpen by remember{mutableStateOf(false)}
        Surface(
            color = Color.White,
            tonalElevation = 2.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
                .fillMaxHeight()


        ) {
            Column(
            horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(60.dp)
                        .border(2.dp, Color.Black, RoundedCornerShape(40.dp))
                )


                Spacer(modifier = Modifier.size(4.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.size(4.dp))



                Column (verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)){
                    Text(
                        text = "Pranav Pandey",
                        style = TextStyle(
                            color = Color.Yellow,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,

                        )
                    )
                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = "Android Developer",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,))

                        Spacer(modifier = Modifier.size(4.dp))
                    Row (horizontalArrangement = Arrangement.Center){
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your actual icon
                            contentDescription = "Git Hub Icon",
                            modifier = Modifier
                                .size(22.dp)
                                .border(2.dp, Color.Black, RoundedCornerShape(40.dp))
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(text = "Pronav01",
                            style = MaterialTheme.typography.bodyMedium,)

                    Spacer(modifier = Modifier.height(4.dp))

                        }
                    Button(onClick = { isOpen = !isOpen },
                        colors = ButtonDefaults.buttonColors
                            (containerColor = Color.Blue)
                        ){
                        Text(text = "My profile")
                    }

                    if (isOpen){
                        LazyColumn{
                            items(getProjectList()){
                                ProjectItem(it)
                            }

                        }
                    }





                    }


            }
        }
    }
}

@Composable
fun ProjectItem( project : Project) {
   Row(modifier = Modifier
       .fillMaxWidth()
       .padding(8.dp)){
       Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),contentDescription = null,
           modifier = Modifier
               .size(40.dp)
               .clip(CircleShape)
               .border(2.dp, Color.Black, CircleShape)
       )
       Column(modifier = Modifier.padding(horizontal = 8.dp)){
           Text(text = project.name, style = MaterialTheme.typography.headlineMedium)
           Text(text = project.desc, style = MaterialTheme.typography.bodySmall)
       }

   }
    
}
data class Project(val name : String, val desc:String)


    fun getProjectList(): List<Project> {
        return listOf(Project(name = "Social Media App", desc = "Connect with your friends"),
            Project(name = "E-commerce App", desc = "Shop online with ease"),
            Project(name = "Music Player App", desc = "Enjoy your favorite tunes"),
            Project(name ="Fitness Tracker App", desc = "Track your workouts and stay fit"),
            Project(name = "News Reader App", desc = "Stay updated with the latest news"),
            Project(name = "Weather App", desc = "Check the weather forecast"),
            Project(name = "Travel Planner App", desc = "Plan your trips, book flights and hotels, and explore destinations."),
            Project(name = "Language Learning App", desc = "Learn new languages with interactive lessons and exercises."),
            Project(name = "Budgeting App", desc = "Manage your finances, track expenses, and achieve your financial goals.")
        )
    }
