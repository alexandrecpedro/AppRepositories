package br.com.dio.app.repositories.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.createDialog
import br.com.dio.app.repositories.core.createProgressDialog
import br.com.dio.app.repositories.core.hideSoftKeyboard
import br.com.dio.app.repositories.databinding.ActivityMainBinding
import br.com.dio.app.repositories.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    // Instance of Dialog
    private val dialog by lazy { createProgressDialog() }
    // Instance of View Model
    private val viewModel by viewModel<MainViewModel>()
    // Instance of RepoListAdapter
    private val adapter by lazy { RepoListAdapter() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setting ContentView as root
        setContentView(binding.root)
        // Setting Toolbar
        setSupportActionBar(binding.toolbar)
        // Associating RecyclerView with RepoListAdapter
        binding.rvRepos.adapter = adapter

        viewModel.repos.observe(this) {
            // Handle all info (it = all states)
            when(it) {
                // Display ProgressDialog bar while loading
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    // Show the error
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    // Stop displaying Dialog bar
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    // Stop displaying ProgressDialog bar when success
                    dialog.dismiss()
                    // Inserting into adapter the list of returns from success state
                    adapter.submitList(it.list)
                }
            }
        }
    }

    /** Building the Search Menu (non-nullable menu) **/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        // Recover what was entered
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        // Listener = SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    /** Implements interface **/
    // Researching
    override fun onQueryTextSubmit(query: String?): Boolean {
        // Testing query nullable
        query?.let { viewModel.getRepoList(it) }
        // Log.e(TAG, "onQueryTextSubmit: $query")
        // Stop displaying keyboard
        binding.root.hideSoftKeyboard()
        return true
    }

    // Each text entered by keyboard
    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG, "onQueryTextChange: $newText")
        return false
    }

    // Testing the previous methods
    companion object {
        private const val TAG = "TAG"
    }
}