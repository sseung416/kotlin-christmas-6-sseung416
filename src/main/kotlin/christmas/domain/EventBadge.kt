package christmas.domain

enum class EventBadge(val value: String, val range: Iterable<Int>) {
    Star("별", 5000..<10000),
    Tree("트리", 10000..<20000),
    Santa("산타", 20000..Int.MAX_VALUE);

    companion object {
        private const val ERROR_MESSAGE = "매칭되는게 없는데염"

        fun from(money: Int): EventBadge =
            EventBadge.entries.findLast { money in it.range } ?: throw IllegalArgumentException(ERROR_MESSAGE)
    }
}