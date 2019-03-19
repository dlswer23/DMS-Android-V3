package dsm.android.v3.ui.bugReportDialog

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dsm.android.v3.R
import dsm.android.v3.databinding.DialogBugReportBinding
import dsm.android.v3.util.DataBindingDialogFragment
import kotlinx.android.synthetic.main.dialog_bug_report.*
import org.jetbrains.anko.support.v4.toast

class BugReportDialogFragment: DataBindingDialogFragment<DialogBugReportBinding>(), BugReportContract {

    override val layoutId: Int
        get() = R.layout.dialog_bug_report

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val factory = BugReportViewModelFactory(this)
        binding.bugReportViewModel = ViewModelProviders.of(this, factory).get(BugReportViewModel::class.java)
        return rootView
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun exitBugReport() = dialog.dismiss()

    override fun createShortToast(text: String) = toast(text).show()

    override fun flagBugTitleBlankError() { bug_report_dialog_title_edit.error = "제목을 입력하세요." }
    override fun flagBugContentBlankError() { bug_report_dialog_content_edit.error = "내용을 입력하세요." }
}