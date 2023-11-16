package christmas.domain.user.menu

import christmas.domain.Error
import christmas.domain.user.toValidInt

class MenuValidator {

    fun getValidData(value: String): Map<MenuItem, Int> {
        val menu = parseMenuInput(input = value)
        val (menuItems, menuCounts) = menu.unzip()
        validateDuplicatedMenu(menuItems)
        validateOrderCount(menuCounts)
        validateOnlyDrink(menuItems)
        return menu.associate { it }
    }

    private fun parseMenuInput(input: String): List<Pair<MenuItem, Int>> {
        val orders = input.split(ORDER_DELIMITER)
        return orders.map { order -> parseMenuItemAndCount(order) }
    }

    private fun parseMenuItemAndCount(order: String): Pair<MenuItem, Int> =
        runCatching {
            order.split(MENU_DELIMITER).run {
                val menuName = this[0]
                val count = this[1].toValidInt()
                MenuItem.from(menuName) to count
            }
        }.getOrElse {
            throw IllegalArgumentException(Error.InvalidMenu.message)
        }

    private fun validateDuplicatedMenu(menuItems: List<MenuItem>) {
        require(menuItems.toSet().size == menuItems.size) { Error.InvalidMenu.message }
    }

    private fun validateOrderCount(menuCounts: List<Int>) {
        val orderCount = menuCounts.sum()
        require(!menuCounts.contains(0)) { Error.InvalidMenu.message }
        require(orderCount in MIN_ORDER_COUNT..MAX_ORDER_COUNT) { Error.InvalidMenu.message }
    }

    private fun validateOnlyDrink(menuItems: List<MenuItem>) {
        require(isNotOnlyDrink(menuItems)) { Error.InvalidMenu.message }
    }

    private fun isNotOnlyDrink(menuItems: List<MenuItem>): Boolean = menuItems.any { menuItem -> menuItem !is Drink }

    companion object {
        private const val ORDER_DELIMITER = ","
        private const val MENU_DELIMITER = "-"

        private const val MIN_ORDER_COUNT = 1
        private const val MAX_ORDER_COUNT = 20
    }
}