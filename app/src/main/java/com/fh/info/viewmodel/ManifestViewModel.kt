package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.utils.ApkInformation.extractManifest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

class ManifestViewModel(application: Application,val applicationInfo: ApplicationInfo):AndroidViewModel(application) {

    private val _manifestFlow = MutableStateFlow("")
    val manifestFlow: StateFlow<String>
        get() = _manifestFlow

    private val _errorFlow = MutableStateFlow("")
    val errorFlow: Flow<String>
        get() = _errorFlow

    init {
        getManifestInfo()
    }


    private fun getManifestInfo() {

        val tags = Pattern.compile(
            "" /*Only for indentation */ +
                    "\\<\\w+\\.+\\S+" + // <xml.yml.zml>
                    "|\\<\\w+\\.+\\S+" + // <xml.yml.zml...nthml
                    "|\\<\\/\\w+.+\\>" + // </xml.yml.zml>
                    "|\\<\\/\\w+\\-+\\S+\\>" + // </xml-yml>
                    "|\\<\\w+\\-+\\S+" + // <xml-yml-zml...nthml
                    "|\\</\\w+>" + // </xml>
                    "|\\</\\w+" + // </xml
                    "|\\<\\w+\\/>" + // <xml/>
                    "|\\<\\w+\\>" +  // <xml>
                    "|\\<\\w+" +  // <xml
                    "|\\<.\\w+" + // <?xml
                    "|\\?\\>" + // ?>
                    "|\\/>", // />
            Pattern.MULTILINE or Pattern.CASE_INSENSITIVE
        )
        val quotations: Pattern = Pattern.compile("\"([^\"]*)\"", Pattern.MULTILINE)


        viewModelScope.launch {
            kotlin.runCatching {
                val format: SpannableString
                val code: String = applicationInfo.extractManifest()!!

                if (code.length >= 15000) {
                    throw Exception("String size $code length is to big to render")
                }
                format = SpannableString(code)
                val matcher: Matcher = tags.matcher(code)
                while (matcher.find()) {
                    format.setSpan(
                        ForegroundColorSpan(Color.parseColor("#000000")),
                        matcher.start(),
                        matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                }

                matcher.usePattern(quotations)

                while (matcher.find()) {
                    format.setSpan(
                        ForegroundColorSpan(Color.parseColor("#000000")),
                        matcher.start(), matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                _manifestFlow.value = format.toString()

            }.getOrElse {
                _errorFlow.value = it.localizedMessage
            }
        }
    }
}