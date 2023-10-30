package com.example.database

import com.example.database.DatabaseFactory.dbQuery
import com.example.model.Article
import com.example.model.Articles
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

interface ArticleDAO {
	suspend fun allArticles(): List<Article>
	suspend fun article(id: Int): Article?
	suspend fun addNewArticle(title: String, body: String): Article?
	suspend fun editArticle(id: Int, title: String, body: String): Boolean
	suspend fun deleteArticle(id: Int): Boolean
}

class DefaultArticleDAO : ArticleDAO {
	private fun resultRowToArticle(row: ResultRow) = Article(
		id = row[Articles.id],
		title = row[Articles.title],
		body = row[Articles.body],
	)

	override suspend fun allArticles(): List<Article> = dbQuery {
		Articles.selectAll().map(::resultRowToArticle)
	}
	override suspend fun article(id: Int): Article? = dbQuery {
		Articles
			.select { Articles.id eq id }
			.map(::resultRowToArticle)
			.singleOrNull()
	}

	override suspend fun addNewArticle(title: String, body: String): Article? = dbQuery {
		val insertStatement = Articles.insert {
			it[Articles.title] = title
			it[Articles.body] = body
		}
		insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToArticle)
	}

	override suspend fun editArticle(id: Int, title: String, body: String) = dbQuery {
		Articles.update({ Articles.id eq id }) {
			it[Articles.title] = title
			it[Articles.body] = body
		} > 0
	}

	override suspend fun deleteArticle(id: Int): Boolean = dbQuery {
		Articles.deleteWhere { Articles.id eq id } > 0
	}
}