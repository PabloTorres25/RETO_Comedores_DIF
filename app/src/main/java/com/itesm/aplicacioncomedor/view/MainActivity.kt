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

    //Animaciones de los Fab
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}

    private var masClicked = false  // Switch de el fabNuevoRegistro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Clic en fabNuevoRegistro
        binding.appBarMain.fabNuevoRegistro.setOnClickListener { view ->
            onfabNuevoregistroButtonClick()
        }
        // Clic en fabFamilia
        binding.appBarMain.fabFamilia.setOnClickListener { view ->
            findNavController(R.id.nav_host_fragment_content_main)
                .navigate(R.id.action_nav_asistencia_to_familiaFragment)
        }
        // Clic en fabPersona
        binding.appBarMain.fabPersona.setOnClickListener { view ->
            findNavController(R.id.nav_host_fragment_content_main)
                .navigate(R.id.action_nav_asistencia_to_nav_nuevo_registro)
        }

        val btnIniciar = findViewById<Button>(R.id.btnIniciarSesion)
        val fabAppBar = findViewById<FloatingActionButton>(R.id.fabNuevoRegistro)
        val fabReporte = findViewById<FloatingActionButton>(R.id.fabReporte)
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
        // Control de visibilidad del FAB
        navController.addOnDestinationChangedListener { _, destination, _ ->

            // Verifica la ID del destino actual
            when (destination.id) {
                R.id.nav_asistencia -> {
                    fabAppBar.visibility = View.VISIBLE  // Muestra el FAB en el fragmento de inicio de sesión
                    fabReporte.visibility = View.VISIBLE
                }
                else -> {
                    fabAppBar.visibility = View.GONE  // Oculta el FAB en otros fragmentos
                    fabReporte.visibility = View.GONE
                }
            }
        }
    }

    private fun onfabNuevoregistroButtonClick() {
        setVisibility(masClicked)
        setAnimation(masClicked)
        masClicked = !masClicked
    }
    private fun setVisibility(masClicked: Boolean) {
        if(!masClicked){
            binding.appBarMain.fabFamilia.visibility = View.VISIBLE
            binding.appBarMain.fabPersona.visibility = View.VISIBLE
            binding.appBarMain.tvFamiliaBarMain.visibility = View.VISIBLE
            binding.appBarMain.tvPersonaBarMain.visibility = View.VISIBLE
        }else{
            binding.appBarMain.fabFamilia.visibility = View.GONE
            binding.appBarMain.fabPersona.visibility = View.GONE
            binding.appBarMain.tvFamiliaBarMain.visibility = View.GONE
            binding.appBarMain.tvPersonaBarMain.visibility = View.GONE
        }
    }
    private fun setAnimation(masClicked: Boolean) {
        if(!masClicked){
            binding.appBarMain.fabFamilia.startAnimation(fromBottom)
            binding.appBarMain.fabPersona.startAnimation(fromBottom)
            binding.appBarMain.tvFamiliaBarMain.startAnimation(fromBottom)
            binding.appBarMain.tvPersonaBarMain.startAnimation(fromBottom)
            binding.appBarMain.fabNuevoRegistro.startAnimation(rotateOpen)
        }else{
            binding.appBarMain.fabFamilia.startAnimation(toBottom)
            binding.appBarMain.fabPersona.startAnimation(toBottom)
            binding.appBarMain.tvFamiliaBarMain.startAnimation(toBottom)
            binding.appBarMain.tvPersonaBarMain.startAnimation(toBottom)
            binding.appBarMain.fabNuevoRegistro.startAnimation(rotateClose)
        }
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