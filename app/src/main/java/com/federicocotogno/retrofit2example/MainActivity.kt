package com.federicocotogno.retrofit2example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import com.federicocotogno.retrofit2example.R.id.helpFragment as helpFragment1
import android.content.Intent





const val BASE_URL = " https://swapi.dev/"
val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_Navigation_View)
        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.searchFragment, R.id.favoriteFragment, helpFragment1)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        getCurrentData()

        button.setOnClickListener{
            val intent = Intent(this, Information::class.java)
            startActivity(intent)
        }

        bottom_Navigation_View.setOnClickListener {
            getCurrentData()
        }
    }

    private fun getCurrentData() {
        textName.visibility = View.GONE
        textYear.visibility = View.GONE
        textGender.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

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
                        textName.visibility = View.VISIBLE
                        textYear.visibility = View.VISIBLE
                        textGender.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE

                        textName.text = data.name
                        textYear.text = data.birth_year
                        textGender.text = data.gender
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