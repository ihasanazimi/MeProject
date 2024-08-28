package ir.ha.meproject.ui.activity

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.ha.meproject.R
import ir.ha.meproject.databinding.ActivityMainBinding
import ir.ha.meproject.ui.fragments.temp1.Temp1Fragment
import ir.ha.meproject.utility.base.BaseActivity
import ir.ha.meproject.utility.extensions.addFragmentByAnimation
import ir.ha.meproject.utility.extensions.showToast
import ir.ha.meproject.utility.extensions.withNotNull

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
            showToast(this,path.toString())
        }

        /**
         1 - add intent filter to manifest file
         2 - filtered schema -> [https] links
         3 - after adding this intent filter on the manifest - run this command to shell on the project path {  adb shell am start -W -a android.intent.action.VIEW -d "https://www.example.com/hasanazimi"ir.ha.meproject  }
         **/

    }



}