package me.alfredobejarano.brastlewarkarchitecturecomponents.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.alfredobejarano.brastlewarkarchitecturecomponents.R
import me.alfredobejarano.brastlewarkarchitecturecomponents.di.Injector
import me.alfredobejarano.brastlewarkarchitecturecomponents.di.ViewModelFactory
import me.alfredobejarano.brastlewarkarchitecturecomponents.utils.ExceptionReporter
import me.alfredobejarano.brastlewarkarchitecturecomponents.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var snackBar: Snackbar

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        setContentView(R.layout.activity_main)

        buildErrorSnackBar()
        observeExceptions()
    }

    private fun observeExceptions() = ExceptionReporter.exceptionLiveData.observe(this, Observer {
        it?.localizedMessage?.run { snackBar.setText(this).show() }
    })

    private fun buildErrorSnackBar() {
        val parentLayout = findViewById(android.R.id.content) as? View
        snackBar = Snackbar.make(parentLayout ?: View(this), "", Snackbar.LENGTH_SHORT).apply {
            view.setBackgroundColor(Color.RED)
            view.background.alpha = 191
        }
    }
}
