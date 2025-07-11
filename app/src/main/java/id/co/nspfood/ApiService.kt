package id.co.nspfood

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("recipes")
    fun getRecipes(): Call<RecipeResponse>
}
