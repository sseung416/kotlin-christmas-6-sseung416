package christmas.domain.user.menu

import christmas.domain.Money
import christmas.domain.toMoney

class Menu private constructor(val countByMenuItem: Map<MenuItem, Int>) {

    val dessertOrderCount: Int = countByMenuItem.filterKeys { it in Dessert.entries }.values.sum()
    val mainDishOrderCount: Int = countByMenuItem.filterKeys { it in MainDish.entries }.values.sum()

    val totalPrice: Money =
        countByMenuItem.entries.sumOf { (menuItem, count) -> (menuItem.price * count).value }.toMoney()

    companion object {
        fun of(input: String, validator: MenuValidator = MenuValidator()): Menu {
            val countByMenuItem = validator.getValidData(input)
            return Menu(countByMenuItem)
        }
    }
}