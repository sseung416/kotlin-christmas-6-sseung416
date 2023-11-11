package christmas.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DiscountTest {

    @Test
    fun `평일(월~목)에 디저트 메뉴 2개 주문했을 때, 4,046원을 할인해준다`() {
        val menu = Menu("${Dessert.ChocolateCake.menuName}-2")
        val discount = WeekdayDiscount(visitDate = VisitDate.from("4"), menu = menu)
        assertThat(discount.amount).isEqualTo(DISCOUNT_AMOUNT * 2)
    }

    @Test
    fun `평일(월~목)에 메인 메뉴 1개를 주문했을 때, 할인 금액은 없다`() {
        val menu = Menu("${MainDish.BarbecueRibs.menuName}-1")
        val discount = WeekdayDiscount(visitDate = VisitDate.from("4"), menu = menu)
        assertThat(discount.amount).isEqualTo(0)
    }

    companion object {
        private const val DISCOUNT_AMOUNT = 2023
    }
}