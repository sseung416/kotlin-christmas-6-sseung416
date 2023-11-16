package christmas.controller

import christmas.domain.user.menu.Menu
import christmas.domain.DecemberEvent
import christmas.domain.user.VisitDate
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
        outputView.printDecemberEvent(DecemberEvent(visitDate, menu))
        inputView.terminate()
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