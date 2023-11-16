package christmas.domain.user.menu

import christmas.domain.Error
import christmas.domain.Money

interface MenuItem {

    val menuName: String
    val price: Money

    companion object {
        private val allMenuItems: List<MenuItem> =
            Appetizer.entries.toList() + MainDish.entries.toList() + Dessert.entries.toList() + Drink.entries.toList()

        fun from(menuInput: String): MenuItem =
            allMenuItems.find { menuItem -> menuItem.menuName == menuInput }
                ?: throw IllegalArgumentException(Error.InvalidMenu.message)
    }
}

enum class Appetizer(override val menuName: String, override val price: Money) : MenuItem {
    MushroomSoup("양송이수프", Money(6_000)),
    Tapas("타파스", Money(5_500)),
    CaesarSalad("시저샐러드", Money(8_000))
}

enum class MainDish(override val menuName: String, override val price: Money) : MenuItem {
    TBoneSteak("티본스테이크", Money(55_000)),
    BarbecueRibs("바비큐립", Money(54_000)),
    SeafoodPasta("해산물파스타", Money(35_000)),
    ChristmasPasta("크리스마스파스타", Money(25_000))
}

enum class Dessert(override val menuName: String, override val price: Money) : MenuItem {
    ChocolateCake("초코케이크", Money(15_000)),
    IceCream("아이스크림", Money(5_000))
}

enum class Drink(override val menuName: String, override val price: Money) : MenuItem {
    ZeroCola("제로콜라", Money(3_000)),
    RedWine("레드와인", Money(60_000)),
    Champagne("샴페인", Money(25_000))
}