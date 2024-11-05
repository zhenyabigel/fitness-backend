package by.zhenyabigel.features.authentication.domain.repository

import by.zhenyabigel.features.authentication.data.model.UserModel

interface UserRepository {

    suspend fun getUserByEmail(email: String): UserModel?

    suspend fun insertUser(user: UserModel)
}