package ufscar.dc.Pokedex

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class PokemonListEntry(
    val name: String,
    val url: String,
)

data class PokemonList(
    val count: Int,
    val results: List<PokemonListEntry>,
)

data class Pokemon(
    val id: Int,
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val isDefault: Boolean,
    val weight: Int,
)

interface PokeApi {
    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Response<Pokemon>

    @GET("api/v2/pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<PokemonList>
}

fun getPokeApi(context: Context): Retrofit {
    val cacheSize: Long = 10 * 1024 * 1024
    val cache = Cache(context.cacheDir, cacheSize)

    val okHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .build()

    val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    return Retrofit.Builder()
        .baseUrl("https://pokeapi.co/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}
