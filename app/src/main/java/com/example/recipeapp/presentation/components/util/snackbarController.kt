package com.example.recipeapp.presentation.components.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class snackbarController(
        private val scope: CoroutineScope
){
    private var snackbaJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun getScope() = scope

    @ExperimentalMaterialApi
    fun showSnackbar(
            scaffoldState: ScaffoldState,
            message: String,
            actionLable: String,
    ){
        if(snackbaJob == null){
            snackbaJob = scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLable
                )
                cancelActiveJob()
            }

        }else{
            cancelActiveJob()
            snackbaJob = scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLable
                )
            }
            cancelActiveJob()
        }
    }

    private fun cancelActiveJob(){
        snackbaJob?.let{ job ->
            job.cancel()
            snackbaJob = Job()
        }
    }
}