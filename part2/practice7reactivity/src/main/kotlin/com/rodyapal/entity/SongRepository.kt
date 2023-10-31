package com.rodyapal.entity

import com.rodyapal.database.appDatabase
import com.rodyapal.dto.SongDto
import kotlinx.coroutines.flow.Flow
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase

interface SongRepository {
	suspend fun getAll(): Flow<Song>
	suspend fun getById(id: Long): Song?
	suspend fun add(dto: SongDto): Song
	suspend fun searchByAlbum(album: String): Flow<Song>
	suspend fun searchByArtist(artist: String): Flow<Song>
	suspend fun searchByTitle(title: String): Flow<Song>
}

class DefaultSongRepository(
	private val database: R2dbcDatabase = appDatabase
) : SongRepository {

	private val songs get() = Meta.song

	override suspend fun getAll(): Flow<Song> = QueryDsl
		.from(songs)
		.let { database.flowQuery(it) }
	override suspend fun getById(id: Long): Song? = QueryDsl
		.from(songs)
		.where { songs.id eq id }
		.let { database.runQuery(it) }
		.let { if (it.isEmpty()) null else it.first() }

	override suspend fun add(dto: SongDto): Song = QueryDsl
		.insert(songs)
		.single(Song.fromDto(dto))
		.let { database.runQuery(it) }

	override suspend fun searchByAlbum(album: String): Flow<Song> = QueryDsl
		.from(songs)
		.where { songs.album like "%$album%" }
		.let { database.flowQuery(it) }

	override suspend fun searchByArtist(artist: String): Flow<Song> = QueryDsl
		.from(songs)
		.where { songs.artist like "%$artist%" }
		.let { database.flowQuery(it) }

	override suspend fun searchByTitle(title: String): Flow<Song> = QueryDsl
		.from(songs)
		.where { songs.title like "%$title%" }
		.let { database.flowQuery(it) }
}