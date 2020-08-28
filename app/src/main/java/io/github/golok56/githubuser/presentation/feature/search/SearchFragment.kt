package io.github.golok56.githubuser.presentation.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.golok56.githubuser.R
import io.github.golok56.githubuser.data.repository.githubuser.LimitReachedException
import io.github.golok56.githubuser.databinding.FragmentSearchBinding
import io.github.golok56.githubuser.presentation.EspressoIdlingVersion
import io.github.golok56.githubuser.presentation.feature.search.di.DaggerSearchComponent
import kotlinx.android.synthetic.main.error.view.*
import java.net.UnknownHostException
import javax.inject.Inject

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory

    private lateinit var vm: SearchViewModel
    private val searchAdapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        DaggerSearchComponent.builder().build().inject(this)
        setupViewModel()

        return with(binding) {
            rvGithubUsers.adapter = searchAdapter
            vError.btnError.setOnClickListener { vm.updateQuery(etSearchUser.text.toString()) }
            etSearchUser.addTextChangedListener(SearchTextWatcher { vm.updateQuery(it) })
            root
        }
    }

    private fun setupViewModel() {
        vm = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java].apply {
            githubUsers.observe(viewLifecycleOwner, Observer {
                searchAdapter.submitList(it)
            })

            loading.observe(viewLifecycleOwner, Observer {
                with(binding) {
                    if (it) showLoading()
                    else {
                        pbSearch.visibility = View.GONE
                        if (searchAdapter.itemCount != 0) hideError()
                        else {
                            showError(
                                getString(R.string.not_found_message, etSearchUser.text.toString()),
                                R.drawable.ic_not_found
                            )
                            vError.btnError.visibility = View.GONE
                        }
                    }
                }
            })

            error.observe(viewLifecycleOwner, Observer {
                binding.pbSearch.visibility = View.GONE
                if (it is LimitReachedException) onLimitReached(it.message)
                else if (it is UnknownHostException) onNoInternet()
            })
        }
    }

    private fun onLimitReached(message: String?) = with(binding) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        if (searchAdapter.itemCount != 0) hideError()
        else showError(message, R.drawable.ic_limit_reached)
    }

    private fun onNoInternet() = with(binding) {
        val message = getString(R.string.no_internet_message)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        if (searchAdapter.itemCount != 0) hideError()
        else showError(message, R.drawable.ic_no_internet)
    }

    private fun showLoading() = with(binding) {
        pbSearch.visibility = View.VISIBLE
        rvGithubUsers.visibility = View.GONE
        vError.root.visibility = View.GONE
    }

    private fun showError(message: String?, drawableResource: Int) = with(binding) {
        vError.root.visibility = View.VISIBLE
        vError.root.ivError.setImageResource(drawableResource)
        vError.root.tvError.text = message
        vError.btnError.visibility = View.VISIBLE
        rvGithubUsers.visibility = View.GONE
    }

    private fun hideError() = with(binding) {
        rvGithubUsers.visibility = View.VISIBLE
        vError.root.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}