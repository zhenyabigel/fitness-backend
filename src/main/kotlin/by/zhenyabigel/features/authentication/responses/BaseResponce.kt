package by.zhenyabigel.features.authentication.responses

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse(
    val success: Boolean,
    val message: String?
)