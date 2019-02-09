package dsm.android.v3.ui.applyGoing

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dsm.android.v3.R
import dsm.android.v3.databinding.ActivityApplyGoingDocBinding
import dsm.android.v3.util.DataBindingActivity
import kotlinx.android.synthetic.main.activity_apply_going_doc.*
import org.jetbrains.anko.toast

class ApplyGoingDocActivity: DataBindingActivity<ActivityApplyGoingDocBinding>(), ApplyGoingContract.ApplyGoingDocContract {
    override val layoutId: Int
        get() = R.layout.activity_apply_going_doc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ApplyGoingViewModelFactory(this, intent.getIntExtra("currentItem", 0))
        binding.applyGoingDocViewModel = ViewModelProviders.of(this, factory).get(ApplyGoingViewModel::class.java)
    }

    override fun createListFullWarningToast() { toast("외출신청 수의 초과로 산청할 수 없습니다.") }

    override fun backApplyGoing() = finish()

    override fun setErrorApplyGoingGoDate() { applyGoing_doc_going_date_edit.error = "날짜를 입력하세요." }

    override fun setErrorApplyGoingGoTime() { applyGoing_doc_going_time_edit.error = "일시를 입력하세요." }

    override fun setErrorApplyGoingBackDate() { applyGoing_doc_return_date_edit.error = "날짜를 입력하세요." }

    override fun setErrorApplyGoingBackTime() { applyGoing_doc_return_time_edit.error = "일시를 입력하세요." }

    override fun setErrorApplyGoingReason() { applyGoing_doc_reason_content_edit.error = "사유를 입력하세요." }
}