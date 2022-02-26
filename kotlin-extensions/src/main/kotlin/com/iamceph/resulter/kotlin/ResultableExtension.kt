package com.iamceph.resulter.kotlin

import com.iamceph.resulter.core.Resultable
import com.iamceph.resulter.core.model.ProtoResultable
import reactor.core.publisher.Mono

/**
 * Converts [ProtoResultable] to [Resultable].
 */
fun ProtoResultable.resultable(): Resultable = Resultable.convert(this)

/**
 * Converts [ProtoResultable] to [Resultable].
 */
fun Mono<ProtoResultable>.resultable(): Mono<Resultable> = map { it.resultable() }

fun Mono<Resultable>.ifOk(resultable: (Resultable) -> Unit): Mono<Resultable> = doOnNext {
    if (it.isOk) {
        resultable(it)
    }
}

fun Mono<Resultable>.ifFail(resultable: (Resultable) -> Unit): Mono<Resultable> = doOnNext {
    if (it.isFail) {
        resultable(it)
    }
}

inline fun resultable(block: () -> Unit): Resultable = try {
    block()
    Resultable.ok()
} catch (e: Exception) {
    Resultable.fail(e)
}