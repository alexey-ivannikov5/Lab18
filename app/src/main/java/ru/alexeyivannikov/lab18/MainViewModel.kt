package ru.alexeyivannikov.lab18

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.net.URL
import java.nio.charset.Charset

class MainViewModel : ViewModel() {

    private var _screenState = MutableLiveData<ScreenState>(ScreenState.Init)
    val screenState: LiveData<ScreenState> = _screenState

    private val scope = CoroutineScope(Dispatchers.Default)
    private val parser = XmlPullParserFactory.newInstance().newPullParser()

    fun loadCurrencies() {
        _screenState.value = ScreenState.Loading
        scope.launch {
            val xml = URL("https://www.cbr.ru/scripts/XML_daily.asp")
                .readText(Charset.forName("Windows-1251"))
            parser.setInput(StringReader(xml))
            val currencyList = ArrayList<CurrencyItem>()
            var currentName = ""
            var currentValue = 0f

            while (parser.eventType != XmlPullParser.END_DOCUMENT) {
                if (parser.eventType == XmlPullParser.START_TAG && parser.name == "Name") {
                    parser.next()
                    currentName = parser.text
                }
                if (parser.eventType == XmlPullParser.START_TAG && parser.name == "Value") {
                    parser.next()
                    currentValue = parser.text.replace(',','.').toFloat()
                    Log.d("XML_TEST", CurrencyItem(currentName, currentValue).toString())
                    currencyList.add(CurrencyItem(currentName, currentValue))
                }
                parser.next()
            }
            withContext(Dispatchers.Main) {
                _screenState.value = ScreenState.Data(currencyList)
            }
        }
    }
}