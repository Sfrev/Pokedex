package ufscar.dc.Pokedex

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ufscar.dc.Pokedex.databinding.ActivityPokemonScreenBinding

class PokemonScreen : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPokemonScreenBinding

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_screen)

        layoutManager = LinearLayoutManager(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.layoutManager = layoutManager


        var rList = ArrayList<ArrayList<String>>()

        for (i in 1..10){
            val first = listOf("Nome", "Hoje", "Truco", "Damo", "Kaline", "Sfrev", "Magal").asSequence().shuffled().find {true}
            val second = listOf("Esquisito", "Hoje", "Truco", "Damo", "Kaline", "Sfrev", "Magal").asSequence().shuffled().find {true}
            rList += arrayListOf<String>(first!!, second!!)
        }

        println("CRIADOOOOOOOOOOOO:\n" + rList)

        adapter = CustomAdapter(rList)

        recyclerView.adapter = adapter
//        val layMan = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        recyclerView.layoutManager = layMan
//        binding = ActivityPokemonScreenBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_pokemon_screen)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAnchorView(R.id.fab)
//                .setAction("Action", null).show()
//        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_pokemon_screen)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}