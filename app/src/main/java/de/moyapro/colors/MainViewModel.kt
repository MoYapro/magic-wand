package de.moyapro.colors

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.random.Random


class MainViewModel : ViewModel() {

    val TAG = "MainViewModel"

    private var dragData: PersonUiItem? by mutableStateOf(null)
    var isCurrentlyDragging by mutableStateOf(false)
        private set

    var items: List<PersonUiItem> by mutableStateOf(emptyList<PersonUiItem>())
        private set

    var addedPersons = mutableStateListOf<PersonUiItem>()
        private set

    init {
        items = listOf(
            PersonUiItem("Larissa", "2", Color.Green, Random.nextInt(10_000)),
            PersonUiItem("Marc", "3", Color.Blue, Random.nextInt(10_000)),
            PersonUiItem("Michael", "1", Color.Red, Random.nextInt(10_000)),
            PersonUiItem("Tony", "4", Color.Magenta, Random.nextInt(10_000))
        )
    }

    fun startDragging(dragData: PersonUiItem) {
        Log.d(TAG, "start dragging: $dragData")
        this.dragData = dragData
        isCurrentlyDragging = true
    }

    fun stopDragging() {
        isCurrentlyDragging = false
    }

    fun addPerson(personUiItem: PersonUiItem) {
        println("Added Person new Item: $personUiItem, modelItem: ${this.dragData}")
        items = items.filter { it != personUiItem }
        addedPersons.add(personUiItem)
    }

}
