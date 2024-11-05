package by.zhenyabigel.features.authentication.data.model.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object DishTable:Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 255)
    val calories: Column<Int> = integer("calories")
    val protein: Column<Double> = double("protein")
    val fat: Column<Double> = double("fat")
    val carbohydrates: Column<Double> = double("carbohydrates")
    val servingSize: Column<String> = varchar("serving_size", 50)
    val category: Column<String> = varchar("category", 100)
    val createdAt: Column<Long> = long("created_at")
    val updatedAt: Column<Long> = long("updated_at")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}