package com.rodyapal.dcsa.model

interface Reducer <E> {
	fun obtainEvent(event: E)
}