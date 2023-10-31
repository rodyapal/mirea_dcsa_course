package com.rodyapal

import com.rodyapal.database.appDatabase
import com.rodyapal.database.demoSongs
import com.rodyapal.database.setupDatabase
import com.rodyapal.dto.SongDto
import com.rodyapal.entity.DefaultSongRepository
import com.rodyapal.entity.song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import kotlin.test.assertEquals
import kotlin.test.assertNull

suspend fun recreateTable() {
	appDatabase.runQuery {
		QueryDsl.drop(Meta.song)
	}
	appDatabase.runQuery {
		QueryDsl.create(Meta.song)
	}
	appDatabase.runQuery {
		QueryDsl.insert(Meta.song).multiple(demoSongs)
	}
}


class DefaultSongRepositoryTest {
	private val repository = DefaultSongRepository()

	@BeforeEach
	fun recreate() = runTest {
		recreateTable()
	}

	@Test
	fun testGetAll() = runTest {
		val songs = repository.getAll().toList()
		assertEquals(19, songs.size)
		assertEquals("Bohemian Rhapsody", songs[0].title)
	}

	@Test
	fun testGetById() = runTest {
		assertEquals("The Beatles", repository.getById(15)?.artist)
		assertNull(repository.getById(20))
	}

	@Test
	fun testAdd() = runTest {
		recreateTable()
		SongDto(
			"test title", "test artist", 0, "test album", "test publisher"
		).let { repository.add(it) }
		val songs = repository.getAll().toList()
		println("\n\nallSongs: ${Json.encodeToString(songs)}\n\n")
		assertEquals(20, songs.size)
		assertEquals("test title", songs.last().title)
	}

	@Test
	fun testSearchByAlbum() = runTest {
		val result = repository.searchByAlbum("Night").toList()
		assertEquals("Bohemian Rhapsody", result.first().title)
		assertEquals(1, repository.searchByAlbum("A Night at the Opera").toList().size)
	}

	@Test
	fun testSearchByArtist() = runTest {
		val result = repository.searchByArtist("The").toList()
		assertEquals("Yesterday", result.first().title)
		assertEquals(3, repository.searchByArtist("The").toList().size)
	}

	@Test
	fun testSearchByTitle() = runTest {
		val result = repository.searchByTitle("ike").toList()
		assertEquals("Like a Rolling Stone", result.first().title)
		assertEquals(2, repository.searchByTitle("ike").toList().size)
	}
}