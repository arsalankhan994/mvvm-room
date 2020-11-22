package com.cocooncreations.topstories.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cocooncreations.topstories.data.model.ResultEntity

@Dao
interface LocalDao {

    @Insert
    suspend fun insert(contact: ResultEntity)

    @Update
    suspend fun update(contact: ResultEntity)

    @Delete
    suspend fun delete(contact: ResultEntity)

    @Query("DELETE FROM bookmark")
    fun deleteAll()

    @Query(value = "Select * from bookmark")
    fun getAllBookMark(): LiveData<List<ResultEntity>>
}