package com.cocooncreations.topstories.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cocooncreations.topstories.R
import com.cocooncreations.topstories.adapter.TopStoriesAdapter
import com.cocooncreations.topstories.data.model.StoriesEntity
import com.cocooncreations.topstories.remote.servicebuilder.ServiceBuilder
import com.cocooncreations.topstories.remote.services.NetworkService
import com.cocooncreations.topstories.ui.ViewModelFactory
import com.cocooncreations.topstories.utils.Status
import kotlinx.android.synthetic.main.fragment_top_stories.*

class TopStoriesFragment : Fragment() {

    private lateinit var viewModel: TopStoriesViewModel
    private lateinit var adapter: TopStoriesAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        setUpObserver()
        val root = inflater.inflate(R.layout.fragment_top_stories, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TopStoriesAdapter(this.requireContext())
        recyclerView.layoutManager =
                LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
                ViewModelFactory(ServiceBuilder.buildService(NetworkService::class.java))).get(TopStoriesViewModel::class.java)
    }

    private fun setUpObserver() {
        viewModel.getTopStories().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { topStories -> updateList(topStories) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }

            }
        })
    }

    private fun updateList(topStories: StoriesEntity) {
        adapter.submitList(topStories.resultEntities)
    }
}