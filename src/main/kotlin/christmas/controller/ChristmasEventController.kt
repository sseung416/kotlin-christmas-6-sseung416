package christmas.controller

import christmas.domain.menu.Menu
import christmas.domain.Receipt
import christmas.domain.VisitDate
import christmas.view.InputView
import christmas.view.OutputView

class ChristmasEventController(
    private val outputView: OutputView,
    private val inputView: InputView
) {

    fun start() {
        outputView.printIntroduce()
        val visitDate = getVisitDate()
        val menu = getMenu()
        outputView.printReceipt(Receipt(visitDate, menu))
    }

    private fun getVisitDate(): VisitDate =
        try {
            VisitDate.from(inputView.readVisitDate())
        } catch (exception: IllegalArgumentException) {
            outputView.printError(exception.message)
            getVisitDate()
        }

    private fun getMenu(): Menu =
        try {
            Menu.of(inputView.readInputMenu())
        } catch (exception: IllegalArgumentException) {
            outputView.printError(exception.message)
            getMenu()
        }
}