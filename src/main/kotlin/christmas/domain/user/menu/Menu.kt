package christmas.domain.user.menu


class Menu private constructor(val countByMenuItem: Map<MenuItem, Int>) {

    val dessertOrderCount get() = countByMenuItem.filterKeys { it in Dessert.entries }.values.sum()
    val mainDishOrderCount get() = countByMenuItem.filterKeys { it in MainDish.entries }.values.sum()

    val totalPrice get() = countByMenuItem.entries.sumOf { (menuItem, count) -> menuItem.price * count }

    companion object {
        fun of(input: String, validator: MenuValidator = MenuValidator()): Menu {
            val countByMenuItem = validator.getValidData(input)
            return Menu(countByMenuItem)
        }
    }
}