package rodyapal.mirea.model.plot

import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.intern.Plot
import org.jetbrains.letsPlot.intern.layer.StatOptions
import org.jetbrains.letsPlot.letsPlot

fun createDensityPlot(dataset: Map<String, Any>, column: String, stat: StatOptions): Plot {
	return letsPlot(dataset) + geomDensity(
		dataset, stat = stat,
		color = "dark-green",
		fill = "green",
		alpha = .3,
		size = 2.0
	) {
		x = column
	}
}

fun createBarPlot(dataset: Map<String, Any>, column: String, stat: StatOptions): Plot {
	return letsPlot(dataset) + geomBar(
		dataset, stat = stat,
		color = "dark-blue",
		fill = "blue",
		alpha = .3,
		size = 2.0
	) {
		x = column
	}
}