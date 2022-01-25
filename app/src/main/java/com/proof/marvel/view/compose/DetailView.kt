package com.proof.marvel.view.compose

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.presentation.viewModel.ViewModel

@Composable
fun detailView(viewModel: ViewModel, id: Int) {
    viewModel.getDetailResponse(id)
    val detail = viewModel.getDetailState.value?.data

    when (viewModel.getDetail.value) {
        is Resource.Success -> {
            viewModel.getDetail.value?.data.let {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (detail != null) {
                        imageMarvel(detail = detail.data.results[0])
                    }
                    Column {
                        if (detail != null && detail.data.results.isNotEmpty() ) {
                            Text(
                                text = detail.data.results[0].name,
                                style = MaterialTheme.typography.h3
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = detail.data.results[0].description,
                                style = MaterialTheme.typography.h5
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            detail.data.results[0].stories.items[0].let {
                                Text(
                                    text = detail.data.results[0].stories.items[0].name,
                                    style = MaterialTheme.typography.h5
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            detail.data.results[0].events.items.let {
                                if(detail.data.results[0].events.items.isNotEmpty()) {
                                    Text(
                                        text = detail.data.results[0].events.items[0].name,
                                        style = MaterialTheme.typography.h5
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
        is Resource.Error -> {
            (viewModel.getDetail.value as Resource.Error<MarvelApiResponse>).message?.let {
                Toast.makeText(LocalContext.current, "An error occurred : $it", Toast.LENGTH_LONG)
                    .show()
                Log.i("ERROR", it)
            }
        }

        is Resource.Loading -> {
            ShowProgressBar()
        }

    }

}