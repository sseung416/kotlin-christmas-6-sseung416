package christmas.domain

@JvmInline
value class Money(val value: Int) {

    init {
        require(value >= 0) { "음수 값은 입력할 수 없습니다." }
    }

    operator fun plus(money: Money): Money = Money(this.value + money.value)

    operator fun plus(int: Int): Money = Money(this.value + int)

    operator fun minus(money: Money): Money = Money(this.value - money.value)

    operator fun times(count: Int): Money = Money(this.value * count)

    override fun toString(): String = value.toString()

    companion object {
        val ZERO = Money(0)
    }
}

fun Int.toMoney(): Money = Money(this)
