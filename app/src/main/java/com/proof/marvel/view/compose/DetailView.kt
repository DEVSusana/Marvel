package com.proof.marvel.view.compose

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.presentation.viewModel.ViewModel

@ExperimentalCoilApi
@Composable
fun DetailView(viewModel: ViewModel, id: Int) {
    viewModel.getDetailResponse(id)

    when (viewModel.getDetail.value) {
        is Resource.Success -> {
            val detail by remember {
                mutableStateOf(viewModel.getDetail.value)
            }
            viewModel.getDetail.value?.data.let {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (detail != null) {
                        detail!!.data?.data?.results?.get(0)
                            ?.let { it1 -> ImageMarvel(detail = it1) }
                    }
                    Column {
                        if (detail != null && detail!!.data?.data?.results?.isNotEmpty() == true) {
                            detail!!.data?.data?.results?.get(0)?.let { it1 ->
                                Text(
                                    text = it1.name,
                                    style = MaterialTheme.typography.h5
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            detail!!.data?.data?.results?.get(0)?.description?.let { it1 ->
                                Text(
                                    text = it1,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            if(detail!!.data?.data?.results?.get(0)?.stories?.items?.isNotEmpty() == true) {
                                detail!!.data?.data?.results?.get(0)?.stories?.items?.get(0).let {
                                    detail!!.data?.data?.results?.get(0)?.stories?.items?.get(0)
                                        ?.let { it1 ->
                                            Text(
                                                text = it1.name,
                                                style = MaterialTheme.typography.body2
                                            )
                                        }
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }
                            detail!!.data?.data?.results?.get(0)?.events?.items.let {
                                if (detail!!.data?.data?.results?.get(0)?.events?.items?.isNotEmpty() == true) {
                                    detail!!.data?.data?.results?.get(0)?.events?.items?.get(0)
                                        ?.let { it1 ->
                                            Text(
                                                text = it1.name,
                                                style = MaterialTheme.typography.body2
                                            )
                                        }
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