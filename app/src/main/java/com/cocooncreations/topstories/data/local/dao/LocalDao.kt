package com.cocooncreations.topstories.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cocooncreations.topstories.data.model.ResultEntity

@Dao
interface LocalDao {

    @Insert
    suspend fun insert(resultEntity: ResultEntity)

    @Update
    suspend fun update(resultEntity: ResultEntity)

    @Delete
    suspend fun delete(resultEntity: ResultEntity)

    @Query("DELETE FROM bookmark")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM bookmark WHERE url = :url)")
    suspend fun isRowIsExist(url : String) : Boolean


    @Query(value = "Select * from bookmark")
    fun getAllBookMark(): List<ResultEntity>
}