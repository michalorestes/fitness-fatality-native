package com.example.fitnessfatality.ui

interface OnActivityInteractionInterface {
    var action: () -> Unit
    fun setFabAction(floatingActionButtonAction:() -> Unit)
}