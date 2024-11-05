package by.zhenyabigel.features.authentication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DishModel(
    val id: Int,
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