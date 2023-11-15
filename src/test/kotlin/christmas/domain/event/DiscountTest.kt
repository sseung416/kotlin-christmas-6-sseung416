package christmas.domain.event

import christmas.domain.user.VisitDate
import christmas.domain.user.menu.Dessert
import christmas.domain.user.menu.MainDish
import christmas.domain.user.menu.Menu
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DiscountTest {

    private val weekday = VisitDate.from("4")
    private val weekend = VisitDate.from("1")
    private val christmas = VisitDate.from("25")

    @Test
    fun `평일(일~목)에 디저트 메뉴 2개 주문했을 때, 4,046원을 할인해준다`() {
        // given
        val dessertMenuItemCount = 2
        val menu = Menu.of(input = "${Dessert.ChocolateCake.menuName}-$dessertMenuItemCount")
        val discount = WeekdayDiscount(visitDate = weekday, menu = menu)

        // when, then
        assertThat(discount.amount).isEqualTo(DAY_DISCOUNT_AMOUNT * dessertMenuItemCount)
    }

    @Test
    fun `평일(일~목)에 메인 메뉴 1개를 주문했을 때, 할인 금액은 없다`() {
        // given
        val menu = Menu.of(input = "${MainDish.BarbecueRibs.menuName}-1")
        val discount = WeekdayDiscount(visitDate = weekday, menu = menu)

        // when, then
        assertThat(discount.amount).isZero()
    }

    @Test
    fun `주말(금~토)에 메인 메뉴를 3개 주문했을 때, 6,069원을 할인해준다`() {
        // given
        val menu = Menu.of(input = "${MainDish.BarbecueRibs.menuName}-2,${MainDish.ChristmasPasta.menuName}-1")
        val discount = WeekendDiscount(visitDate = weekend, menu = menu)

        // when, then
        assertThat(discount.amount).isEqualTo(DAY_DISCOUNT_AMOUNT * 3)
    }

    @Test
    fun `주말(금~토)에 메인 메뉴를 포함하지 않은 메뉴를 주문했을 때, 할인 금액은 없다`() {
        // given
        val menu = Menu.of(input = "${Dessert.IceCream.menuName}-10")
        val discount = WeekendDiscount(visitDate = VisitDate.from("1"), menu = menu)

        // when, then
        assertThat(discount.amount).isZero()
    }

    @ParameterizedTest
    @ValueSource(strings = ["3", "10", "17", "24", "25", "31"])
    fun `별이 있는 날에 방문하면 1000원을 할인해준다`(input: String) {
        // given
        val specialDay = VisitDate.from(input)
        val discount = SpecialDiscount(visitDate = specialDay)

        // when, then
        assertThat(discount.amount).isEqualTo(SPECIAL_DISCOUNT_AMOUNT)
    }

    @Test
    fun `25일에 방문한다면, 크리스마스 할인 금액은 3400원이다`() {
        // given
        val discount = ChristmasDiscount(visitDate = christmas)

        // when, then
        assertThat(discount.amount).isEqualTo(3400)
    }

    @ParameterizedTest
    @ValueSource(strings = ["26", "27", "31"])
    fun `크리스마스를 지나서 방문하면, 크리스마스 할인 금액은 없다`(input: String) {
        // given
        val discount = ChristmasDiscount(visitDate = VisitDate.from(input))

        // when, then
        assertThat(discount.amount).isZero()
    }

    companion object {
        private const val DAY_DISCOUNT_AMOUNT = 2023
        private const val SPECIAL_DISCOUNT_AMOUNT = 1000
    }
}