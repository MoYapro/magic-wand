package de.moyapro.colors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel : ViewModel() {

    var isCurrentlyDragging by mutableStateOf(false)
        private set

    var items: List<PersonUiItem> by mutableStateOf(emptyList<PersonUiItem>())
        private set

    var addedPersons = mutableStateListOf<PersonUiItem>()
        private set

    init {
        items = listOf(
            PersonUiItem("Michael", "1", Color.Red, Random.nextInt(10_000)),
            PersonUiItem("Larissa", "2", Color.Green, Random.nextInt(10_000)),
            PersonUiItem("Marc", "3", Color.Blue, Random.nextInt(10_000)),
        )
    }

    fun startDragging() {
        isCurrentlyDragging = true
    }

    fun stopDragging() {
        isCurrentlyDragging = false
    }

    fun addPerson(personUiItem: PersonUiItem) {
        println("Added Person")
        items = items.filter { it != personUiItem }
        addedPersons.add(personUiItem)
    }

}