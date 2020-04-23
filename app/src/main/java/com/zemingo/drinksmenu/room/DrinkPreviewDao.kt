package com.zemingo.drinksmenu.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zemingo.drinksmenu.models.DrinkPreviewModel
import com.zemingo.drinksmenu.models.PreviousSearchModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkPreviewDao {

    @Query("SELECT * FROM DrinkPreviewModel")
    fun getAll(): Flow<List<DrinkPreviewModel>>

//    @Query("SELECT * FROM DrinkPreviewModel")
//    fun getByCategory(): Flow<List<DrinkPreviewModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(drinkPreviews: List<DrinkPreviewModel>)


    /*
    @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
    fun loadUsersFromRegions(regions: List<String>): List<NameTuple>
    */
    @Query("SELECT * FROM DrinkPreviewModel WHERE id IN (:ids)")
    fun getByIds(ids: List<String>): Flow<List<DrinkPreviewModel>>

    /*@Query("SELECT * FROM DrinkPreviewModel INNER JOIN PreviousSearchModel ON id = drinkId")
    fun getPreviousSearches(): Flow<List<DrinkPreviewModel>>
    */
    @Query("SELECT * FROM DrinkPreviewModel INNER JOIN PreviousSearchModel ON id = drinkId")
    fun getPreviousSearches(): Flow<List<DrinkPreviewModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchModel: PreviousSearchModel)
}