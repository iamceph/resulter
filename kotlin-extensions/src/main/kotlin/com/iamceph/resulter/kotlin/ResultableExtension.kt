package com.iamceph.resulter.kotlin

import com.iamceph.resulter.core.Resultable
import com.iamceph.resulter.core.model.ProtoResultable
import reactor.core.publisher.Mono
import java.util.function.Consumer

/**
 * Converts [ProtoResultable] to [Resultable].
 */
fun ProtoResultable.resultable(): Resultable {
    return Resultable.convert(this)
}

/**
 * Converts [ProtoResultable] to [Resultable].
 */
fun Mono<ProtoResultable>.resultable(): Mono<Resultable> {
    return this.map { it.resultable() }
}

fun Mono<Resultable>.ifOk(resultable: Consumer<Resultable>): Mono<Resultable> {
    return this.doOnNext {
        if (it.isOk) {
            resultable.accept(it)
        }
    }
}

fun Mono<Resultable>.ifFail(resultable: Consumer<Resultable>): Mono<Resultable> {
    return this.doOnNext {
        if (it.isFail) {
            resultable.accept(it)
        }
    }
}