package ufscar.dc.Pokedex

import android.content.ContentValues
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.Async
import retrofit2.Response
import ufscar.dc.Pokedex.databinding.ActivityPokemonScreenBinding
import java.lang.Exception
import java.util.Locale

class PokemonScreen : AppCompatActivity(), CoroutineScope by MainScope(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPokemonScreenBinding
    private lateinit var dbHelper: DbHelper

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>? = null
    private var rList = ArrayList<ArrayList<String>>()
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        dbHelper = DbHelper(this)
        val pokeApi = getPokeApi(this.applicationContext).create(PokeApi::class.java)
//        println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
//        println(pokeApi)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_screen)

        layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView!!.layoutManager = layoutManager
        println("DEGUB MANUAL")
        val id = intent.getIntExtra("id", -1)
        val captured = intent.getBooleanExtra("captured", false)

//        var rList = ArrayList<ArrayList<String>>()

        GlobalScope.launch(Dispatchers.IO) {
            val poke = getPoke(pokeApi, id)

            val text = findViewById<TextView>(R.id.textView)

            text.setText(poke!!.name.replaceFirstChar { it.titlecase(Locale.getDefault()) })

            val image = findViewById<ImageView>(R.id.imageView)


            rList = listUpd(poke)

            println("rList = " + rList)
            withContext(Dispatchers.Main){
                Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/ca5a7886c10753144e6fae3b69d45a4d42a449b4/sprites/pokemon/$id.png").into(image)
                adapter = CustomAdapter(rList)
                recyclerView!!.adapter = adapter
            }

        }


        val toggle = findViewById<ToggleButton>(R.id.toggleButton2)

        if(captured){
            toggle.isEnabled = false
        }


        val ids = dbHelper.getAllPoke()
        println("IUHUU GOT CATCH EM ALL: " + ids)

        if(toggle.isChecked && ids.find { it == id } == null){
            toggle.isChecked = false
        }
        else if(!toggle.isChecked && ids.find { it == id } != null){
            toggle.isChecked = true
        }

        toggle.setOnCheckedChangeListener{
            _, isChecked ->
            run {
                if (!isChecked) {
                        if(ids.find { it == id } != null){
                            dbHelper.deletePoke(id)
                            ids.remove(id)
                            println("SQL Confirm del: ")
                        }

                }
                else{
                        dbHelper.insertPoke(id)
                        ids.add(id)
                        println("SQL Confirmed: ")
                }
            }
        }

        val back = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        back.setOnClickListener{
            finish()
        }
    }


    suspend fun listUpd(poke: Pokemon) : ArrayList<ArrayList<String>>{
        val myList = ArrayList<ArrayList<String>>()
        val asyncUpd = async {
            val Labels = listOf("Poke IDX:", "Base Experience", "Height", "Weight", "Initial Pokemon?", "Types")
            val values = listOf(poke.id.toString(), poke.baseExperience.toString(), poke.height.toString(), poke.weight.toString(), if(poke.isDefault) "True" else "False", poke.types)
            for (i in 0..Labels.size-1){
                if(i == Labels.size-1){
                    println("Sfrev " + values[i])
                    val arr = values[i] as List<Magalmon>
                    for(j in 0..arr.size-1){
                        myList += arrayListOf("Type ${j+1}", arr[j].type.name)
                    }
                }
                else{
                    myList += arrayListOf(Labels[i], values[i].toString())
                }
            }
        }

        asyncUpd.await()
        return myList
    }

    suspend fun getPoke(pokeApi: PokeApi?, id: Int?): Pokemon? {
        val asyncPoke = async {
            pokeApi!!.getPokemon(id!!.toInt())
        }
        return asyncPoke.await().body()

    }
}





