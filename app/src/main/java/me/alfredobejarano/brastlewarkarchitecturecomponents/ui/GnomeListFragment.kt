package me.alfredobejarano.brastlewarkarchitecturecomponents.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import me.alfredobejarano.brastlewarkarchitecturecomponents.adapters.GnomesAdapter
import me.alfredobejarano.brastlewarkarchitecturecomponents.databinding.FragmentGnomeListBinding
import me.alfredobejarano.brastlewarkarchitecturecomponents.di.Injector
import me.alfredobejarano.brastlewarkarchitecturecomponents.di.ViewModelFactory
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
        viewModel = ViewModelProvider(
            requireActivity() as ViewModelStoreOwner,
            viewModelFactory
        )[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        FragmentGnomeListBinding.inflate(inflater, parent, false).also { dataBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchBar()
        observeGnomeList()
        dataBinding.gnomesList.layoutManager = LinearLayoutManager(requireContext())
        currentJob = viewModel.getGnomePopulation()
    }

    private fun getSettings() =
        observeSafely(viewModel.getFilterSettings(), dataBinding::setFilterSettings)

    private fun observeGnomeList() = observeSafely(viewModel.gnomesLiveData) { gnomes ->
        getSettings()
        currentJob = null
        dataBinding.gnomesList.run {
            adapter?.let { (adapter as? GnomesAdapter)?.setGnomes(gnomes) } ?: run {
                adapter = GnomesAdapter(gnomes) {

                }
            }
        }
        dataBinding.loadingBar.visibility = View.GONE
    }

    private fun setupSearchBar() = dataBinding.searchInputEditText.addTextChangedListener {
        currentJob = viewModel.searchForGnome(it)
        dataBinding.loadingBar.visibility = View.VISIBLE
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