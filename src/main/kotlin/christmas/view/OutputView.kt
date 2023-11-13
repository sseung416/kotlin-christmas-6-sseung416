package christmas.view

import christmas.domain.Receipt

class OutputView {

    fun printIntroduce() {
        println(INTRODUCE_MESSAGE)
    }

    fun printReceipt(receipt: Receipt) {
        val message = buildString {
            appendLine(PREVIEW_EVENT_MESSAGE_TEMPLATE.format(receipt.visitDay))

            append(getOrderMenuMessage(receipt.getMenuNameAndCount()))
            append(getTotalOrderAmountMessage(receipt.totalPrice))
            append(getGiftMessage(receipt.getGiftNameOr(NOTHING)))
            append(getsDiscountDetailsMessage(receipt.getAmountByEvent()))
            append(getTotalBenefitMessage(receipt.totalBenefit))
            append(getExpectedPaymentAfterDiscountMessage(receipt.expectedPaymentAfterDiscount))
            append(getEventBadgeMessage(receipt.getEventBadgeNameOr(NOTHING)))
        }
        print(message)
    }

    fun printError(errorMessage: String?) {
        errorMessage?.let { println("$ERROR_TOKEN $it") }
    }

    private fun getEventBadgeMessage(badgeName: String): String = buildString {
        appendDoubleLine(Header.DecemberEventBadge)
        append(badgeName)
    }

    private fun getExpectedPaymentAfterDiscountMessage(money: Int): String = buildString {
        appendDoubleLine(Header.ExpectedPaymentAfterDiscount)
        appendLine("${formatMoney(money)}원")
    }

    private fun getsDiscountDetailsMessage(amountByEvent: Map<String, Int>): String = buildString {
        appendDoubleLine(Header.DiscountDetails)

        if (amountByEvent.isEmpty()) {
            appendLine(NOTHING)
            return@buildString
        }

        amountByEvent.forEach { (eventName, discount) ->
            appendLine("$eventName: -${formatMoney(discount)}원")
        }
    }

    private fun getTotalBenefitMessage(totalBenefit: Int): String = buildString {
        appendDoubleLine(Header.TotalBenefit)

        if (totalBenefit != 0) append("-")
        appendLine("${formatMoney(totalBenefit)}원")
    }

    private fun getOrderMenuMessage(countByMenuNames: Map<String, Int>): String = buildString {
        appendDoubleLine(Header.OrderMenu)

        countByMenuNames.forEach { (menuName, count) ->
            appendLine("$menuName ${count}개")
        }
    }

    private fun getTotalOrderAmountMessage(money: Int): String = buildString {
        appendDoubleLine(Header.TotalOrderAmount)
        appendLine("${formatMoney(money)}원")
    }

    private fun getGiftMessage(giftName: String): String = buildString {
        appendDoubleLine(Header.GiftMenu)
        append(giftName)
        val count = if (giftName != NOTHING) " 1개" else ""
        appendLine(count)
    }

    private fun formatMoney(money: Int): String = "%,d".format(money)

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