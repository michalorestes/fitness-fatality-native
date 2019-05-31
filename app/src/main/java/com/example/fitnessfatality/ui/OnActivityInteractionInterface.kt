package com.example.fitnessfatality.ui

interface OnActivityInteractionInterface {
    var floatingActionButtonAction: () -> Unit
    fun setFabAction(floatingActionButtonAction:() -> Unit)
    fun setBottomToolBarMenu()
}