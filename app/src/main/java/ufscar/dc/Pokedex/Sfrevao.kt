package ufscar.dc.Pokedex

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FilledIconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
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
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Greeting(pokeApi, imageLoader)
                }
            }
        }
    }

val PAGE_SIZE = 40

@Composable
fun CardTitle(pokemonName: String) {
    Text(
        pokemonName,
        color = MaterialTheme.colorScheme.onSurface,
        fontFamily = FontFamily(Font(R.font.pokemon_solid)),
        fontSize = 16.sp,
        maxLines = 1,
    )
}

@Composable
fun CardTypeColumnItem(ty: String) {
    Surface(
        Modifier.padding(2.dp).defaultMinSize(50.dp),
        RoundedCornerShape(4.dp),
        tonalElevation = 2.dp,
    ) {
        Text(
            ty,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CardTypeColumn(pokemon: Pokemon?) {
    if (pokemon != null) {
        LazyColumn() {
            items(pokemon.types) { type ->
                CardTypeColumnItem(type.type.name.replaceFirstChar { it.titlecase() })
            }
        }
    } else {
        Column {}
    }
}

@Composable
fun CardImage(page: Int, item: Int, imageLoader: ImageLoader?) {
    if (imageLoader != null) {
        val spriteId = 1 + page * PAGE_SIZE + item
        AsyncImage(
            model = "https://raw.githubusercontent.com/PokeAPI/sprites/ca5a7886c10753144e6fae3b69d45a4d42a449b4/sprites/pokemon/$spriteId.png",
            contentDescription = "",
            imageLoader = imageLoader,
            placeholder = painterResource(id = R.drawable.creature_placeholder),
            modifier = Modifier.size(80.dp),
        )
    } else {
        Image(
            ImageBitmap.imageResource(id = R.drawable.creature_placeholder),
            contentDescription = "",
            modifier = Modifier.size(80.dp),
        )
    }
}

@Composable
fun ListedPokemonCard(
    pokeList: PokemonList?,
    page: Int,
    item: Int,
    pokeApi: PokeApi?,
    imageLoader: ImageLoader?,
) {
    val context = LocalContext.current

    Surface(
        Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                val intent = Intent(context, PokemonScreen::class.java)
                intent.putExtra("id", 1 + page * PAGE_SIZE + item)
                context.startActivity(intent)
            },
        RoundedCornerShape(12.dp),
        tonalElevation = 1.dp,
    ) {
        Column(Modifier.padding(8.dp)) {
            if (pokeApi != null) {
                if (pokeList != null) {
                    val name = pokeList.results[item].name
                    CardTitle(name.replaceFirstChar { it.titlecase(Locale.getDefault()) })
                } else {
                    CardTitle("??????????")
                }
            } else {
                CardTitle("Jesus ${page * PAGE_SIZE + item}")
            }

            Row(
                Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                var pokemon by remember { mutableStateOf<Pokemon?>(null) }

                CardTypeColumn(pokemon)

                if (pokeApi != null) {
                    LaunchedEffect(Unit) {
                        pokemon = pokeApi.getPokemon(1 + page * PAGE_SIZE + item).body()
                    }
                } else {
                    Column {
                        CardTypeColumnItem("Jesus1")
                        CardTypeColumnItem("Jesus2")
                    }
                }

                Box {
                    Surface(tonalElevation = 2.dp, shape = RoundedCornerShape(12.dp)) {
                        CardImage(page, item, imageLoader)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(pokeApi: PokeApi?, imageLoader: ImageLoader?) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val context = LocalContext.current

        Row(
            Modifier
                .padding(10.dp)
                .fillMaxWidth(), Arrangement.Start) {
            FilledIconButton(onClick = { (context as? Activity)?.finish() }) {
                Icon(Icons.Default.ArrowBack, "")
            }
        }

        var page by remember { mutableStateOf(0) }

        var pokeList by remember { mutableStateOf<PokemonList?>(null) }
        if (pokeApi != null) {
            LaunchedEffect(page) {
                val response = pokeApi.getPokemonList(page * PAGE_SIZE, PAGE_SIZE)
                pokeList = response.body()
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 15.dp),
        ) {
            items(PAGE_SIZE) { item ->
                ListedPokemonCard(pokeList, page, item, pokeApi, imageLoader)
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
}
