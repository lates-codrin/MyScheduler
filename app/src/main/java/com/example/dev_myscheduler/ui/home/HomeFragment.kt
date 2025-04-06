package com.example.dev_myscheduler.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev_myscheduler.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Callback
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchNews()
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun fetchNews() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://schedulemonitor.onrender.com/news")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { body ->
                    try {
                        val jsonArray = JSONArray(body)
                        val articles = mutableListOf<Article>()

                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            articles.add(
                                Article(
                                    articleTitle = obj.getString("articleTitle"),
                                    articleDescription = obj.getString("articleDescription"),
                                    articleDate = obj.getString("articleDate"),
                                    articleLink = obj.getString("articleLink"),
                                    articleImage = obj.optString("articleImage", null)
                                )
                            )
                        }

                        activity?.runOnUiThread {
                            // Set the adapter with the articles data
                            binding.newsRecyclerView.adapter = NewsAdapter(articles) { url ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                startActivity(intent)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
