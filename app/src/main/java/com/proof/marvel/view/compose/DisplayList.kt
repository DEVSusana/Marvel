package com.proof.marvel.view.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proof.marvel.data.model.Result

@Composable
fun DisplayList(navController: NavController, list: List<Result>) {
    var selectedIndex by remember { mutableStateOf(-1) }
    Surface(color = MaterialTheme.colors.background) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            itemsIndexed(
                items = list
            ) { index, item ->
                listItem(navController = navController, detail = item, index, selectedIndex) { i ->
                    selectedIndex = i
                }
            }
        }
    }

}