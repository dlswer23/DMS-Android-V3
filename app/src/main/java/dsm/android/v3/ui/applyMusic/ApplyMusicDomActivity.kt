package dsm.android.v3.ui.applyMusic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import dsm.android.v3.R
import kotlinx.android.synthetic.main.activity_apply_music_dom.*

class ApplyMusicDomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_apply_music_dom)
        setSupportActionBar(apply_musicDom_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "기상음악 신청"

        apply_musicDom_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        findNavController(R.id.applyMusic_fragmentContainer)
    }
}
