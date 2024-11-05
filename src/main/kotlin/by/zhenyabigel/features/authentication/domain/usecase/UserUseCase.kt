package by.zhenyabigel.features.authentication.domain.usecase

import by.zhenyabigel.features.authentication.domain.helpers.JwtService
import by.zhenyabigel.features.authentication.data.model.UserModel
import by.zhenyabigel.features.authentication.domain.repository.UserRepository
import com.auth0.jwt.JWTVerifier

class UserUseCase(
    private val repositoryImpl: UserRepository,
    private val jwtService: JwtService
) {

    suspend fun createUser(userModel: UserModel) = repositoryImpl.insertUser(user =  userModel)

    suspend fun findUserByEmail(email: String) = repositoryImpl.getUserByEmail(email = email)

    fun generateToken(userModel: UserModel): String = jwtService.generateToken(user = userModel)

    fun getGwtVerifier(): JWTVerifier = jwtService.getVerifier()

}