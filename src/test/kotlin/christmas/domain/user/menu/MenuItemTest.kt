package christmas.domain.user.menu

import christmas.domain.Error
import christmas.domain.hasError
import christmas.domain.user.menu.MenuItem
import org.assertj.core.api.Assertions.assertThatNoException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MenuItemTest {

    @ParameterizedTest
    @ValueSource(strings = ["마라탕", "엽떡마라", "", "0", "지금은 오전 3시 30분"])
    fun `메뉴판에 존재하지 않는 메뉴를 입력했을 때, 오류를 반환한다`(input: String) {
        assertThatThrownBy { MenuItem.from(input) }.hasError(Error.InvalidMenu)
    }

    @ParameterizedTest
    @ValueSource(strings = ["티본스테이크", "초코케이크"])
    fun `메뉴판에 존재하는 메뉴를 입력했을 때, 오류를 반환하지 않는다`(input: String) {
        assertThatNoException().isThrownBy { MenuItem.from(input) }
    }
}