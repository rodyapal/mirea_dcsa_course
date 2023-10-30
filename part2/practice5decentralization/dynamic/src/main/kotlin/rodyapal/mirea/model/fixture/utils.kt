package rodyapal.mirea.model.fixture

import com.appmattus.kotlinfixture.kotlinFixture

private val fixture = kotlinFixture()
fun getSampleFixtures(amount: Int): List<SampleDataModel> = buildList(amount) {
	for (i in 0 until amount) {
		add(
			SampleDataModel(
				someInteger = fixture(1..12),
				stringFromRange = fixture(listOf("alpha", "betta", "gamma")),
			)
		)
	}
}