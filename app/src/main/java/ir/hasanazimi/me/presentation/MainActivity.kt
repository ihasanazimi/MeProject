package ir.hasanazimi.me.presentation

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.hasanazimi.me.R
import ir.hasanazimi.me.common.base.BaseActivity
import ir.hasanazimi.me.common.extensions.showToast
import ir.hasanazimi.me.common.extensions.withNotNull
import ir.hasanazimi.me.databinding.ActivityMainBinding

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