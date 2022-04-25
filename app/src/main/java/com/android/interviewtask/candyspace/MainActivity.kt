package com.android.interviewtask.candyspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.interviewtask.candyspace.databinding.ActivityMainBinding
import com.android.interviewtask.candyspace.ui.userslist.UsersListFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment? ?: return
        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.userListFragment, R.id.userDetailFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home ->
            {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_interviewtaskDetailFragment_to_interviewtaskListFragment)
            }
        }
        return true
    }
}