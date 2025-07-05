package id.co.nspfood

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private val originalList = mutableListOf<Recipe>() // Simpan data asli

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etSearch = findViewById<EditText>(R.id.etSearch)
        recyclerView = findViewById(R.id.rvRecipes)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        adapter = RecipeAdapter(emptyList()) { recipe ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("RECIPE", recipe)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Panggil API
        ApiClient.instance.getRecipes().enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                val recipes = response.body()?.recipes ?: emptyList()
                originalList.clear()
                originalList.addAll(recipes)
                adapter.updateData(recipes)
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })

        // Filter realtime
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString().lowercase()
                val filtered = originalList.filter {
                    it.name.lowercase().contains(keyword)
                }
                adapter.updateData(filtered)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val btnFilter = findViewById<Button>(R.id.btnSearch) // yang sudah diganti jadi "Filter"
        btnFilter.setOnClickListener {
            showFilterDialog(originalList)
        }

    }
    private fun showFilterDialog(originalList: List<Recipe>) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_filter, null)
        val spinnerDifficulty = dialogView.findViewById<Spinner>(R.id.spinnerDifficulty)
        val spinnerCuisine = dialogView.findViewById<Spinner>(R.id.spinnerCuisine)

        val difficulties = listOf("All", "Easy", "Medium", "Hard")
        val cuisines = listOf("All", "Italian", "French", "Indian", "Japanese", "American") // Sesuaikan dengan data asli

        spinnerDifficulty.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, difficulties)
        spinnerCuisine.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cuisines)

        AlertDialog.Builder(this)
            .setTitle("Filter Recipes")
            .setView(dialogView)
            .setPositiveButton("Terapkan") { _, _ ->
                val selectedDifficulty = spinnerDifficulty.selectedItem.toString()
                val selectedCuisine = spinnerCuisine.selectedItem.toString()

                val filtered = originalList.filter { recipe ->
                    (selectedDifficulty == "All" || recipe.difficulty.equals(selectedDifficulty, true)) &&
                            (selectedCuisine == "All" || recipe.cuisine.equals(selectedCuisine, true))
                }
                adapter.updateData(filtered)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

}
