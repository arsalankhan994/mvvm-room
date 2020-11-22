package com.cocooncreations.topstories.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cocooncreations.topstories.R
import com.cocooncreations.topstories.adapter.TopStoriesAdapter
import com.cocooncreations.topstories.constant.KeyConstant
import com.cocooncreations.topstories.data.model.ResultEntity
import com.cocooncreations.topstories.delegate.ItemClickDelegate
import com.cocooncreations.topstories.ui.activity.StoryDetailActivity
import com.cocooncreations.topstories.utils.NetworkConnectionUtil
import com.cocooncreations.topstories.utils.Status
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.fragment_bookmark.noDataFoundText
import kotlinx.android.synthetic.main.fragment_top_stories.recyclerView

class BookmarkFragment : Fragment(), ItemClickDelegate {

    private lateinit var viewModel: BookmarkViewModel
    private lateinit var adapter: TopStoriesAdapter

    /*
    Initializing viewmodel object and return view against this fragment
    */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        val root = inflater.inflate(R.layout.fragment_bookmark, container, false)
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
        Here we are creating adapter object and passing context and listener for item click
        */
        adapter = TopStoriesAdapter(this.requireContext(), this)
        recyclerView.layoutManager =
            GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    /*
    Creating viewMode object for doing our business logic inside that class
    */
    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            BookmarkViewModelFactory(this.requireContext())
        ).get(BookmarkViewModel::class.java)
    }

    /*
    Here we are observing our booklist to get the updated data
    show list on success
    show toast on error
    */
    private fun setUpObserver() {
        viewModel.getBookmarkList().observe(viewLifecycleOwner, Observer {
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
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }

            }
        })
    }

    private fun hideShimmer() {
        shimmerLayout.visibility = View.GONE
        shimmerLayout.stopShimmer()
    }

    private fun showShimmer() {
        shimmerLayout.visibility = View.VISIBLE
        shimmerLayout.startShimmer()
    }

    /*
    Updating our adapter
    */
    private fun updateList(bookmarkList: List<ResultEntity>) {
        if (bookmarkList.size == 0) {
            recyclerView.visibility = View.GONE
            noDataFoundText.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            noDataFoundText.visibility = View.GONE
            adapter.submitList(bookmarkList)
        }
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

    /*
    callback on item click
    */
    override fun onClick(any: Any) {
        val resultEntity = any as ResultEntity
        openStoryDetailActivity(resultEntity)
    }
}