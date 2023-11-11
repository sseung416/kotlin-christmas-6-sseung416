package christmas.domain

import java.time.LocalDate

class VisitDate(private val date: LocalDate) {

    companion object {
        private const val START_DATE = 1
        private const val END_DATE = 31

        fun from(input: String): VisitDate {
            validateNumber(input)
            validateInvalid(input)
            val date = LocalDate.ofYearDay(12, input.toInt())
            return VisitDate(date)
        }

        private fun validateNumber(input: String) {
            require(input.isNumber()) { Error.InvalidDate.message }
        }

        private fun validateInvalid(input: String) {
            require(input.length <= 2 && input.toInt() in START_DATE..END_DATE) { Error.InvalidDate.message }
        }
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