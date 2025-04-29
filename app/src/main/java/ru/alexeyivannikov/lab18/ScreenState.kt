package ru.alexeyivannikov.lab18

sealed class ScreenState {
    data object Init: ScreenState()
    data object Loading: ScreenState()

    data class Data(val data: List<CurrencyItem>): ScreenState()
}