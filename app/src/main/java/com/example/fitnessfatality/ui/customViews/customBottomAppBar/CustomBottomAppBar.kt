package com.example.fitnessfatality.ui.customViews.customBottomAppBar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.example.fitnessfatality.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.custom_view_custom_bottom_app_bar.view.*
import java.lang.Exception

open class CustomBottomAppBar : CoordinatorLayout {
    private var adapter: BottomAppBarAdapter = BottomAppBarAdapter()
    private lateinit var fab: FloatingActionButton
    private lateinit var bar: BottomAppBar

    private var currentFabDrawableId = R.drawable.ic_add_white_24dp

    constructor(applicationContext: Context) : super(applicationContext)
    constructor(
        applicationContext: Context,
        attributeSet: AttributeSet?
    ) : super(applicationContext, attributeSet)

    constructor(
        applicationContext: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        applicationContext,
        attributeSet,
        defStyleAttr
    )

    init {
        initialiseViews()
    }

    private fun initialiseViews() {
        val v = View.inflate(context, R.layout.custom_view_custom_bottom_app_bar, this)
        fab = v.fab
        bar = v.bottom_app_bar
    }

    fun setAdapter(adapter: BottomAppBarAdapter) {
        this.adapter = adapter
        initialiseAppBar()
    }

    private fun initialiseAppBar() {
        isGone = adapter.isGone //TODO: Steps below should not be executed if isGone is true
        setNavigationType()
        setOnClickListeners()
        setDrawables()
    }

    private fun setNavigationType() {
        if (adapter.isPrimaryBottomAppBar) {
            enablePrimaryNavigation()
        } else {
            enableSecondaryNavigation()
        }
    }

    private fun enablePrimaryNavigation() {
        bar.navigationIcon = context.getDrawable(R.drawable.ic_menu_white_24dp)
        bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        bar.replaceMenu(R.menu.navigation)
    }

    private fun enableSecondaryNavigation() {
        bar.navigationIcon = null
        bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
        bar.replaceMenu(adapter.navigationMenu)
    }

    private fun setOnClickListeners() {
        fab.setOnClickListener {
            try {
                adapter.actionListenerInterface.onFloatingActionButtonPress(it)
            } catch (e: Exception) {
                Log.w("-->", "Found exception")
            }
        }

        bar.setOnMenuItemClickListener {
            //TODO: How to catch exception here?
            adapter.actionListenerInterface.onOptionsMenu(it)
        }

        bar.setNavigationOnClickListener {
            try {
                adapter.actionListenerInterface.onNavigationClickListener()
            } catch (e: Exception) {
                Log.w("-->", "Found exception")
            }
        }
    }

    private fun setDrawables() {
        if (currentFabDrawableId != adapter.fabDrawableResourceId) {
            fab.setImageDrawable(ContextCompat.getDrawable(context, adapter.fabDrawableResourceId))
            currentFabDrawableId = adapter.fabDrawableResourceId
        }
    }
}
