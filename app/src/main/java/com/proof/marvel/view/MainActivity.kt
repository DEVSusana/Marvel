package com.proof.marvel.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.Result
import com.proof.marvel.domain.repository.Repository
import com.proof.marvel.presentation.viewModel.ViewModel
import com.proof.marvel.presentation.viewModel.ViewModelFactory
import com.proof.marvel.view.compose.NavigationComponent
import com.proof.marvel.view.compose.ShowProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var viewModel: ViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var repository: Repository

    private lateinit var list: List<Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ViewModel::class.java]
        viewModel.getListResponse()
        viewModel.getList.observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        list = response.data.data.results
                        setContent {
                            val navController = rememberNavController()
                            NavigationComponent(
                                navController = navController,
                                list = list,
                                viewModel = viewModel
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(this, "An error occurred : $it", Toast.LENGTH_LONG).show()
                        Log.i("ERROR", it)
                    }
                }

                is Resource.Loading -> {
                    setContent {
                        ShowProgressBar()
                    }


                }
            }
        })
    }
}