package christmas.domain.event

enum class Badge(val value: String, val range: IntRange) { // todo value naming 변경
    Star("별", 5000..<10000),
    Tree("트리", 10000..<20000),
    Santa("산타", 20000..Int.MAX_VALUE);

    companion object {
        private const val ERROR_MESSAGE = "매칭되는게 없는데염"

        fun from(money: Int): Badge = find(money) ?: throw IllegalArgumentException(ERROR_MESSAGE)

        fun find(money: Int): Badge? =
            Badge.entries.findLast { money in it.range }
    }
}