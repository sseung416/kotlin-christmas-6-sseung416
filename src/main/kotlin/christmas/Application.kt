package christmas

import christmas.controller.ChristmasEventController
import christmas.view.InputView
import christmas.view.OutputView

fun main() {
    val outputView = OutputView()
    val inputView = InputView()
    val controller = ChristmasEventController(outputView, inputView)
    controller.start()
}
