package christmas.view

import camp.nextstep.edu.missionutils.Console

class InputView {

    fun readVisitDate(): String {
        println(INPUT_VISIT_DATE_MESSAGE)
        return Console.readLine()
    }

    fun readInputMenu(): String {
        println(INPUT_MENU)
        return Console.readLine()
    }

    fun terminate() = Console.close()

    companion object {
        private const val INPUT_VISIT_DATE_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"
        private const val INPUT_MENU = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"
    }
}