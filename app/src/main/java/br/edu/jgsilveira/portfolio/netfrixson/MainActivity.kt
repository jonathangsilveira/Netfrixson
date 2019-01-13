package br.edu.jgsilveira.portfolio.netfrixson

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var progressBarContainer: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val queryButton = findViewById<Button>(R.id.button)
        progressBarContainer = findViewById(R.id.progressContainer)
    }

    private fun onProcessChanged(processing: Boolean?) {
        progressBarContainer.visibility = if (processing == true) View.VISIBLE else View.GONE
    }

    private fun onError(e: Throwable?) {
        e?.let {
            AlertDialog.Builder(this)
                    .setTitle(R.string.error)
                    .setMessage(e.message)
                    .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                    .show()
        }
    }

}
