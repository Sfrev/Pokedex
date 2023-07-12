package ufscar.dc.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import ufscar.dc.Pokedex.MainActivity
import ufscar.dc.Pokedex.PokemonScreen
import ufscar.dc.Pokedex.R

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        val instructionsButtonClick = findViewById<Button>(R.id.button_instrucoes)
        instructionsButtonClick.setOnClickListener{
            //val intent = Intent(this, Instrucoes::class.java)
            //startActivity(intent)
        }
        val completePokedexButtonClick = findViewById<Button>(R.id.button_pokedex_completa)
        instructionsButtonClick.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val capturedPokensButtonClick = findViewById<Button>(R.id.button_pokemons_capturados)
        instructionsButtonClick.setOnClickListener{
            val intent = Intent(this, PokemonScreen::class.java)
            startActivity(intent)
        }
    }
}