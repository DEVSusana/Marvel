package com.proof.marvel.view.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proof.marvel.data.model.Result

@Composable
fun listItem(
    navController: NavController,
    detail: Result,
    index: Int,
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    val backgroundColor =
        if (index == selectedIndex) MaterialTheme.colors.primary else MaterialTheme.colors.background
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Surface(color = backgroundColor) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .clickable { navController.navigate("details/${detail.id}") },
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageMarvel(detail = detail)
                Column {
                    Text(text = detail.name, style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = detail.description, style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}