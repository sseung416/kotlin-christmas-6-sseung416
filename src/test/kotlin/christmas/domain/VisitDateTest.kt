package christmas.domain

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class VisitDateTest {

    @ParameterizedTest
    @ValueSource(strings = ["+1", "-1", "+23", "히히"])
    fun `입력 값이 문자일 때, 오류를 반환한다`(input: String) {
        assertThatThrownBy { VisitDate(input) }.hasError(Error.InvalidDate)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "100", "32", "111111111111111111111"])
    fun `입력 값이 1~31의 숫자가 아닐 때, 오류를 반환한다`(input: String) {
        assertThatThrownBy { VisitDate(input) }.hasError(Error.InvalidDate)
    }
}