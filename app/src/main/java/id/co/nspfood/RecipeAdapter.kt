package id.co.nspfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipeAdapter(
    private var recipeList: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgRecipe: ImageView = itemView.findViewById(R.id.imgRecipe)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvCuisine: TextView = itemView.findViewById(R.id.tvCuisine)
        val tvDifficulty: TextView = itemView.findViewById(R.id.tvDifficulty)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating) // Tambahan
        val btnDetail: Button = itemView.findViewById(R.id.btnDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]

        // Load gambar
        Glide.with(holder.itemView.context)
            .load(recipe.image)
            .into(holder.imgRecipe)

        // Isi teks
        holder.tvName.text = recipe.name
        holder.tvCuisine.text = recipe.cuisine
        holder.tvDifficulty.text = recipe.difficulty
        holder.tvRating.text = recipe.rating?.toString() ?: "-"


        // Aksi tombol detail
        holder.btnDetail.setOnClickListener {
            onItemClick(recipe)
        }
    }

    override fun getItemCount(): Int = recipeList.size

    fun updateData(newRecipes: List<Recipe>) {
        recipeList = newRecipes
        notifyDataSetChanged()
    }
}
