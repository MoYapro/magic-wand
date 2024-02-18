package de.moyapro.colors

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ,
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            mainViewModel.items.forEach { person ->
                Draggable(
                    dataToDrop = person,
                    viewModel = mainViewModel
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dp(screenWidth / 5f))
                            .clip(RoundedCornerShape(15.dp))
                            .shadow(5.dp, RoundedCornerShape(15.dp))
                            .background(person.backgroundColor, RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column {
                            Text(
                                text = person.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = person.value.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
            DropZone<PersonUiItem>(
                modifier = Modifier
                    .size(Dp(screenWidth / 3.5f))
            ) { isInBound, personItem ->
                if (personItem != null) {
                    LaunchedEffect(key1 = personItem) {
                        mainViewModel.items
                        mainViewModel.addPerson(personItem)
                    }
                }
                if (isInBound) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                1.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(Color.Gray.copy(0.5f), RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = " >> Add Person << ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(
                                Color.Black.copy(0.5f),
                                RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Add Person",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .padding(bottom = 100.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Added Persons",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                mainViewModel.addedPersons.forEach { person ->
                    Text(
                        text = person.name,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}