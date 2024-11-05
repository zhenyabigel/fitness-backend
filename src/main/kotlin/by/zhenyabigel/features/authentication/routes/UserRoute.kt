package by.zhenyabigel.features.authentication.routes

import by.zhenyabigel.features.authentication.data.model.UserModel
import by.zhenyabigel.features.authentication.data.model.getRoleByString
import by.zhenyabigel.features.authentication.requests.LoginRequest
import by.zhenyabigel.features.authentication.requests.RegisterRequest
import by.zhenyabigel.features.authentication.responses.BaseResponse
import by.zhenyabigel.features.authentication.domain.helpers.hash
import by.zhenyabigel.features.authentication.domain.usecase.UserUseCase
import by.zhenyabigel.features.authentication.utils.Error
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.UserRoute(userUseCase: UserUseCase) {

    val hashFunction = { p: String -> hash(password = p) }

    post("api/v1/signup") {
        val registerRequest = call.receiveNullable<RegisterRequest>() ?: kotlin.run {
            call.run { respond(HttpStatusCode.BadRequest, BaseResponse(false, Error.GENERAL)) }
            return@post
        }

        try {
            val user = UserModel(
                id = 0,
                email = registerRequest.email.trim().lowercase(),
                login = registerRequest.login.trim().lowercase(),
                password = hashFunction(registerRequest.password.trim()),
                firstName = registerRequest.firstName.trim(),
                lastName = registerRequest.lastName.trim(),
                role = registerRequest.role.trim().getRoleByString()
            )

            userUseCase.createUser(user)
            call.respond(HttpStatusCode.OK, BaseResponse(true, userUseCase.generateToken(userModel = user)))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Error.GENERAL))
        }
    }

    post("api/v1/login") {
        val loginRequest = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.run { respond(HttpStatusCode.BadRequest, BaseResponse(false, Error.GENERAL)) }
            return@post
        }

        try {
            val user = userUseCase.findUserByEmail(loginRequest.email.trim().lowercase())

            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Error.WRONG_EMAIL))
            } else {
                if (user.password == hashFunction(loginRequest.password)) {
                    call.respond(HttpStatusCode.OK, BaseResponse(true, userUseCase.generateToken(userModel = user)))
                } else {
                    call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Error.INCORRECT_PASSWORD))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Error.GENERAL))
        }
    }

    authenticate("jwt") {

        get("api/v1/get-user-info") {
            try {
                val user = call.principal<UserModel>()

                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, Error.USER_NOT_FOUND))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Error.GENERAL))
            }
        }

    }
}