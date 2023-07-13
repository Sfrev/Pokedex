package ufscar.dc.Pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        val completePokedexButtonClick = findViewById<Button>(R.id.button_pokedex_completa)
        completePokedexButtonClick.setOnClickListener{
            val intent = Intent(this, Sfrevao::class.java)
            startActivity(intent)
        }
        val capturedPokensButtonClick = findViewById<Button>(R.id.button_pokemons_capturados)
        capturedPokensButtonClick.setOnClickListener{
            val intent = Intent(this, PokemonScreen::class.java)
            startActivity(intent)
        }
    }
}