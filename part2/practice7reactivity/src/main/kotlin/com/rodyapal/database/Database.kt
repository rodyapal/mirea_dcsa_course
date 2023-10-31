package com.rodyapal.database

import com.rodyapal.config.AppConfig
import com.rodyapal.entity.Song
import com.rodyapal.entity.song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase

val appDatabase = R2dbcDatabase(url = AppConfig.dbUrl).also {
	setupDatabase(it)
}

val demoSongs = listOf(
	Song("Bohemian Rhapsody", "Queen", 354, "A Night at the Opera", "EMI", 1),
	Song("Imagine", "John Lennon", 197, "Imagine", "Apple", 2),
	Song("Hotel California", "Eagles", 391, "Hotel California", "Asylum", 3),
	Song("Like a Rolling Stone", "Bob Dylan", 364, "Highway 61 Revisited", "Columbia", 4),
	Song("Yesterday", "The Beatles", 126, "Help!", "Parlophone", 5),
	Song("Stairway to Heaven", "Led Zeppelin", 482, "Led Zeppelin IV", "Atlantic", 6),
	Song("Purple Haze", "Jimi Hendrix", 157, "Are You Experienced", "Track", 7),
	Song("Smells Like Teen Spirit", "Nirvana", 301, "Nevermind", "DGC", 8),
	Song("Boogie Wonderland", "Earth, Wind & Fire", 286, "I Am", "Columbia", 9),
	Song("Billie Jean", "Michael Jackson", 295, "Thriller", "Epic", 10),
	Song("Rolling in the Deep", "Adele", 228, "21", "XL", 11),
	Song("Sweet Child o' Mine", "Guns N' Roses", 356, "Appetite for Destruction", "Geffen", 12),
	Song("Another Brick in the Wall", "Pink Floyd", 343, "The Wall", "Harvest", 13),
	Song("Every Breath You Take", "The Police", 243, "Synchronicity", "A&M", 14),
	Song("Let It Be", "The Beatles", 243, "Let It Be", "Apple", 15),
	Song("Livin' on a Prayer", "Bon Jovi", 243, "Slippery When Wet", "Mercury", 16),
	Song("Superstition", "Stevie Wonder", 244, "Talking Book", "Tamla", 17),
	Song("Dancing Queen", "ABBA", 231, "Arrival", "Epic", 18),
	Song("Wonderwall", "Oasis", 258, "(What's the Story) Morning Glory?", "Creation", 19),
)

fun setupDatabase(database: R2dbcDatabase) {
	MainScope().launch(Dispatchers.IO) {
		database.runQuery {
			QueryDsl.create(Meta.song)
		}
		database.runQuery {
			QueryDsl.insert(Meta.song).multiple(demoSongs)
		}
		return@launch
	}
}