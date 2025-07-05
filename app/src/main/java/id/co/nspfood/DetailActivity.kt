package id.co.nspfood

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val recipe = intent.getParcelableExtra<Recipe>("RECIPE")!!


        val img = findViewById<ImageView>(R.id.imgDetail)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvCuisine = findViewById<TextView>(R.id.tvCuisine)
        val tvInstructions = findViewById<TextView>(R.id.tvInstructions)
        val tvIngredients = findViewById<TextView>(R.id.tvIngredients)

        Glide.with(this).load(recipe.image).into(img)
        tvTitle.text = recipe.name
        tvCuisine.text = "Cuisine: ${recipe.cuisine}"
        tvInstructions.text = recipe.instructions.joinToString("\n")
        tvIngredients.text = "Ingredients:\n" + recipe.ingredients.joinToString("\n")

    }
}
