package by.zhenyabigel.features.authentication.utils

object Error {
    const val GENERAL = "Oh, something went wrong!"
    const val WRONG_EMAIL = "Wrong email address!"
    const val INCORRECT_PASSWORD = "Incorrect password!"
    const val MISSING_FIELDS = "Missing some fields!"
    const val USER_NOT_FOUND = "Oops, user not found!"
    const val UNIQUE_CONSTRAINT_VIOLATION = "This dish already exists!"
    const val FOREIGN_KEY_VIOLATION = "External key violation: the record cannot be deleted because it is used elsewhere"
    const val INVALID_DATA_FORMAT = "Incorrect data format"
}