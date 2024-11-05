package by.zhenyabigel

import by.zhenyabigel.features.authentication.domain.helpers.JwtService
import by.zhenyabigel.features.authentication.data.repository.DishRepositoryImpl
import by.zhenyabigel.features.authentication.data.repository.UserRepositoryImpl
import by.zhenyabigel.features.authentication.domain.usecase.DishUseCase
import by.zhenyabigel.features.authentication.domain.usecase.UserUseCase
import by.zhenyabigel.plugins.DatabaseFactory.initializationDatabase
import by.zhenyabigel.plugins.configureMonitoring
import by.zhenyabigel.plugins.configureRouting
import by.zhenyabigel.plugins.configureSecurity
import by.zhenyabigel.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val dishRepository = DishRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jwtService)
    val dishUseCase = DishUseCase(dishRepository)

    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)
    configureRouting(userUseCase, dishUseCase)
}