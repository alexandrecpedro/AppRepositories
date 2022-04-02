package br.com.dio.app.repositories.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setting ContentView as root
        setContentView(binding.root)
        // Setting Toolbar
        setSupportActionBar(binding.toolbar)
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
        Log.e(TAG, "onQueryTextSubmit: $query")
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