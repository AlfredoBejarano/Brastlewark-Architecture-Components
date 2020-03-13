package me.alfredobejarano.brastlewarkarchitecturecomponents.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Job
import me.alfredobejarano.brastlewarkarchitecturecomponents.databinding.FragmentGnomeListBinding
import me.alfredobejarano.brastlewarkarchitecturecomponents.di.Injector
import me.alfredobejarano.brastlewarkarchitecturecomponents.di.ViewModelFactory
import me.alfredobejarano.brastlewarkarchitecturecomponents.utils.ExceptionReporter
import me.alfredobejarano.brastlewarkarchitecturecomponents.utils.observeSafely
import me.alfredobejarano.brastlewarkarchitecturecomponents.viewmodel.MainViewModel
import javax.inject.Inject

class GnomeListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var dataBinding: FragmentGnomeListBinding
    private var currentJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.component.inject(this)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        FragmentGnomeListBinding.inflate(inflater, parent, false).also { dataBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchBar()
        observeGnomeList()
    }

    private fun observeGnomeList() = observeSafely(viewModel.gnomesLiveData) {
        currentJob = null
        ExceptionReporter.reportException(Exception(it.toString()))
    }

    private fun setupSearchBar() = dataBinding.searchInputEditText.addTextChangedListener {
        currentJob = viewModel.searchForGnome(it)
    }

    private fun cancelJob() {
        if (currentJob?.isCompleted == false) {
            currentJob?.cancel()
            currentJob = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
    }
}