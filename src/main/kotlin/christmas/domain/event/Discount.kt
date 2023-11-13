package christmas.domain.event

import christmas.domain.VisitDate
import christmas.domain.menu.Menu

interface Discount {

    val amount: Int
}

private enum class DiscountAmount(val price: Int) {
    Weekday(2_023),
    Weekend(2_023),
    Special(1_000),
    ChristmasDefault(1_000),
    ChristmasAddition(100)
}

class WeekdayDiscount(visitDate: VisitDate, menu: Menu) : Discount {

    override val amount: Int = if (visitDate.isWeekday) DiscountAmount.Weekday.price * menu.dessertOrderCount else 0
}

class WeekendDiscount(visitDate: VisitDate, menu: Menu) : Discount {

    override val amount: Int = if (visitDate.isWeekend) DiscountAmount.Weekend.price * menu.mainDishOrderCount else 0
}

class SpecialDiscount(visitDate: VisitDate) : Discount {

    override val amount: Int = if (visitDate.isSpecial) DiscountAmount.Special.price else 0
}

class ChristmasDiscount(visitDate: VisitDate) : Discount {

    override val amount: Int = if (visitDate.fromChristmasCount >= 0) {
        DiscountAmount.ChristmasDefault.price + DiscountAmount.ChristmasAddition.price * (24 - visitDate.fromChristmasCount.toInt())
    } else {
        0
    }
}