package christmas.domain.event

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BadgeTest {

    @ParameterizedTest
    @ValueSource(ints = [5000, 9000, 9999])
    fun `5천 원 이상 1만 원 미만 구매 시, 별 배지를 증정한다`(money: Int) {
        // given, when
        val badge = Badge.from(money)

        // then
        assertThat(badge).isEqualTo(Badge.Star)
    }

    @ParameterizedTest
    @ValueSource(ints = [10000, 15000, 19999])
    fun `1만 원 이상 2만 원 미만 구매 시, 트리 배지를 증정한다`(money: Int) {
        // given, when
        val badge = Badge.from(money)

        // then
        assertThat(badge).isEqualTo(Badge.Tree)
    }

    @ParameterizedTest
    @ValueSource(ints = [20000, 10000000])
    fun `2만 원 이상 구매 시, 산타 배지를 증정한다`(money: Int) {
        // given, when
        val badge = Badge.from(money)

        // then
        assertThat(badge).isEqualTo(Badge.Santa)
    }

    @Test
    fun `매칭되는 이벤트 배지가 없을 때, 오류를 반환한다`() {
        assertThatThrownBy { Badge.from(1000) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
    }
}