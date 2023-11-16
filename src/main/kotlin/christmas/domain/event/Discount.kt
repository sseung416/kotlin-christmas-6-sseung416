package christmas.domain.event

import christmas.domain.Money
import christmas.domain.user.VisitDate
import christmas.domain.user.menu.Menu

interface Discount {

    val amount: Money
    val eventName: String
}

fun getDiscounts(visitDate: VisitDate, menu: Menu): List<Discount> =
    listOf(
        WeekdayDiscount(visitDate, menu),
        WeekendDiscount(visitDate, menu),
        SpecialDiscount(visitDate),
        ChristmasDiscount(visitDate)
    )

private enum class DiscountType(val eventName: String, val price: Money, val additionalPrice: Money = Money.ZERO) {
    Weekday("평일 할인", Money(2_023)),
    Weekend("주말 할인", Money(2_023)),
    Special("특별 할인", Money(1_000)),
    Christmas("크리스마스 디데이 할인", Money(1_000), Money(100)),
}

internal class WeekdayDiscount(visitDate: VisitDate, menu: Menu) : Discount {

    private val discountType = DiscountType.Weekday

    override val amount: Money = if (visitDate.isWeekday) discountType.price * menu.dessertOrderCount else Money.ZERO
    override val eventName: String = discountType.eventName
}

internal class WeekendDiscount(visitDate: VisitDate, menu: Menu) : Discount {

    private val discountType = DiscountType.Weekend

    override val amount: Money = if (visitDate.isWeekend) discountType.price * menu.mainDishOrderCount else Money.ZERO
    override val eventName: String = discountType.eventName
}

internal class SpecialDiscount(visitDate: VisitDate) : Discount {

    private val discountType = DiscountType.Special

    override val amount: Money = if (visitDate.isSpecial) discountType.price else Money.ZERO
    override val eventName: String = discountType.eventName
}

internal class ChristmasDiscount(visitDate: VisitDate) : Discount {

    private val discountType = DiscountType.Christmas

    override val amount: Money = if (visitDate.isBeforeChristmas) {
        discountType.price + discountType.additionalPrice * (visitDate.day - 1)
    } else {
        Money.ZERO
    }
    override val eventName: String = discountType.eventName
}