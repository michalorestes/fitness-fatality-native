package com.example.fitnessfatality.ui.screens.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.exercise.Exercise
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner.onItemSelectedListener = this
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchExercises().observe(this, Observer {
            if (it != null) {
                val adapter = ArrayAdapter(activity!!, R.layout.adapter_dashboard_spinner, it)
                spinner.adapter = adapter
            }
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        logs_list.removeAllViews()
        val exercise = spinner.selectedItem as Exercise
        viewModel.fetchExerciseLogs(exercise.id).observe(this, Observer { it ->

            it?.forEach {
                val txtView = TextView(context!!)
                txtView.text =
                    "Exercise ID: " + it.log!!.exerciseId + " Weight: " + it.set!!.weightLifted + " Reps: " + it.set!!.numberOfReps
                logs_list.addView(txtView)
            }
        })
    }
}
