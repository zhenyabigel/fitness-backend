package by.zhenyabigel.plugins

import by.zhenyabigel.features.authentication.domain.usecase.DishUseCase
import by.zhenyabigel.features.authentication.domain.usecase.UserUseCase
import by.zhenyabigel.features.authentication.routes.UserRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userUseCase: UserUseCase, dishUseCase: DishUseCase) {
    routing {
     UserRoute(userUseCase = userUseCase)
    }
}
