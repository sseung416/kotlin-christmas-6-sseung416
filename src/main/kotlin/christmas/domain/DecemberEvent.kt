package christmas.domain

import christmas.domain.event.Gift
import christmas.domain.event.Badge
import christmas.domain.event.getDiscounts
import christmas.domain.user.VisitDate
import christmas.domain.user.menu.Menu

class DecemberEvent(private val visitDate: VisitDate, private val menu: Menu) {

    private val gift = Gift.find(menu.totalPrice)
    private val badge = Badge.find(menu.totalPrice)
    private val amountByEvent = calculateEventAmount()

    val totalPrice = menu.totalPrice
    val totalBenefit = amountByEvent.entries.sumOf { (_, amount) -> amount }
    val expectedPaymentAfterDiscount = menu.totalPrice - totalBenefit
    val visitDay = visitDate.day

    fun getMenuNameAndCount(): Map<String, Int> = menu.countByMenuItem.mapKeys { (menuItem, _) -> menuItem.menuName }

    fun getGiftNameOr(default: String): String = gift?.menuName ?: default

    fun getBadgeNameOr(default: String): String = badge?.badgeName ?: default

    fun getAmountByEvent(): Map<String, Int> = amountByEvent.filterValues { amount -> amount > 0 }

    private fun getGiftPriceOr(default: Int = 0): Int = gift?.menuPrice ?: default

    private fun calculateEventAmount(): Map<String, Int> {
        val discounts = getDiscounts(visitDate, menu)
        val giftPrice = getGiftPriceOr()

        val mutableMap = mutableMapOf<String, Int>()
        val discountMap = discounts.associate { discount ->
            discount.eventName to discount.amount
        }
        mutableMap.putAll(discountMap)
        mutableMap[Gift.EVENT_NAME] = giftPrice

        return mutableMap
    }
}