package christmas.domain

import java.time.DayOfWeek
import java.time.LocalDate

class VisitDate(date: LocalDate) {

    private val dayOfWeekType = DayOfWeekType.from(dayOfWeek = date.dayOfWeek)
    val isWeekday = dayOfWeekType == DayOfWeekType.Weekday

    companion object {
        private const val START_DATE = 1
        private const val END_DATE = 31

        fun from(input: String): VisitDate {
            validateNumber(input)
            validateInvalid(input)
            val date = LocalDate.of(2023, 12, input.toInt())
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

enum class DayOfWeekType {
    Weekend, Weekday;

    companion object {
        fun from(dayOfWeek: DayOfWeek): DayOfWeekType =
            if (dayOfWeek in DayOfWeek.FRIDAY..DayOfWeek.SATURDAY) Weekend else Weekday
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