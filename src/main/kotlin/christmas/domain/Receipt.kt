package christmas.domain

class Receipt(visitDate: VisitDate, private val menu: Menu) { // todo 이름변경

    private val gift = runCatching { Gift.from(menu.totalPrice) }.getOrNull()
    private val eventBadge = runCatching { EventBadge.from(menu.totalPrice) }.getOrNull()

    val totalPrice = menu.totalPrice

    val totalBenefit = WeekdayDiscount(visitDate, menu).amount +
            WeekendDiscount(visitDate, menu).amount +
            SpecialDiscount(visitDate).amount +
            ChristmasDiscount(visitDate).amount +
            getGiftPriceOr()

    val expectedPaymentAfterDiscount = menu.totalPrice - totalBenefit

    fun getMenuNameAndCount(): Map<String, Int> = menu.countByMenuItem.mapKeys { (menuItem, _) -> menuItem.menuName }

    fun getGiftNameOr(default: String): String = gift?.menuName ?: default

    fun getEventBadgeNameOr(default: String): String = eventBadge?.value ?: default

    private fun getGiftPriceOr(default: Int = 0): Int = gift?.menuPrice ?: default
}