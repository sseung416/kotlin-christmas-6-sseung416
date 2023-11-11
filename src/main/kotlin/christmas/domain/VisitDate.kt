package christmas.domain

import java.time.LocalDate

class VisitDate(input: String) {

    private val date: LocalDate

    init {
        validateNumber(input)
        validateInvalid(input)
        date = LocalDate.ofYearDay(12, input.toInt())
    }

    private fun validateNumber(input: String) {
        require(input.isNumber()) { Error.InvalidDate.message }
    }

    private fun validateInvalid(input: String) {
        require(input.length <= 2 && input.toInt() in START_DATE..END_DATE) { Error.InvalidDate.message }
    }

    companion object {
        private const val START_DATE = 1
        private const val END_DATE = 31
    }
}

fun String.toValidInt(errorMessage: String = ""): Int {
    require(this.isNumber()) { errorMessage }
    return this.toInt()
}

fun String.isNumber(containsSign: Boolean = false): Boolean {
    if (this.isBlank()) return false
    if (!containsSign && this[0] in listOf('-', '+')) return false
    return this.toBigIntegerOrNull() != null
}