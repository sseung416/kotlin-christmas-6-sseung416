package christmas.domain

import christmas.domain.event.Gift
import christmas.domain.event.Badge
import christmas.domain.event.getDiscounts
import christmas.domain.user.VisitDate
import christmas.domain.user.menu.Menu

class DecemberEvent(private val visitDate: VisitDate, private val menu: Menu) {

    private val gift = Gift.find(menu.totalPrice)
    private val badge get() = Badge.find(totalBenefit)
    private val amountByEvent = calculateEventAmount()

    val totalPrice = menu.totalPrice
    val totalBenefit = amountByEvent.entries.sumOf { (_, amount) -> amount.value }.toMoney()
    val expectedPaymentAfterDiscount = menu.totalPrice - totalBenefit
    val visitDay = visitDate.day

    fun getMenuNameAndCount(): Map<String, Int> = menu.countByMenuItem.mapKeys { (menuItem, _) -> menuItem.menuName }

    fun getGiftNameOr(default: String): String = gift?.menuName ?: default

    fun getBadgeNameOr(default: String): String = badge?.badgeName ?: default

    fun getAmountByEvent(): Map<String, Money> = amountByEvent.filterValues { amount -> amount.value > 0 }

    private fun getGiftPriceOr(default: Money = Money.ZERO): Money = gift?.menuPrice ?: default

    private fun calculateEventAmount(): Map<String, Money> {
        if (totalPrice.value < MIN_DISCOUNT_AMOUNT) {
            return emptyMap()
        }

        val discounts = getDiscounts(visitDate, menu)
        val giftPrice = getGiftPriceOr()

        return discounts.associate { discount ->
            discount.eventName to discount.amount
        } + mapOf(Gift.EVENT_NAME to giftPrice)
    }

    companion object {
        private const val MIN_DISCOUNT_AMOUNT = 10_000
    }
}