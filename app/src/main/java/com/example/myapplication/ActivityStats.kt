package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import androidx.fragment.app.Fragment

class ActivityStats : AppCompatActivity(){
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)


        val actionBar = supportActionBar
        actionBar?.hide()

        val imgHomeTeam = findViewById<ImageView>(R.id.imgHomeTeam)
        val imgVisitorTeam = findViewById<ImageView>(R.id.imgVisitorTeam)


        val extraAbvHome = intent.getStringExtra("EXTRA_ABV_HOME")
        val extraAbvVisitor = intent.getStringExtra("EXTRA_ABV_VISITOR")
        val extraScoreHome = intent.getIntExtra("EXTRA_SCORE_HOME",1)
        val extraScoreVisitor = intent.getIntExtra("EXTRA_SCORE_VISITOR",1)
        val extraId = intent.getIntExtra("EXTRA_ID",1)


        if (extraAbvHome != null) {
            Log.w("extraAbvHome", extraAbvHome)
        }




        val txtScoreHome = findViewById<TextView>(R.id.txtHomeTeam).apply {
            text = extraScoreHome.toString()
        }

        val textScoreVisitor = findViewById<TextView>(R.id.txtVisitorTeam).apply {
            text = extraScoreVisitor.toString()
        }


        //ressource home team
        val con = applicationContext
        val id = con.resources.getIdentifier(extraAbvHome?.toLowerCase(),"drawable",con.packageName)
        imgHomeTeam.setImageResource(id)

        //ressource visitor team

        val id_visitor = con.resources.getIdentifier(extraAbvVisitor?.toLowerCase(),"drawable",con.packageName)
        imgVisitorTeam.setImageResource(id_visitor)

        val mRecyclerView = findViewById<RecyclerView>(R.id.rwStats)

        doAsync {
            val serviceGenerator = ServiceGenerator.buildService(ApiServiceStats::class.java)
            val call = serviceGenerator.getStats(extraId)
            val rwStats : RecyclerView = findViewById(R.id.rwStats)




            call.enqueue(object : Callback<ListPostStats> {
                override fun onResponse(
                        call: Call<ListPostStats>,
                        response: Response<ListPostStats>
                ) {
                    if (response.isSuccessful) {
                        val catch = response.body()
                        val getcatch = catch?.getData()?.toList()
                        val json = GsonBuilder().setPrettyPrinting().create().toJson(getcatch)




                        val objTeams = Gson().fromJson(json, Array<PostStats>::class.java).toList()

                        uiThread {
                            objTeams.forEach {
                                Log.d("PLAYERSTATS","PLAYER ${it.reb} got ${it.fgPct} points ")
                            }
                        }
                            Log.d("DONE","DONE")
                        mRecyclerView.apply {
                            layoutManager = LinearLayoutManager(this@ActivityStats)

                            adapter = PostAdapterStats(objTeams!!)
                        }




                    }
                }

                override fun onFailure(call: Call<ListPostStats>, t: Throwable) {
                    t.printStackTrace()
                    Log.w("error", t.message.toString())
                }



            })


        }




        intent.putExtra("EXTRA_MESSAGE_ID",extraId)
        setResult(Activity.RESULT_OK, intent)






                }




            }







