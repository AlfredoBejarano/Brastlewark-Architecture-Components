package me.alfredobejarano.brastlewarkarchitecturecomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import me.alfredobejarano.brastlewarkarchitecturecomponents.databinding.ActivityMainBinding
import me.alfredobejarano.brastlewarkarchitecturecomponents.di.ViewModelFactory
import me.alfredobejarano.brastlewarkarchitecturecomponents.viewmodel.GnomeListViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GnomeListViewModel
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}
