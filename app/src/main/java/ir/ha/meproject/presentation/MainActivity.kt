package ir.ha.meproject.presentation

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.ha.meproject.R
import ir.ha.meproject.databinding.ActivityMainBinding
import ir.ha.meproject.presentation.features.fragments.temp1.Temp1Fragment
import ir.ha.meproject.common.base.BaseActivity
import ir.ha.meproject.common.extensions.addFragmentByAnimation
import ir.ha.meproject.common.extensions.showToast
import ir.ha.meproject.common.extensions.withNotNull

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initializing() {
        super.initializing()

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val data = intent.data
        data.withNotNull {
            val path = data?.path
            showToast(this, path.toString())
        }

        /**
        1 - add intent filter to manifest file

        <intent-filter>

        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        addFragmentByAnimation(Temp1Fragment(), Temp1Fragment::class.java.simpleName,true,true,R.id.main)

        }
         */

    }
}