package com.fh.info.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fh.info.R
import com.fh.info.data.model.CertificateModel
import com.fh.info.utils.ApkInformation.getActivities
import com.fh.info.utils.ApkInformation.getCertificate
import com.fh.info.utils.ApkInformation.getDexClasses
import com.fh.info.utils.AppResource
import com.jaredrummler.apkparser.model.AndroidComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InfoItemViewModel(app: Application, var applicationInfo: ApplicationInfo) :
    AndroidViewModel(app) {





    private val _activityFlow = MutableStateFlow<AppResource<MutableList<AndroidComponent>>>(AppResource.Loading())
    val activityFlow: StateFlow<AppResource<MutableList<AndroidComponent>>>
        get() = _activityFlow



    private val _certificateFlow = MutableStateFlow<AppResource<MutableList<CertificateModel>>>(AppResource.Loading())
    val certificateFlow: StateFlow<AppResource<MutableList<CertificateModel>>>
        get() = _certificateFlow


    private val _dexFlow = MutableStateFlow<AppResource<MutableList<net.dongliu.apk.parser.bean.DexClass>>>(AppResource.Loading())
    val dexFlow: StateFlow<AppResource<MutableList<net.dongliu.apk.parser.bean.DexClass>>>
        get() = _dexFlow







    init {
        getActivitiesInfo()
        getCertificateInfo()
        getClassesInfo()

    }


    private fun getActivitiesInfo() {

        kotlin.runCatching {
            val activities = applicationInfo.getActivities().apply {
                Log.i("TAG", "getActivitiesInfo:${this.size} ")
                sortBy {
                    it.name.substring(it.name.lastIndexOf("."))
                }
            }

            _activityFlow.value=AppResource.Success(activities)
        }.getOrElse {
            _activityFlow.value=AppResource.Error(it.localizedMessage)
        }


    }


    private fun getCertificateInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                val context = getApplication<Application>().applicationContext
                val certificate = applicationInfo.getCertificate()

                val list = mutableListOf(
                    CertificateModel(
                        context.getString(R.string.signAlgorithm),
                        certificate.signAlgorithm
                    ),
                    CertificateModel(
                        context.getString(R.string.signAlgorithmOID),
                        certificate.signAlgorithmOID
                    ),
                    CertificateModel(
                        context.getString(R.string.certBase64Md5),
                        certificate.certBase64Md5
                    ),
                    CertificateModel(context.getString(R.string.certMd5), certificate.certMd5),
                    CertificateModel(
                        context.getString(R.string.start_date),
                        certificate.startDate.toString()
                    ),
                    CertificateModel(
                        context.getString(R.string.end_date),
                        certificate.endDate.toString()
                    )
                )

                _certificateFlow.value = AppResource.Success(list)

            }.getOrElse {
                _certificateFlow.value=AppResource.Error(it.message.toString())
            }
        }

    }


    private fun getClassesInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                _dexFlow.value = AppResource.Success(applicationInfo.getDexClasses())
            }.getOrElse {
                _dexFlow.value=AppResource.Error(it.message.toString())
            }
        }
    }


}