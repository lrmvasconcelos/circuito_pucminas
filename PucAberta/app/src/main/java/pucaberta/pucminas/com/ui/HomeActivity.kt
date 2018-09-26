package pucaberta.pucminas.com.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import pucaberta.pucminas.com.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val host = supportFragmentManager.findFragmentById(R.id.myFragment) as NavHostFragment
        val navController = host.navController
        NavigationUI.setupActionBarWithNavController(this, navController,null)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }
}
