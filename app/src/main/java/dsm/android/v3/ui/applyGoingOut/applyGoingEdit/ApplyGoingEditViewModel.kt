package dsm.android.v3.ui.applyGoingOut.applyGoingEdit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import dsm.android.v3.connecter.api
import dsm.android.v3.ui.applyGoingOut.applyGoingLog.ApplyGoingLogData.deleteItem
import dsm.android.v3.util.SingleLiveEvent
import dsm.android.v3.util.getToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class ApplyGoingEditViewModel(application: Application) : AndroidViewModel(application) {

    private val dateFormat = SimpleDateFormat("MM/dd")
    private val sendDateFormat = SimpleDateFormat("MM-dd")

    val applyGoingGoDate = MutableLiveData<String>()
    val applyGoingGoTime = MutableLiveData<String>()
    val applyGoingReason = MutableLiveData<String>()

    val applyGoingGoDateError = MutableLiveData<String>()
    val applyGoingGoTimeError = MutableLiveData<String>()
    val applyGoingReasonError = MutableLiveData<String>()

    val createShortToastSingleLiveEvent = SingleLiveEvent<String>()
    val backApplyGoingSingleLiveEvent = SingleLiveEvent<Any>()

    init {
        setDataText()
    }

    private fun setDataText() {
        val idx = deleteItem.date.indexOf(" ")
        applyGoingGoDate.value = createSetDateString(deleteItem.date.substring(0 until idx))
        applyGoingGoTime.value = deleteItem.date.substring(idx + 1)
        applyGoingReason.value = deleteItem.reason
    }

    private fun createSendDateString(date: String): String = sendDateFormat.format(dateFormat.parse(date))
    private fun createSetDateString(date: String): String = dateFormat.format(sendDateFormat.parse(date))

    fun applyGoingEditClickEdit() {
        if (applyGoingGoDate.value.isNullOrBlank() or !applyGoingGoDate.value!!.matches(Regex("[0-1]\\d/[0-3]\\d")))
            applyGoingGoDateError.value = "MM/DD 포맷에 맞춰 정확한 날짜를 입력해주세요."
        else applyGoingGoDateError.value = null
        if (applyGoingGoTime.value.isNullOrBlank() or !applyGoingGoTime.value!!.matches(Regex("[0-2]\\d:[0-5]\\d\\s~\\s[0-2]\\d:[0-5]\\d")))
            applyGoingGoTimeError.value = "hh:mm ~ hh:mm 포맷에 맞춰 정확한 시간을 입력해주세요."
        else applyGoingGoTimeError.value = null
        if (applyGoingReason.value.isNullOrBlank()) applyGoingReasonError.value = "사유를 입력하세요."
        else applyGoingReasonError.value = null
        if (applyGoingGoDateError.value.isNullOrBlank() and applyGoingGoTimeError.value.isNullOrBlank() and applyGoingReasonError.value.isNullOrBlank()) {
            api.editGoingOut(
                getToken(getApplication()), hashMapOf(
                    "applyId" to deleteItem.id
                    , "date" to "${createSendDateString(applyGoingGoDate.value!!)} ${applyGoingGoTime.value}"
                    , "reason" to "${applyGoingReason.value}"
                )
            ).enqueue(object : Callback<Unit> {

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    createShortToastSingleLiveEvent.value =
                        when (response.code()) {
                            201 -> "외출신청 수정에 성공했습니다."
                            204 -> "외출신청 수정 가능시간이 아닙니다."
                            403 -> "외출신청 수정 권한이 없습니다."
                            500 -> "로그인이 필요합니다."
                            else -> "오류코드: ${response.code()}"
                        }
                    backApplyGoingSingleLiveEvent.call()
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    createShortToastSingleLiveEvent.value = "오류가 발생했습니다."
                }
            })
        }
    }

    fun applyGoingEditClickCancel() {
        api.deleteGoingOut(getToken(getApplication()), hashMapOf("applyId" to deleteItem.id))
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    createShortToastSingleLiveEvent.value =
                        when (response.code()) {
                            200 -> "외출신청 취소에 성공했습니다."
                            204 -> "존재하지 않는 외출신청입니다."
                            409 -> "외출신청 불가 시간입니다."
                            500 -> "로그인이 필요합니다."
                            else -> "오류코드: ${response.code()}"
                        }
                    backApplyGoingSingleLiveEvent.call()
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    createShortToastSingleLiveEvent.value = "오류가 발생했습니다."
                }
            })
    }

    fun applyGoingEditClickBack() = backApplyGoingSingleLiveEvent.call()
}