package io.github.golok56.githubuser.domain.usecase

import kotlinx.coroutines.flow.Flow

abstract class UseCase<Input, Output> {
    operator fun invoke(input: Input? = null) = createOutput(input)
    protected abstract fun createOutput(input: Input?): Flow<Output>
}