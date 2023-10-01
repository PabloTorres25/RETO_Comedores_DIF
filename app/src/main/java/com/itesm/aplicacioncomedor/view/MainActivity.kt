package com.itesm.aplicacioncomedor.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)



        val btnIniciar = findViewById<Button>(R.id.btnIniciarSesion)
        val toolBa = findViewById<AppBarLayout>(R.id.appBarLayout)
        btnIniciar.setOnClickListener {
            //fabAppBar.visibility = View.VISIBLE
            toolBa.visibility = View.VISIBLE
            findNavController(R.id.nav_host_fragment_content_main)
                .navigate(R.id.action_inicioSesionFragment_to_nav_asistencia)
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_reportaProblema,
                R.id.nav_voluntario,
                R.id.nav_nuevo_registro,
                R.id.nav_asistencia
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}