package com.itesm.aplicacioncomedor.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import com.google.android.material.appbar.AppBarLayout
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.ActivityMainBinding
import com.itesm.aplicacioncomedor.view.iniciar_sesion.InicioSesionFragment

class MainActivity : AppCompatActivity()  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val toolBa = findViewById<AppBarLayout>(R.id.appBarLayout)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val menu = navView.menu
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Cerrar Sesion
        val menuCerrarSesion = menu.findItem(R.id.inicioSesionFragment)
        menuCerrarSesion.setOnMenuItemClickListener{
            drawerLayout.closeDrawer(GravityCompat.START)

            // Inicia la actividad principal (MainActivity) y finaliza la actividad actual
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            true
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.inicioSesionFragment,
                R.id.nav_reportaProblema,
                R.id.nav_voluntario,
                R.id.nav_asistencia
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->

            // Verifica la ID del destino actual
            when (destination.id) {
                R.id.inicioSesionFragment -> {
                    toolBa.visibility = View.GONE
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else -> {
                    toolBa.visibility = View.VISIBLE
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

        // Comprueba si el fragmento actual es el que no deseas que se retroceda
        if (currentFragment is InicioSesionFragment) {
            return
        }
        super.onBackPressed()
    }
}