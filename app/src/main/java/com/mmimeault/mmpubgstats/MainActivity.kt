package com.mmimeault.mmpubgstats

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.widget.TextView
import com.mmimeault.mmpubgstats.data.stats.Stats
import com.mmimeault.mmpubgstats.data.user.User
import com.mmimeault.mmpubgstats.data.user.Users
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var service: PubgUserService

    private lateinit var tvWins: TextView
    private lateinit var tvLosses: TextView
    private lateinit var tvRatio: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvWins = findViewById(R.id.wins)
        tvLosses = findViewById(R.id.losses)
        tvRatio = findViewById(R.id.winratio)

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain?): okhttp3.Response {
                val request = chain!!.request().newBuilder()
                        .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0MTUxYzE0MC00MDIyLTAxMzYtZDcwMC0wNjNhNDEwOWIyOWIiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTI3MDE2NDYxLCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6InRlY2hlYW4iLCJzY29wZSI6ImNvbW11bml0eSIsImxpbWl0IjoxMH0.qBrzz999jWM_iYpD-VdFxuOD_HMjUsxdRzIfs4l5e0Q")
                        .addHeader("accept", "application/vnd.api+json")
                        .build()



                return chain.proceed(request)
            }


        })


        retrofit = Retrofit.Builder()
                .baseUrl("https://api.playbattlegrounds.com/shards/pc-na/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

        service = retrofit.create(PubgUserService::class.java)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false) // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("MICMIC", "query submit $query")
                searchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    fun searchUser(query: String) {
        service.search(query).enqueue(object : Callback<Users> {
            override fun onFailure(call: Call<Users>?, t: Throwable?) {
                Log.e("MICMIC", "query failed", t)
            }

            override fun onResponse(call: Call<Users>?, response: Response<Users>?) {
                Log.d("MICMIC", "query success ${response?.body()}")
                Log.d("MICMIC", "query code ${response?.code()}")
                searchUserStats(response!!.body()!!.users[0])
            }
        })
    }

    fun searchUserStats(user: User) {
        service.stats(user.id, "division.bro.official.2018-06").enqueue(object : Callback<Stats> {
            override fun onFailure(call: Call<Stats>?, t: Throwable?) {
                Log.e("MICMIC", "query failed", t)
            }

            override fun onResponse(call: Call<Stats>?, response: Response<Stats>?) {
                Log.d("MICMIC", "query success ${response?.body()}")
                Log.d("MICMIC", "query code ${response?.code()}")

                val gameModeStats = response!!.body()?.data?.attributes?.gameModeStats
                val wins: Double = gameModeStats!!.soloFpp?.wins!!.toDouble()
                val losses: Double = gameModeStats.soloFpp?.losses!!.toDouble()
                runOnUiThread {
                    tvWins.setText("wins: " + wins)
                    tvLosses.setText("losses: " + losses)
                    tvRatio.setText("ratio: " + (wins / (wins + losses)))
                }
            }
        })
    }
}
