package com.zemingo.drinksmenu.repo.reactive_store

import kotlinx.coroutines.flow.Flow

interface ReactiveStore<Key, Value, Param> {

    fun getAll(key: List<Key>? = null): Flow<List<Value>>

    fun getByParam(param: Param): Flow<List<Value>>

    fun storeAll(data: List<Value>)
}