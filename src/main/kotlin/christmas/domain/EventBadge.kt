package christmas.domain

enum class EventBadge(val value: String, val range: Iterable<Int>) {
    Star("별", 5000..<10000),
    Tree("트리", 10000..<20000),
    Santa("산타", 20000..Int.MAX_VALUE);

    companion object {

        // todo: null return 리팩토링하기, sortedDescending 리팩
        fun from(money: Int): EventBadge? =
            EventBadge.entries.sortedDescending().find { money in it.range }
    }
}