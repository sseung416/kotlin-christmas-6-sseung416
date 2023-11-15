package christmas.domain

import christmas.domain.event.Badge
import christmas.domain.user.VisitDate
import christmas.domain.user.menu.Appetizer
import christmas.domain.user.menu.Drink
import christmas.domain.user.menu.MainDish
import christmas.domain.user.menu.Menu
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DecemberEventTest {

    @Test
    fun getBadgeNameOrTest() {
        // given
        val menu = Menu.of("${MainDish.ChristmasPasta.menuName}-10")
        val visitDate = VisitDate.from("1")
        val decemberEvent = DecemberEvent(visitDate, menu)

        // when
        val badgeName = decemberEvent.getBadgeNameOr("")

        // then
        assertThat(badgeName).isEqualTo(Badge.Santa.badgeName)
    }

    @Test
    fun totalBenefitTest() {
        // given
        val menu = Menu.of("${MainDish.TBoneSteak.menuName}-10,${MainDish.ChristmasPasta.menuName}-1")
        val visitDate = VisitDate.from("25")
        val decemberEvent = DecemberEvent(visitDate, menu)

        // when, then
        val expectedBenefit = Drink.Champagne.price + 3400 + 1000
        assertThat(decemberEvent.totalBenefit).isEqualTo(expectedBenefit)
    }

    @Test
    fun getMenuNameAndeCountTest() {
        // given
        val soupCount = 1
        val menu = Menu.of("${Appetizer.MushroomSoup.menuName}-$soupCount")
        val visitDate = VisitDate.from("1")
        val decemberEvent = DecemberEvent(visitDate, menu)

        // when
        val menuName = decemberEvent.getMenuNameAndCount()

        // then
        assertThat(menuName)
            .containsKey(Appetizer.MushroomSoup.menuName)
            .containsValue(soupCount)
    }

    @Test
    fun getOrGiftNameOr() {
        // given
        val menu = Menu.of("${Appetizer.MushroomSoup.menuName}-1")
        val visitDate = VisitDate.from("1")
        val decemberEvent = DecemberEvent(visitDate, menu)

        // when
        val giftName = decemberEvent.getGiftNameOr(NOTHING)

        assertThat(giftName).isEqualTo(NOTHING)
    }

    companion object {
        private const val NOTHING = "없음"
    }
}