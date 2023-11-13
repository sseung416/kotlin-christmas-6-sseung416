package christmas.domain.user.menu

import christmas.domain.Error
import christmas.domain.hasError
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MenuTest {

    @ParameterizedTest
    @ValueSource(strings = ["양송이스프-0", "타파스-1,제로콜라-0"])
    fun `메뉴는 1개 이상 주문이 가능하다`(input: String) {
        assertThatThrownBy { Menu.of(input) }.hasError(Error.InvalidMenu)
    }

    @ParameterizedTest
    @ValueSource(strings = ["양송이수프-21", "타파스-10,초코케이크-5,제로콜라-10", "티본스테이크-10000000000000000000000000"])
    fun `최대 주문 가능 개수(20개)를 넘긴 경우, 오류를 반환한다`(input: String) {
        assertThatThrownBy { Menu.of(input) }.hasError(Error.InvalidMenu)
    }

    @Test
    fun `중복된 메뉴를 입력할 경우, 오류를 반환한다`() {
        assertThatThrownBy { Menu.of("양송이수프-1, 양송이수프-1") }.hasError(Error.InvalidMenu)
    }

    @ParameterizedTest
    @ValueSource(strings = ["제로콜라-1", "레드와인-1", "샴페인-1"])
    fun `음료만 주문할 경우, 오류를 반환한다`(input: String) {
        assertThatThrownBy { Menu.of(input) }.hasError(Error.InvalidMenu)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "안녕", "양송이수프-2,타파스", "타파스-1.양송이수프-1"])
    fun `입력 값이 메뉴의 주문 형식이 다른 경우, 오류를 반환한다`(input: String) {
        assertThatThrownBy { Menu.of(input) }.hasError(Error.InvalidMenu)
    }

    @Test
    fun `티본 스테이크 1개, 아이스크림 2개를 주문했을 때 총 주문금액은 65,000원이다`() {
        val menu = Menu.of("${MainDish.TBoneSteak.menuName}-1,${Dessert.IceCream.menuName}-2")
        assertThat(menu.totalPrice).isEqualTo(MainDish.TBoneSteak.price + Dessert.IceCream.price * 2)
    }
}