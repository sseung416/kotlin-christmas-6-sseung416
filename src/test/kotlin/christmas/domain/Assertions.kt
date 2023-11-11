package christmas.domain

import org.assertj.core.api.AbstractThrowableAssert

internal fun AbstractThrowableAssert<*, out Throwable>.hasError(
    expectedError: Error,
    expectedType: Class<out Throwable> = IllegalArgumentException::class.java
) = this.isExactlyInstanceOf(expectedType).hasMessage(expectedError.message)