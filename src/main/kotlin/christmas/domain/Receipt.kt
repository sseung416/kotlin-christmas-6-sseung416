package christmas.domain

class Receipt(private val visitDate: VisitDate, private val menu: Menu) { // todo 이름변경

    private val gift = runCatching { Gift.from(menu.totalPrice) }.getOrNull()
    private val eventBadge = runCatching { EventBadge.from(menu.totalPrice) }.getOrNull()
    private val amountByEvent = calculateEventAmount()

    val totalPrice = menu.totalPrice
    val totalBenefit = amountByEvent.entries.sumOf { (_, amount) -> amount }
    val expectedPaymentAfterDiscount = menu.totalPrice - totalBenefit
    val visitDay = visitDate.day

    fun getMenuNameAndCount(): Map<String, Int> = menu.countByMenuItem.mapKeys { (menuItem, _) -> menuItem.menuName }

    fun getGiftNameOr(default: String): String = gift?.menuName ?: default

    fun getEventBadgeNameOr(default: String): String = eventBadge?.value ?: default

    fun getAmountByEvent(): Map<String, Int> =
        amountByEvent.filterValues { amount -> amount > 0 }.mapKeys { (event, _) -> event.eventName }

    private fun getGiftPriceOr(default: Int = 0): Int = gift?.menuPrice ?: default

    private fun calculateEventAmount(): Map<Event, Int> {
        val weekdayDiscountAmount = WeekdayDiscount(visitDate, menu).amount
        val weekendDiscountAmount = WeekendDiscount(visitDate, menu).amount
        val specialDiscountAmount = SpecialDiscount(visitDate).amount
        val christmasDiscountAmount = ChristmasDiscount(visitDate).amount
        val giftPrice = getGiftPriceOr()

        return mapOf(
            Event.WeekdayDiscount to weekdayDiscountAmount,
            Event.WeekendDiscount to weekendDiscountAmount,
            Event.SpecialDiscount to specialDiscountAmount,
            Event.ChristmasDDay to christmasDiscountAmount,
            Event.Gift to giftPrice
        )
    }
}

private enum class Event(val eventName: String) {
    ChristmasDDay("크리스마스 디데이 할인"),
    WeekdayDiscount("평일 할인"),
    WeekendDiscount("주말 할인"),
    SpecialDiscount("특별 할인"),
    Gift("증정 이벤트");
}