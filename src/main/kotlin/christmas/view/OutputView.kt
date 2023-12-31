package christmas.view

import christmas.domain.DecemberEvent
import christmas.domain.Money

class OutputView {

    fun printIntroduce() {
        println(INTRODUCE_MESSAGE)
    }

    fun printDecemberEvent(decemberEvent: DecemberEvent) {
        val message = buildString {
            appendLine(PREVIEW_EVENT_MESSAGE_TEMPLATE.format(decemberEvent.visitDay))

            append(getOrderMenuMessage(decemberEvent.getMenuNameAndCount()))
            append(getTotalOrderAmountMessage(decemberEvent.totalPrice))
            append(getGiftMessage(decemberEvent.getGiftNameOr(NOTHING)))
            append(getsDiscountDetailsMessage(decemberEvent.getAmountByEvent()))
            append(getTotalBenefitMessage(decemberEvent.totalBenefit))
            append(getExpectedPaymentAfterDiscountMessage(decemberEvent.expectedPaymentAfterDiscount))
            append(getBadgeMessage(decemberEvent.getBadgeNameOr(NOTHING)))
        }
        print(message)
    }

    fun printError(errorMessage: String?) {
        errorMessage?.let { println("$ERROR_TOKEN $it") }
    }

    private fun getBadgeMessage(badgeName: String): String = buildString {
        appendDoubleLine(Header.DecemberEventBadge)
        append(badgeName)
    }

    private fun getExpectedPaymentAfterDiscountMessage(money: Money): String = buildString {
        appendDoubleLine(Header.ExpectedPaymentAfterDiscount)
        appendLine("${formatMoney(money)}원")
    }

    private fun getsDiscountDetailsMessage(amountByEvent: Map<String, Money>): String = buildString {
        appendDoubleLine(Header.DiscountDetails)

        if (amountByEvent.isEmpty()) {
            appendLine(NOTHING)
            return@buildString
        }

        amountByEvent.forEach { (eventName, discount) ->
            appendLine("$eventName: -${formatMoney(discount)}원")
        }
    }

    private fun getTotalBenefitMessage(totalBenefit: Money): String = buildString {
        appendDoubleLine(Header.TotalBenefit)

        if (totalBenefit != Money.ZERO) append("-")
        appendLine("${formatMoney(totalBenefit)}원")
    }

    private fun getOrderMenuMessage(countByMenuNames: Map<String, Int>): String = buildString {
        appendDoubleLine(Header.OrderMenu)

        countByMenuNames.forEach { (menuName, count) ->
            appendLine("$menuName ${count}개")
        }
    }

    private fun getTotalOrderAmountMessage(money: Money): String = buildString {
        appendDoubleLine(Header.TotalOrderAmount)
        appendLine("${formatMoney(money)}원")
    }

    private fun getGiftMessage(giftName: String): String = buildString {
        appendDoubleLine(Header.GiftMenu)
        append(giftName)
        val count = if (giftName != NOTHING) " 1개" else ""
        appendLine(count)
    }

    private fun formatMoney(money: Money): String = "%,d".format(money.value)

    companion object {
        private const val INTRODUCE_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."
        private const val PREVIEW_EVENT_MESSAGE_TEMPLATE = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        private const val NOTHING = "없음"
        private const val ERROR_TOKEN = "[ERROR]"
    }

    private enum class Header(private val message: String) {
        OrderMenu("주문 메뉴"),
        TotalOrderAmount("할인 전 총주문 금액"),
        GiftMenu("증정 메뉴"),
        DiscountDetails("혜택 내역"),
        TotalBenefit("총혜택 금액"),
        ExpectedPaymentAfterDiscount("할인 후 예상 결제 금액"),
        DecemberEventBadge("12월 이벤트 배지");

        override fun toString(): String = "<$message>"
    }
}

fun StringBuilder.appendDoubleLine(value: Any?) =
    with(this) {
        appendLine()
        appendLine(value)
    }