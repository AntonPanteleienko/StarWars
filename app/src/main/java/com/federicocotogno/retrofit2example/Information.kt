package com.federicocotogno.retrofit2example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_information.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

//const val BASE_URLL = " https://swapi.dev/"
//val TAGG = "MainActivity"

class Information : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_Navigation_View)
//        val navController = findNavController(R.id.fragment)
//        bottomNavigationView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.searchFragment, R.id.favoriteFragment, R.id.helpFragment)
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)

        getCurrentData()

        bottom_Navigation_View1.setOnClickListener {
            getCurrentData()
        }
    }

    private fun getCurrentData() {
        name.visibility = View.GONE
        birth_year.visibility = View.GONE
        edited.visibility = View.GONE
        eye_color.visibility = View.GONE
        films.visibility = View.GONE
        gender.visibility = View.GONE
        hair_color.visibility = View.GONE
        homeworld.visibility = View.GONE
        skin_color.visibility = View.GONE
        species.visibility = View.GONE
        starships.visibility = View.GONE
        vehicles.visibility = View.GONE
//        progressBar.visibility = View.VISIBLE

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getPerson().awaitResponse()
                if (response.isSuccessful) {

                    val data = response.body()!!
                    Log.d(TAG, data.toString())

                    withContext(Dispatchers.Main) {
                        name.visibility = View.VISIBLE
                        birth_year.visibility = View.VISIBLE
                        edited.visibility = View.VISIBLE
                        eye_color.visibility = View.VISIBLE
                        films.visibility = View.VISIBLE
                        gender.visibility = View.VISIBLE
                        hair_color.visibility = View.VISIBLE
                        homeworld.visibility = View.VISIBLE
                        skin_color.visibility = View.VISIBLE
                        species.visibility = View.VISIBLE
                        starships.visibility = View.VISIBLE
                        vehicles.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE

                        name.text = data.name
                        birth_year.text = data.birth_year
                        edited.text = data.edited
                        eye_color.text = data.eye_color
                        films.text = data.films.toString()
                        gender.text = data.gender
                        hair_color.text = data.hair_color
                        homeworld.text = data.homeworld
                        skin_color.text = data.skin_color
                        species.text = data.species.toString()
                        starships.text = data.starships.toString()
                        vehicles.text = data.vehicles.toString()


                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "Seems like something went wrong...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

