package christmas.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DiscountTest {

    @Test
    fun `평일(일~목)에 디저트 메뉴 2개 주문했을 때, 4,046원을 할인해준다`() {
        val menu = Menu(menuInput = "${Dessert.ChocolateCake.menuName}-2")
        val discount = WeekdayDiscount(visitDate = VisitDate.from("4"), menu = menu)
        assertThat(discount.amount).isEqualTo(DISCOUNT_AMOUNT * 2)
    }

    @Test
    fun `평일(일~목)에 메인 메뉴 1개를 주문했을 때, 할인 금액은 없다`() {
        val menu = Menu(menuInput = "${MainDish.BarbecueRibs.menuName}-1")
        val discount = WeekdayDiscount(visitDate = VisitDate.from("4"), menu = menu)
        assertThat(discount.amount).isEqualTo(0)
    }

    @Test
    fun `주말(금~토)에 메인 메뉴를 3개 주문했을 때, 6,069원을 할인해준다`() {
        val menu = Menu(menuInput = "${MainDish.BarbecueRibs.menuName}-2,${MainDish.ChristmasPasta.menuName}-1")
        val discount = WeekendDiscount(visitDate = VisitDate.from("1"), menu = menu)
        assertThat(discount.amount).isEqualTo(DISCOUNT_AMOUNT * 3)
    }

    @Test
    fun `주말(금~토)에 메인 메뉴를 포함하지 않은 메뉴를 주문했을 때, 할인 금액은 없다`() {
        val menu = Menu(menuInput = "${Dessert.IceCream.menuName}-10")
        val discount = WeekendDiscount(visitDate = VisitDate.from("1"), menu = menu)
        assertThat(discount.amount).isEqualTo(0)
    }

    @ParameterizedTest
    @ValueSource(strings = ["3", "10", "17", "24", "25", "31"])
    fun `별이 있는 날에 방문하면 1000원을 할인해준다`(input: String) {
        val discount = SpecialDiscount(visitDate = VisitDate.from(input))
        assertThat(discount.amount).isEqualTo(1000)
    }

    companion object {
        private const val DISCOUNT_AMOUNT = 2023
    }
}