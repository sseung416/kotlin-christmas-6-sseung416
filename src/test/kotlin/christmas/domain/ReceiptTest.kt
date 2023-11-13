package christmas.domain

import christmas.domain.menu.Appetizer
import christmas.domain.menu.Drink
import christmas.domain.menu.MainDish
import christmas.domain.menu.Menu
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReceiptTest {

    @Test
    fun getEventBadgeNameOrTest() {
        val menu = Menu("${MainDish.ChristmasPasta.menuName}-10")
        val visitDate = VisitDate.from("1")
        val receipt = Receipt(visitDate, menu)
        assertThat(receipt.getEventBadgeNameOr("")).isEqualTo(EventBadge.Santa.value)
    }

    @Test
    fun totalBenefitTest() {
        val menu = Menu("${MainDish.TBoneSteak.menuName}-10,${MainDish.ChristmasPasta.menuName}-1")
        val visitDate = VisitDate.from("25")
        val receipt = Receipt(visitDate, menu)

        val expectedBenefit = Drink.Champagne.price + 3400 + 1000
        assertThat(receipt.totalBenefit).isEqualTo(expectedBenefit)
    }

    @Test
    fun getMenuNameAndeCountTest() {
        val soupCount = 1
        val menu = Menu("${Appetizer.MushroomSoup.menuName}-$soupCount")
        val visitDate = VisitDate.from("1")
        val receipt = Receipt(visitDate, menu)

        assertThat(receipt.getMenuNameAndCount())
            .containsKey(Appetizer.MushroomSoup.menuName)
            .containsValue(soupCount)
    }

    @Test
    fun getOrGiftNameOr() {
        val menu = Menu("${Appetizer.MushroomSoup.menuName}-1")
        val visitDate = VisitDate.from("1")
        val receipt = Receipt(visitDate, menu)

        assertThat(receipt.getGiftNameOr(NOTHING)).isEqualTo(NOTHING)
    }

    companion object {
        private const val NOTHING = "없음"
    }
}