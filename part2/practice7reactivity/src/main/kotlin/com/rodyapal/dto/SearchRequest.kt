package com.rodyapal.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchRequest(
	val query: String
)
