package christmas.domain

enum class Gift(val menuName: String, val menuPrice: Int, val range: Iterable<Int>) {
    Champagne(Drink.Champagne.menuName, Drink.Champagne.price, 120_000..Int.MAX_VALUE);

    companion object {
        private const val ERROR_MESSAGE = "일치하는 값이 없는데욤?"

        fun from(money: Int): Gift =
            Gift.entries.findLast { money in it.range } ?: throw IllegalArgumentException(ERROR_MESSAGE)
    }
}