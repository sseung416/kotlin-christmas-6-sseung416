package christmas.domain.user

import christmas.domain.Error
import christmas.domain.hasError
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class VisitDateTest {

    @ParameterizedTest
    @ValueSource(strings = ["+1", "-1", "+23", "히히"])
    fun `입력 값에 문자가 포함되어 있을 때, 오류를 반환한다`(input: String) {
        assertThatThrownBy { VisitDate.from(input) }.hasError(Error.InvalidDate)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "100", "32", "111111111111111111111"])
    fun `입력 값이 1~31의 숫자가 아닐 때, 오류를 반환한다`(input: String) {
        assertThatThrownBy { VisitDate.from(input) }.hasError(Error.InvalidDate)
    }

    @ParameterizedTest
    @ValueSource(strings = ["1", "2", "8", "9"])
    fun `예약 날짜로 주말을 입력했을 때, isWeekend의 값이 true이다`(input: String) {
        // given
        val visitDate = VisitDate.from(input)

        // when, then
        assertThat(visitDate.isWeekend).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["4", "11", "5", "6", "7"])
    fun `예약 날짜로 평일을 입력했을 때, isWeekday의 값이 true이다`(input: String) {
        // given
        val visitDate = VisitDate.from(input)

        // when, then
        assertThat(visitDate.isWeekday).isTrue()
    }
}