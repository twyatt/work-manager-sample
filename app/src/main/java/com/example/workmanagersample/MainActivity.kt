package com.example.workmanagersample

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import androidx.work.WorkStatus
import kotlinx.android.synthetic.main.activity_main.brush_teeth_list
import kotlinx.android.synthetic.main.activity_main.cancel_brush_teeth
import kotlinx.android.synthetic.main.activity_main.enqueue_brush_teeth
import kotlinx.android.synthetic.main.activity_main.floss
import kotlinx.android.synthetic.main.activity_main.floss_list

class MainActivity : AppCompatActivity() {

    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = (applicationContext as App)

        floss.isChecked = app.preferences.floss

        enqueue_brush_teeth.setOnClickListener { app.work.brushTeethPeriodically() }
        cancel_brush_teeth.setOnClickListener { app.work.stopBrushingTeethPeriodically() }
        floss.setOnCheckedChangeListener { _, isChecked -> app.preferences.floss = isChecked }

        brush_teeth_list.observe(app.work.getBrushTeethStatus())
        floss_list.observe(app.work.getFlossingStatus())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.prune -> app.work.prune()
            R.id.cancel_all -> app.work.cancelAll()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun ViewGroup.observe(liveData: LiveData<List<WorkStatus>>) {
        val lifecycleOwner = this@MainActivity
        liveData.observe(lifecycleOwner, Observer { statuses ->
            removeAllViews()
            statuses?.forEach { addWorkStatusListItem(it) }
        })
    }

    private fun ViewGroup.addWorkStatusListItem(status: WorkStatus) {
        val view = layoutInflater.inflate(R.layout.work_list_item, this, false).apply {
            findViewById<TextView>(R.id.id_label).text = "ID: ${status.id}"
            findViewById<TextView>(R.id.state_label).text = "${status.state}"
            findViewById<TextView>(R.id.data_label).text = "Data: ${status.outputData.keyValueMap}"
            findViewById<TextView>(R.id.tags_label).text = "Tags: ${status.tags}"
        }
        addView(view)
    }
}
