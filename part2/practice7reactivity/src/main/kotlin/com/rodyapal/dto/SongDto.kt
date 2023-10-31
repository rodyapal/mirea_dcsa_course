package com.rodyapal.dto

import kotlinx.serialization.Serializable

@Serializable
data class SongDto(
	val title: String,
	val artist: String,
	val duration: Int,
	val album: String,
	val publisher: String,
)
