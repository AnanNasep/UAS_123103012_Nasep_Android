package id.co.nspfood

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val name: String,
    val image: String,
    val cuisine: String,
    val difficulty: String,
    val rating: Float,
    val instructions: List<String>,
    val ingredients: List<String>
) : Parcelable



data class RecipeResponse(
    val recipes: List<Recipe>
)
