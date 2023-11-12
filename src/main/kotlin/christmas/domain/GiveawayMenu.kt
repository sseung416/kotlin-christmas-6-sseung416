package christmas.domain

enum class GiveawayMenu(val menuItem: MenuItem, val range: Iterable<Int>) {
    Champagne(Drink.Champagne, 120_000..Int.MAX_VALUE);

    companion object {
        fun from(money: Int): GiveawayMenu? =
            GiveawayMenu.entries.findLast { money in it.range }
    }
}