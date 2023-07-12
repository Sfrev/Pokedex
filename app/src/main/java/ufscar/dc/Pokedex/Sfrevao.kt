package ufscar.dc.Pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import ufscar.dc.Pokedex.ui.theme.PokedexTheme
import java.util.Locale

class Sfrevao : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val pokeApi = getPokeApi(this.applicationContext).create(PokeApi::class.java)

        val imageLoader = ImageLoader.Builder(this.applicationContext)
            .diskCache {
                DiskCache.Builder()
                    .directory(this.applicationContext.cacheDir.resolve("image_cache"))
                    .build()
            }
            .build()

        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(pokeApi, imageLoader)
                }
            }
        }
    }
}

const val PAGE_SIZE = 40

@Composable
fun Greeting(pokeApi: PokeApi?, imageLoader: ImageLoader?) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "PokemonMassa",
            fontFamily = FontFamily(Font(R.font.pokemon_hollow)),
            fontSize = 48.sp,
            modifier = Modifier.padding(top = 20.dp),
        )

        var page by remember { mutableStateOf(0) }

        var pokeList by remember { mutableStateOf<PokemonList?>(null) }
        if (pokeApi != null) {
            LaunchedEffect(page) {
                val response = pokeApi.getPokemonList(page * PAGE_SIZE, PAGE_SIZE)
                pokeList = response.body()
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(horizontal = 15.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Image(
                    ImageBitmap.imageResource(id = R.drawable.img),
                    "",
                    modifier = Modifier.padding(all = 15.dp),
                )
            }

            items(PAGE_SIZE) { item ->
                Column(
                    modifier = Modifier.padding(all = 3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imageLoader != null) {
                        val spriteId = 1 + page * PAGE_SIZE + item
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/PokeAPI/sprites/ca5a7886c10753144e6fae3b69d45a4d42a449b4/sprites/pokemon/$spriteId.png",
                            contentDescription = "",
                            imageLoader = imageLoader,
                            placeholder = painterResource(id = R.drawable.creature_placeholder),
                        )
                    } else {
                        Image(ImageBitmap.imageResource(id = R.drawable.img), "")
                    }

                    if (pokeApi != null) {
                        if (pokeList != null) {
                            val name = pokeList!!.results[item].name
                            Text(name.replaceFirstChar { it.titlecase(Locale.getDefault()) })
                        } else {
                            Text("??????????")
                        }
                    } else {
                        Text("Jesus ${page * PAGE_SIZE + item}")
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { page = maxOf(page - 1, 0) }) {
                        Icon(Icons.Default.ArrowBack, "")
                    }
                    Text(page.toString(), fontSize = 24.sp)
                    IconButton(onClick = { page++ }) {
                        Icon(Icons.Default.ArrowForward, "")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexTheme {
        Greeting(null, null)
    }
}
