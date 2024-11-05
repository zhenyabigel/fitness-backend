package by.zhenyabigel.features.authentication.data.repository

import by.zhenyabigel.features.authentication.data.model.DishModel
import by.zhenyabigel.features.authentication.data.model.table.DishTable
import by.zhenyabigel.features.authentication.domain.repository.DishRepository
import by.zhenyabigel.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DishRepositoryImpl : DishRepository {

    override suspend fun addDish(dish: DishModel) {
        dbQuery {
            DishTable.insert { table ->
                table[name] = dish.name
                table[calories] = dish.calories
                table[protein] = dish.protein
                table[fat] = dish.fat
                table[carbohydrates] = dish.carbohydrates
                table[servingSize] = dish.servingSize
                table[category] = dish.category
                table[createdAt] = System.currentTimeMillis()
                table[updatedAt] = System.currentTimeMillis()
            }
        }
    }

    override suspend fun getAllDishes(): List<DishModel> {
        return dbQuery {
            DishTable.selectAll().mapNotNull { rowToDish(it) }
        }
    }

    override suspend fun updateDish(dish: DishModel, dishId: Int) {
        dbQuery {
            DishTable.update(where = {
                DishTable.id.eq(dishId) and DishTable.name.eq(dish.name)
            }) { table ->
                table[name] = dish.name
                table[calories] = dish.calories
                table[protein] = dish.protein
                table[fat] = dish.fat
                table[carbohydrates] = dish.carbohydrates
                table[servingSize] = dish.servingSize
                table[category] = dish.category
                table[createdAt] = dish.createdAt
                table[updatedAt] = dish.updatedAt
            }
        }
    }

    override suspend fun deleteDish(dishId: Int) {
        dbQuery {
            DishTable.deleteWhere { DishTable.id.eq(dishId) }
        }
    }

    private fun rowToDish(row: ResultRow?): DishModel? {
        if (row == null) {
            return null
        }

        return DishModel(
            id = row[DishTable.id],
            name = row[DishTable.name],
            calories = row[DishTable.calories],
            protein = row[DishTable.protein],
            fat = row[DishTable.fat],
            carbohydrates = row[DishTable.carbohydrates],
            servingSize = row[DishTable.servingSize],
            category = row[DishTable.category],
            createdAt = row[DishTable.createdAt],
            updatedAt = row[DishTable.updatedAt]
        )
    }

}