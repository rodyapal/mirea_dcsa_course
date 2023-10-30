package rodyapal.mirea.model.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object AppDb {
	private const val DB_USER = "user"
	private const val DB_PASSWORD = "password"
	private const val DB_URL = "jdbc:mysql://mysql:3306/appDb"
	private const val DB_URL_BACKUP = "jdbc:mysql://localhost:3306/appDb"
	private const val DB_DRIVER = "com.mysql.cj.jdbc.Driver"

	private var self = Database.connect(
		url = DB_URL,
		user = DB_USER,
		password = DB_PASSWORD,
		driver = DB_DRIVER
	)

	fun setup() {
		try {
			transaction(self) {
				SchemaUtils.create(Users, Valuables)
			}
		} catch (e: Exception) {
			self = Database.connect(
				url = DB_URL_BACKUP,
				user = DB_USER,
				password = DB_PASSWORD,
				driver = DB_DRIVER
			)
		}
	}

	suspend fun <T> query(block: suspend () -> T): T =
		newSuspendedTransaction(Dispatchers.IO) {
			addLogger(StdOutSqlLogger)
			block()
		}
}