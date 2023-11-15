package christmas.domain.event

import christmas.domain.user.VisitDate
import christmas.domain.user.menu.Menu

interface Discount {

    val amount: Int
    val eventName: String
}

fun getDiscounts(visitDate: VisitDate, menu: Menu): List<Discount> =
    listOf(
        WeekdayDiscount(visitDate, menu),
        WeekendDiscount(visitDate, menu),
        SpecialDiscount(visitDate),
        ChristmasDiscount(visitDate)
    )

private enum class DiscountAmount(val price: Int) {
    Weekday(2_023),
    Weekend(2_023),
    Special(1_000),
    ChristmasDefault(1_000),
    ChristmasAddition(100)
}

internal class WeekdayDiscount(visitDate: VisitDate, menu: Menu) : Discount {

    override val amount: Int = if (visitDate.isWeekday) DiscountAmount.Weekday.price * menu.dessertOrderCount else 0
    override val eventName: String = "평일 할인"
}

internal class WeekendDiscount(visitDate: VisitDate, menu: Menu) : Discount {

    override val amount: Int = if (visitDate.isWeekend) DiscountAmount.Weekend.price * menu.mainDishOrderCount else 0
    override val eventName: String = "주말 할인"
}

internal class SpecialDiscount(visitDate: VisitDate) : Discount {

    override val amount: Int = if (visitDate.isSpecial) DiscountAmount.Special.price else 0
    override val eventName: String = "특별 할인"
}

internal class ChristmasDiscount(visitDate: VisitDate) : Discount {

    override val amount: Int = if (visitDate.isBeforeChristmas) {
        DiscountAmount.ChristmasDefault.price + DiscountAmount.ChristmasAddition.price * (visitDate.day - 1)
    } else {
        0
    }
    override val eventName: String = "크리스마스 디데이 할인"
}