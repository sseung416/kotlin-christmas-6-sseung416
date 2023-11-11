package christmas.domain

class Menu(menuInput: String) {

    private val countByMenuItem: Map<MenuItem, Int>

    val dessertOrderCount get() = countByMenuItem.filterKeys { it in Dessert.entries }.values.sum()
    val mainDishOrderCount get() = countByMenuItem.filterKeys { it in MainDish.entries }.values.sum()

    init {
        val pairs = menuInput.split(DELIMITER).map { menuNameAndCountinput ->
            runCatching {
                val list = menuNameAndCountinput.split(SEPARATOR)
                MenuItem.from(list[0]) to list[1].toValidInt(Error.InvalidMenu.message)
            }.getOrElse {
                throw IllegalArgumentException(Error.InvalidMenu.message)
            }
        }

        val (menuItems, counts) = pairs.unzip()
        validateDuplicatedMenu(menuItems)
        validateCount(counts)
        validateMaxOrderCount(counts)
        validateOnlyDrink(menuItems)

        countByMenuItem = pairs.associate { it }
    }

    private fun validateDuplicatedMenu(menuItems: List<MenuItem>) {
        require(menuItems.toSet().size == menuItems.size) { Error.InvalidMenu.message }
    }

    private fun validateCount(counts: List<Int>) {
        require(counts.all { it >= 1 }) { Error.InvalidMenu.message }
    }

    private fun validateMaxOrderCount(count: List<Int>) {
        require(count.sum() <= 20) { Error.InvalidMenu.message }
    }

    private fun validateOnlyDrink(menuItems: List<MenuItem>) {
        require(menuItems.any { it !is Drink }) { Error.InvalidMenu.message }
    }

    companion object {
        private const val DELIMITER = ","
        private const val SEPARATOR = "-" // TODO: rename
    }
}