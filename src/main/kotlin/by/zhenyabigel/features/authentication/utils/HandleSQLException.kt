package by.zhenyabigel.features.authentication.utils

import by.zhenyabigel.features.authentication.responses.BaseResponse
import io.ktor.http.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

fun handleSQLException(e: ExposedSQLException): Pair<HttpStatusCode, BaseResponse> {
    return when (e.sqlState) {
        "23505" -> {
            HttpStatusCode.Conflict to BaseResponse(false, Error.UNIQUE_CONSTRAINT_VIOLATION)
        }
        "23503" -> {
            HttpStatusCode.BadRequest to BaseResponse(false, Error.FOREIGN_KEY_VIOLATION)
        }
        "22007" -> {
            HttpStatusCode.BadRequest to BaseResponse(false, Error.INVALID_DATA_FORMAT)
        }
        else -> {
            HttpStatusCode.InternalServerError to BaseResponse(false, Error.GENERAL)
        }
    }
}