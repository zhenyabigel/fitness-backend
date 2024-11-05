package by.zhenyabigel.features.authentication.routes

import by.zhenyabigel.features.authentication.data.model.DishModel
import by.zhenyabigel.features.authentication.domain.usecase.DishUseCase
import by.zhenyabigel.features.authentication.requests.UpsertDishRequest
import by.zhenyabigel.features.authentication.responses.BaseResponse
import by.zhenyabigel.features.authentication.utils.Error
import by.zhenyabigel.features.authentication.utils.Success
import by.zhenyabigel.features.authentication.utils.handleSQLException
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

fun Route.DishRoute(dishUseCase: DishUseCase) {

    authenticate("jwt") {

        get("api/v1/get-all-dishes") {
            try {
                val dishes = dishUseCase.getAllDishes()

                call.respond(HttpStatusCode.OK, dishes)
            } catch (e: ExposedSQLException) {
                val (status, response) = handleSQLException(e)

                call.respond(status, response)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Error.GENERAL))
            }
        }


        post("api/v1/create-dish") {
            val dishRequest = call.receiveNullable<UpsertDishRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Error.MISSING_FIELDS))
                return@post
            }

            try {
                val dish = DishModel(
                    id = 0,
                    name = dishRequest.name,
                    calories = dishRequest.calories,
                    protein = dishRequest.protein,
                    fat = dishRequest.fat,
                    carbohydrates = dishRequest.carbohydrates,
                    servingSize = dishRequest.servingSize,
                    category = dishRequest.category,
                    createdAt = dishRequest.createdAt,
                    updatedAt = dishRequest.updatedAt
                )

                dishUseCase.addDish(dish = dish)
                call.respond(
                    HttpStatusCode.OK, BaseResponse(success = true, message = Success.CARD_ADDED_SUCCESSFULLY)
                )
            } catch (e: ExposedSQLException) {
                val (status, response) = handleSQLException(e)

                call.respond(status, response)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Error.GENERAL))
            }
        }

        post("api/v1/update-dish") {
            val dishRequest = call.receiveNullable<UpsertDishRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Error.MISSING_FIELDS))
                return@post
            }

            try {
                val dish = DishModel(
                    id = dishRequest.id ?: 0,
                    name = dishRequest.name,
                    calories = dishRequest.calories,
                    protein = dishRequest.protein,
                    fat = dishRequest.fat,
                    carbohydrates = dishRequest.carbohydrates,
                    servingSize = dishRequest.servingSize,
                    category = dishRequest.category,
                    createdAt = dishRequest.createdAt,
                    updatedAt = dishRequest.updatedAt
                )

                dishUseCase.updateDish(dish = dish, dishId = dishRequest.id ?: 0)
                call.respond(
                    HttpStatusCode.OK, BaseResponse(success = true, message = Success.CARD_UPDATE_SUCCESSFULLY)
                )
            } catch (e: ExposedSQLException) {
                val (status, response) = handleSQLException(e)

                call.respond(status, response)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Error.GENERAL))
            }
        }

        delete("api/v1/delete-dish") {
            val dishRequest = call.request.queryParameters["id"]?.toInt() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Error.MISSING_FIELDS))
                return@delete
            }

            try {
                dishUseCase.deleteDish(dishId = dishRequest)
                call.respond(
                    HttpStatusCode.OK, BaseResponse(success = true, message = Success.CARD_DELETED_SUCCESSFULLY)
                )
            } catch (e: ExposedSQLException) {
                val (status, response) = handleSQLException(e)

                call.respond(status, response)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Error.GENERAL))
            }
        }
    }
}