package com.zemingo.cocktailmenu.repo.mappers

import com.zemingo.cocktailmenu.models.CategoryEntity
import com.zemingo.cocktailmenu.models.CategoryResponse
import com.zemingo.cocktailmenu.models.DrinksWrapperResponse
import java.util.function.Function

class CategoryMapper : Function<DrinksWrapperResponse<CategoryResponse>, List<CategoryEntity>> {
    override fun apply(t: DrinksWrapperResponse<CategoryResponse>): List<CategoryEntity> {
        return t.data.map {
            CategoryEntity(
                name = it.category
            )
        }
    }
}