package by.zhenyabigel.features.authentication.domain.repository

import by.zhenyabigel.features.authentication.data.model.DishModel

interface DishRepository {

    suspend fun addDish(dish: DishModel)

    suspend fun getAllDishes(): List<DishModel>

    suspend fun updateDish(dish: DishModel, dishId: Int)

    suspend fun deleteDish(dishId: Int)
}