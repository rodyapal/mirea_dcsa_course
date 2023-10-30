package rodyapal.mirea.routes.view

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import jetbrains.datalore.plot.PlotSvgExport
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.intern.toSpec
import rodyapal.mirea.model.fixture.getSampleFixtures
import rodyapal.mirea.model.fixture.toDataset
import rodyapal.mirea.model.plot.createBarPlot
import rodyapal.mirea.model.plot.createDensityPlot
import rodyapal.mirea.view.plotPage

fun Route.plotPageRoute() {
	route("plot") {
		get {
			val dataset = getSampleFixtures(100).toDataset()
			val densityPlot = createDensityPlot(dataset, column = "someInteger", stat = Stat.count())
			val barPlot = createBarPlot(dataset, column = "stringFromRange", stat = Stat.density())
			val svgData = listOf(
				PlotSvgExport.buildSvgImageFromRawSpecs(densityPlot.toSpec()),
				PlotSvgExport.buildSvgImageFromRawSpecs(barPlot.toSpec())
			)
			call.respondHtml {
				plotPage(svgData)
			}
		}
	}
}