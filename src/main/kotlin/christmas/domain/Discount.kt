package christmas.domain

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