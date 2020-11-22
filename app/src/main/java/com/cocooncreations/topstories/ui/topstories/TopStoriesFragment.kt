package com.cocooncreations.topstories.ui.topstories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cocooncreations.topstories.R
import com.cocooncreations.topstories.adapter.TopStoriesAdapter
import com.cocooncreations.topstories.constant.KeyConstant
import com.cocooncreations.topstories.data.model.ResultEntity
import com.cocooncreations.topstories.data.model.StoriesEntity
import com.cocooncreations.topstories.delegate.ItemClickDelegate
import com.cocooncreations.topstories.remote.servicebuilder.ServiceBuilder
import com.cocooncreations.topstories.remote.services.NetworkService
import com.cocooncreations.topstories.ui.activity.StoryDetailActivity
import com.cocooncreations.topstories.utils.NetworkConnectionUtil
import com.cocooncreations.topstories.utils.RecyclerItemClickListener
import com.cocooncreations.topstories.utils.Status
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.fragment_top_stories.*
import kotlinx.android.synthetic.main.fragment_top_stories.noDataFoundText
import kotlinx.android.synthetic.main.fragment_top_stories.recyclerView
import kotlinx.android.synthetic.main.fragment_top_stories.shimmerLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TopStoriesFragment : Fragment(), ItemClickDelegate {

    private lateinit var viewModel: TopStoriesViewModel
    private lateinit var adapter: TopStoriesAdapter
    private lateinit var resultEntityList: List<ResultEntity>

    /*
    Initializing viewmodel object and return view against this fragment
    */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        val root = inflater.inflate(R.layout.fragment_top_stories, container, false)
        return root
    }

    /*
    Getting data from database on onResume so we get the update data when come's from background for change fragment
    */
    override fun onResume() {
        super.onResume()
        showShimmer()
        setUpObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        load data again
        */
        reloadData.setOnClickListener {
            if (NetworkConnectionUtil.isNetworkConnected(requireContext())) {
                showShimmer()
                setUpObserver()
            } else {
                Toast.makeText(context, requireContext().getString(R.string.no_internet_message), Toast.LENGTH_LONG).show()
            }
        }

        /*
        Here we are creating adapter object and passing context and listener for item click
        */
        adapter = TopStoriesAdapter(this.requireContext(), this)
        recyclerView.layoutManager =
                LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        /*
        * Here we are getting the item position incase of long click
        */
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(activity, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                openStoryDetailActivity(resultEntityList.get(position))
            }

            override fun onItemLongClick(view: View?, position: Int) {
                showConfirmationDialog(resultEntityList.get(position))
            }
        }))
    }

    /*
    Here we are showing alert dialog for confirmation before bookmark
    */
    private fun showConfirmationDialog(resultEntity: ResultEntity) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setPositiveButton(getString(R.string.text_yes), { dialog, whichButton ->
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.addItemOnBookmark(resultEntity)
            }
            dialog.dismiss()
        })

        alertDialogBuilder.setNegativeButton(getString(R.string.text_no), { dialog, which ->
            dialog.dismiss()
        })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.setTitle(getString(R.string.title_confirmation))
        alertDialog.setMessage(getString(R.string.bookmark_confirmation_message))
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    /*
    Creating viewMode object for doing our business logic inside that class
    */
    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
                TopStoriesViewModelFactory(ServiceBuilder.buildService(NetworkService::class.java), this.requireContext())).get(TopStoriesViewModel::class.java)
    }

    /*
    Here we are observing our booklist to get the updated data
    show list on success
    show toast on error
    */
    private fun setUpObserver() {
        viewModel.getTopStories().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideShimmer()
                        resource.data?.let { topStories -> updateList(topStories) }
                    }
                    Status.ERROR -> {
                        hideShimmer()
                        recyclerView.visibility = View.GONE
                        noDataFoundText.visibility = View.VISIBLE
                        reloadData.visibility = View.VISIBLE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    /*
    Here we are updating our adapter and also update the resultEntityList list
    so we can get the data when use press long click for bookmark
    */
    private fun updateList(topStories: StoriesEntity) {
        resultEntityList = topStories.resultEntities!!
        if (resultEntityList.size == 0) {
            recyclerView.visibility = View.GONE
            noDataFoundText.visibility = View.VISIBLE
            reloadData.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            noDataFoundText.visibility = View.GONE
            reloadData.visibility = View.GONE
            adapter.submitList(topStories.resultEntities)
        }
    }

    private fun hideShimmer() {
        shimmerLayout.visibility = View.GONE
        shimmerLayout.stopShimmer()
    }

    private fun showShimmer() {
        noDataFoundText.visibility = View.GONE
        reloadData.visibility = View.GONE
        shimmerLayout.visibility = View.VISIBLE
        shimmerLayout.startShimmer()
    }

    /*
    Callback on item click
    */
    override fun onClick(any: Any) {
        val resultEntity = any as ResultEntity
        openStoryDetailActivity(resultEntity)
    }

    /*
    Here we are opening our story detail activity and passing our story data
    as we cannot pass custom inside bundle that's why we are using parcelable and implement parcelable to our result entity
    */
    private fun openStoryDetailActivity(resultEntity: ResultEntity) {
        val intent = Intent(requireContext(), StoryDetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(KeyConstant.KEY_RESULT_DATA, resultEntity)
        intent.putExtra(KeyConstant.BUNDLE_DATA, bundle)
        startActivity(intent)
    }
}