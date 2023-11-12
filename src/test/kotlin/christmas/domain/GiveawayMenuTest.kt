package christmas.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class GiveawayMenuTest {

    @Test
    fun `12만 원 이상 주문했을 때, 샴페인을 증정한다`() {
        val money = 120_000
        val giveawayMenu = GiveawayMenu.from(money)
        assertThat(giveawayMenu.menuName).isEqualTo(Drink.Champagne.menuName)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1_000, 100_000])
    fun `12만 원 미만 주문했을 때, 아무 것도 증정되지 않는다`(money: Int){
        val giveawayMenu = GiveawayMenu.from(money)
        assertThat(giveawayMenu).isNull()
    }
}