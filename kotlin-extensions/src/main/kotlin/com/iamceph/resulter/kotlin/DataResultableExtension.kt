package com.iamceph.resulter.kotlin

import com.google.protobuf.Message
import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.model.ResultableData
import com.iamceph.resulter.core.pack.Packeto
import reactor.core.publisher.Mono
import kotlin.reflect.KClass

fun <T> DataResultable<T>.pack(): ResultableData = Packeto.pack(this)

fun <T> Mono<DataResultable<T>>.pack(): Mono<ResultableData> = map { it.pack() }

fun <T> Mono<DataResultable<T>>.ifOk(data: (T) -> Unit): Mono<DataResultable<T>> = doOnNext { it.ifOk(data) }

fun <K : Message> ResultableData.unpack(type: KClass<K>): DataResultable<K> = Packeto.unpack(data, type.java)

fun <K : Message> Mono<ResultableData>.unpack(type: KClass<K>): Mono<DataResultable<K>> = map { it.unpack(type) }

inline fun <T> dataResultable(block: () -> T?): DataResultable<T> = try {
    DataResultable.failIfNull(block())
} catch (e: Exception) {
    DataResultable.fail(e)
}