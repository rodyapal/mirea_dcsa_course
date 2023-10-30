package rodyapal.mirea.model.database

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Valuables : IntIdTable("valuables") {
	val title = varchar("title", 32)
	val description = varchar("description", 256)
	val cost = integer("cost")
}

class ValuableEntity(id: EntityID<Int>) : IntEntity(id) {
	companion object : IntEntityClass<ValuableEntity>(Valuables)
	var title by Valuables.title
	var description by Valuables.description
	var cost by Valuables.cost

	val data get() = ValuableDto(title, description, cost)
	val idData get() = ValuableIdDto(id.value, title, description, cost)
}

@Serializable
data class ValuableDto(
	val title: String,
	val description: String,
	val cost: Int
)

@Serializable
data class ValuableIdDto(
	val id: Int,
	val title: String,
	val description: String,
	val cost: Int
)