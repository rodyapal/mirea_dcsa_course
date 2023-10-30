package com.example.database

import com.example.model.Article
import com.example.model.Articles
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
	fun init() {
		val driverClassName = "org.h2.Driver"
		val jdbcURL = "jdbc:h2:file:./build/db"
		val database = Database.connect(jdbcURL, driverClassName)
		transaction(database) {
			SchemaUtils.create(Articles)
		}
		listOf<Article>(
			Article(id = 1, title = "Title of the article", body = "The body of the article"),
			Article(id = 2, title = "Another title of the article", body = "More content of the article"),
			Article(id = 3, title = "A third title of the article", body = "Yet more content of the article"),
			Article(id = 4, title = "A fourth title of the article", body = "Last part of the article content"),
			Article(id = 5, title = "And a fifth title of the article", body = "Final content of article")
		).forEach { article ->
			transaction {
				Articles.insert {
					it[Articles.title] = article.title
					it[Articles.body] = article.body
				}
			}
		}
	}

	suspend fun <T> dbQuery(block: suspend () -> T): T =
		newSuspendedTransaction(Dispatchers.IO) { block() }
}