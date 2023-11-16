package christmas.domain

enum class Error(val message: String) {
    InvalidDate("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    InvalidMenu("유효하지 않은 주문입니다. 다시 입력해 주세요.")
}