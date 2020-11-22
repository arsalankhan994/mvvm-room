package com.cocooncreations.topstories.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.cocooncreations.topstories.R
import com.cocooncreations.topstories.constant.KeyConstant
import com.cocooncreations.topstories.data.model.ResultEntity
import com.cocooncreations.topstories.utils.DateUtil
import kotlinx.android.synthetic.main.activity_story_detail.*

class StoryDetailActivity : AppCompatActivity() {

    private lateinit var bundle: Bundle
    private lateinit var resultEntity: ResultEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)

        /*
        Get Data from intent to show story detail
        */
        bundle = intent.getBundleExtra(KeyConstant.BUNDLE_DATA)
        resultEntity  = bundle.getParcelable<ResultEntity>(KeyConstant.KEY_RESULT_DATA) as ResultEntity

        articleTitle.text = resultEntity.title
        publishDate.text = DateUtil.changeDateFormat(resultEntity.publishedDate)
        description.text = resultEntity.abstractString
        articleLink.text = resultEntity.url

        Glide.with(this)
            .load(resultEntity.multimedia?.get(0)?.url)
            .placeholder(R.drawable.ic_dashboard_black_24dp)
            .into(articleImage)
    }
}