package com.rodyapal.entity

import com.rodyapal.dto.SongDto
import kotlinx.serialization.Serializable
import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId

@Serializable
@KomapperEntity
data class Song(
	val title: String,
	val artist: String,
	val duration: Int,
	val album: String,
	val publisher: String,
	@KomapperId
	@KomapperAutoIncrement
	val id: Long = 0,
) {
	companion object {
		fun fromDto(dto: SongDto) = with(dto) {
			Song(title, artist, duration, album, publisher)
		}
	}
}