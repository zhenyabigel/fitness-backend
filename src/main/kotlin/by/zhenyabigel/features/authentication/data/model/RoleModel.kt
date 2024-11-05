package by.zhenyabigel.features.authentication.data.model

import by.zhenyabigel.features.authentication.utils.Roles

enum class RoleModel  {
    ADMIN, INSTRUCTOR, CLIENT
}

fun String.getRoleByString(): RoleModel {
    return when(this) {
        Roles.ADMIN -> RoleModel.ADMIN
        Roles.INSTRUCTOR -> RoleModel.INSTRUCTOR
        else -> RoleModel.CLIENT
    }
}

fun RoleModel.getStringByRole() : String {
    return when(this) {
        RoleModel.ADMIN -> Roles.ADMIN
        RoleModel.INSTRUCTOR -> Roles.INSTRUCTOR
        else -> Roles.CLIENT
    }
}