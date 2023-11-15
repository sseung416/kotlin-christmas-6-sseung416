package christmas.domain.event

import christmas.domain.user.menu.Drink

enum class Gift(val menuName: String, val menuPrice: Int, val range: IntRange) {
    Champagne(Drink.Champagne.menuName, Drink.Champagne.price, 120_000..Int.MAX_VALUE);

    companion object {
        private const val ERROR_MESSAGE = "일치하는 값이 없는데욤?"
        const val EVENT_NAME = "증정 이벤트"

        fun from(money: Int): Gift = find(money) ?: throw IllegalArgumentException(ERROR_MESSAGE)

        fun find(money: Int): Gift? = Gift.entries.findLast { money in it.range }
    }
}