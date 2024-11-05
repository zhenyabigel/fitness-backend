package by.zhenyabigel.features.authentication.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpsertDishRequest(
    val id: Int? = null,
    val name: String,
    val calories: Int,
    val protein: Double,
    val fat: Double,
    val carbohydrates: Double,
    val servingSize: String,
    val category: String,
    val createdAt: Long,
    val updatedAt: Long
)
