package by.zhenyabigel.features.authentication.domain.usecase

import by.zhenyabigel.features.authentication.data.model.DishModel
import by.zhenyabigel.features.authentication.domain.repository.DishRepository

class DishUseCase(
    private val dishRepository: DishRepository
) {

    suspend fun addDish(dish: DishModel) {
        dishRepository.addDish(dish = dish)
    }

    suspend fun getAllDishes(): List<DishModel> {
        return dishRepository.getAllDishes()
    }

    suspend fun updateDish(dish: DishModel, dishId: Int) {
        dishRepository.updateDish(dish = dish, dishId = dishId)
    }

    suspend fun deleteCard(dishId: Int) {
        dishRepository.deleteDish(dishId = dishId)
    }
}