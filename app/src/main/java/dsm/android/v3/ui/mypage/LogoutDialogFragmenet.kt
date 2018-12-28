package dsm.android.v3.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dsm.android.v3.R
import dsm.android.v3.databinding.DialogLogoutBinding
import dsm.android.v3.util.DataBindingDialogFragment

class LogoutDialogFragmenet: DataBindingDialogFragment<DialogLogoutBinding>(), MyPageContract.LogoutContract{
    override val layoutId: Int
        get() = R.layout.dialog_logout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.myPageViewModel = MyPageViewModel(this)
        return rootView
    }

    override fun exitLogut() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}