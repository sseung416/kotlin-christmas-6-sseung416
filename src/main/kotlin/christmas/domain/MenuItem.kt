package christmas.domain

interface MenuItem {

    val menuName: String
    val price: Int

    companion object {
        private val allMenuItems: List<MenuItem> =
            Appetizer.entries.toList() + MainDish.entries.toList() + Dessert.entries.toList() + Drink.entries.toList()

        fun from(menuInput: String): MenuItem =
            allMenuItems.find { menuItem -> menuItem.menuName == menuInput }
                ?: throw IllegalArgumentException(Error.InvalidMenu.message)
    }
}

enum class Appetizer(override val menuName: String, override val price: Int) : MenuItem {
    MushroomSoup("양송이수프", 6_000),
    Tapas("타파스", 5_500),
    CaesarSalad("시저샐러드", 8_000)
}

enum class MainDish(override val menuName: String, override val price: Int) : MenuItem {
    TBoneSteak("티본스테이크", 55_000),
    BarbecueRibs("바비큐립", 54_000),
    SeafoodPasta("해산물파스타", 35_000),
    ChristmasPasta("크리스마스파스타", 25_000)
}

enum class Dessert(override val menuName: String, override val price: Int) : MenuItem {
    ChocolateCake("초코케이크", 15_000),
    IceCream("아이스크림", 5_000)
}

enum class Drink(override val menuName: String, override val price: Int) : MenuItem {
    ZeroCola("제로콜라", 3_000),
    RedWine("레드와인", 60_000),
    Champagne("샴페인", 25_000)
}