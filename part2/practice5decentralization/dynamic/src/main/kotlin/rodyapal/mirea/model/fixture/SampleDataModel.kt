package rodyapal.mirea.model.fixture

data class SampleDataModel(
	val someInteger: Int,
	val stringFromRange: String
)

fun List<SampleDataModel>.toDataset(): Map<String, Any> = mapOf(
	"someInteger" to map { it.someInteger },
	"stringFromRange" to map { it.stringFromRange }
)