package com.yanivsos.mixological.repo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yanivsos.mixological.domain.models.IngredientDetailsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDetailsDao {

    @Query("SELECT * FROM ingredient_details")
    fun getAll(): Flow<List<IngredientDetailsModel>>

    @Query("SELECT * FROM ingredient_details WHERE name LIKE :name")
    fun getByName(name: String): Flow<List<IngredientDetailsModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun storeAll(ingredientDetails: List<IngredientDetailsModel>)
}