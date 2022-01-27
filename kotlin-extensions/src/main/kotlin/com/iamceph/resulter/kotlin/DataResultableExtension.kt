package com.iamceph.resulter.kotlin

import com.google.protobuf.Message
import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.core.model.ResultableData
import com.iamceph.resulter.core.pack.Packeto
import reactor.core.publisher.Mono
import java.util.function.Consumer
import java.util.function.Supplier
import kotlin.reflect.KClass

fun <T> DataResultable<T>.pack(): ResultableData {
    return Packeto.pack(this)
}

fun <T> Mono<DataResultable<T>>.pack(): Mono<ResultableData> {
    return this.map { it.pack() }
}

fun <T> Mono<DataResultable<T>>.ifOk(data : Consumer<T>) : Mono<DataResultable<T>> {
    return this.doOnNext{
        it.ifOk(data)
    }
}

fun <K : Message> ResultableData.unpack(type: KClass<K>): DataResultable<K> {
    return Packeto.unpack(this.data, type.java)
}

fun <K : Message> Mono<ResultableData>.unpack(type: KClass<K>): Mono<DataResultable<K>> {
    return this.map { it.unpack(type) }
}