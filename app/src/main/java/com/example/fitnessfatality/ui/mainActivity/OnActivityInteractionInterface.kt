package com.example.fitnessfatality.ui.mainActivity

import android.view.MenuItem

interface OnActivityInteractionInterface {
    var floatingActionButtonAction: () -> Unit
    var optionsItemMenuAction: (item: MenuItem?) -> Unit
    fun setFabAction(floatingActionButtonAction:() -> Unit)
    fun setSecondaryBottomAppBarMenu()
    fun setPrimaryBottomAppBarMenu()
}