package com.example.newsly

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import kotlin.random.Random
import android.graphics.Color
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.newsly.adapter.NewsAdapter
import com.example.newsly.api.NewsAPI
import com.example.newsly.api.RetrofitHelper
import com.example.newsly.models.Article
import com.example.newsly.models.News
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var articles = mutableListOf<Article>()
    private lateinit var adapter: NewsAdapter
    var pageNum = 1
    var totalResults = -1
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        adapter = NewsAdapter(this, articles)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        val manager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL)
        manager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var currentPosition = -1

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition = (recyclerView.layoutManager as CarouselLayoutManager)
                    .centerItemPosition
                if(totalResults > recyclerView.layoutManager!!.itemCount && firstVisibleItemPosition >= ((recyclerView.layoutManager as CarouselLayoutManager).itemCount - 5)){
                    pageNum++
                    getNews()
                }
                if (currentPosition != firstVisibleItemPosition) {
                    currentPosition = firstVisibleItemPosition
                    recyclerView.setBackgroundColor(getRandomColor())
                }
            }
        })

        getNews()
    }

    private fun getNews() {
        val retrofit = RetrofitHelper.getInstance().create(NewsAPI::class.java)
        val news = retrofit.getTopHeadlines("in", pageNum)
        news.enqueue(object : Callback<News?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                val news = response.body()
                if (news != null) {
                    totalResults = news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<News?>, t: Throwable) {
                Log.d("TAG", "Error in fetching news", t)
            }
        })
    }

    private fun getRandomColor(): Int {
        val random = Random.Default
        return Color.argb(
            255, // Alpha value
            random.nextInt(256), // Red value
            random.nextInt(256), // Green value
            random.nextInt(256) // Blue value
        )
    }
}