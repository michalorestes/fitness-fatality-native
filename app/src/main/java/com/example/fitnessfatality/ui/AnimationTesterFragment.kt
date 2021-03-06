package com.example.fitnessfatality.ui

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.PathInterpolator
import androidx.fragment.app.Fragment
import com.example.fitnessfatality.R
import kotlinx.android.synthetic.main.fragment_animation_tester.*

class AnimationTesterFragment: Fragment() {

    private lateinit var image: View
    private var shortAnimationDuration: Int = 0
    private var isImageVisible = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_animation_tester, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image = img_image
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        btn_click_me.setOnClickListener {
        }
    }
}