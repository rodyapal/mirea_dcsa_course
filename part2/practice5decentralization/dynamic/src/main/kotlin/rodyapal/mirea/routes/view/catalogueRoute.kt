package rodyapal.mirea.routes.view

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import rodyapal.mirea.model.database.ValuableEntity
import rodyapal.mirea.view.cataloguePage

fun Route.catalogueRoute() {
	route("/catalogue") {
		get {
			val goods = transaction { ValuableEntity.all().map { it.idData } }.toList()
			call.respondHtml {
				cataloguePage(goods)
			}
		}
	}
}