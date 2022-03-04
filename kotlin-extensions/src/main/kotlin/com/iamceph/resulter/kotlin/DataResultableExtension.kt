package com.iamceph.resulter.kotlin

import com.google.protobuf.Message
import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.model.ResultableData
import com.iamceph.resulter.core.pack.Packeto
import reactor.core.publisher.Mono
import kotlin.reflect.KClass

/**
 * Shortcut for Packing the [DataResultable] into [ResultableData].
 */
fun <T> DataResultable<T>.pack(): ResultableData = Packeto.pack(this)

/**
 * Shortcut for packing the [Mono] with [DataResultable] into a [ResultableData].
 */
fun <T> Mono<DataResultable<T>>.pack(): Mono<ResultableData> = map { it.pack() }

/**
 * Does an action if the internal [DataResultable] is OK.
 */
fun <T> Mono<DataResultable<T>>.ifOk(data: (T) -> Unit): Mono<DataResultable<T>> = doOnNext { it.ifOk(data) }

/**
 * Does an action if the internal [DataResultable] is FAIL.
 */
fun <T> Mono<DataResultable<T>>.ifFail(resultable: (DataResultable<T>) -> Unit): Mono<DataResultable<T>> = doOnNext {
    if (it.isFail) {
        resultable(it)
    }
}

/**
 * Unpacks the [ResultableData] to [DataResultable] with given type.
 */
fun <K : Message> ResultableData.unpack(type: KClass<K>): DataResultable<K> = Packeto.unpack(data, type.java)

/**
 * Unpacks the [ResultableData] in the [Mono] to [DataResultable] with given type.
 */
fun <K : Message> Mono<ResultableData>.unpack(type: KClass<K>): Mono<DataResultable<K>> = map { it.unpack(type) }

/**
 * DataResultable builder block.
 */
inline fun <T> dataResultable(block: () -> T?): DataResultable<T> = try {
    DataResultable.failIfNull(block())
} catch (e: Exception) {
    DataResultable.fail(e)
}