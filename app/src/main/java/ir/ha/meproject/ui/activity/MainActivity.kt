package ir.ha.meproject.ui.activity

import android.Manifest
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.R
import ir.ha.meproject.databinding.ActivityMainBinding
import ir.ha.meproject.utility.base.BaseActivity
import ir.ha.meproject.utility.extensions.isTIRAMISUPlus
import ir.ha.meproject.utility.extensions.isUPSIDE_DOWN_CAKE_Plus
import ir.ha.meproject.utility.extensions.singleClick
import ir.ha.meproject.utility.security.PermissionUtils


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val contentPermissions = if (isTIRAMISUPlus()){
        arrayOf(Manifest.permission.MANAGE_DOCUMENTS,Manifest.permission.MANAGE_MEDIA,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_VIDEO)
    }else {
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private val notificationPermission = if (isTIRAMISUPlus()) arrayOf(Manifest.permission.POST_NOTIFICATIONS) else arrayOf()

    private val servicePermissions = if (isUPSIDE_DOWN_CAKE_Plus()) arrayOf(Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC) else arrayOf()

    private val totalPermissions = notificationPermission + notificationPermission + servicePermissions

    override fun initializing() {
        super.initializing()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun checkPermission() {

        PermissionUtils.requestPermissions(
            this,
            totalPermissions,
            1001, object : PermissionUtils.PermissionResultCallback {
                override fun onPermissionGranted() {
                    downloadFile()
                }
                override fun onPermissionDenied() {
                    showErrorMessage("ooops")
                }
            }
        )
    }

    private fun downloadFile() {
        val intent = Intent(this, DownloadService::class.java).apply {
            val link = "https://dl.motionlab.ir/Music/%D8%A7%D9%81%DA%A9%D8%AA%20%D8%B5%D9%88%D8%AA%DB%8C/01_notification_mobile_www.motionlab.ir.wav"
            val fileName = link.substringBeforeLast('.', "").substringAfterLast('/')
            val format = link.substringAfterLast('.', "")
            putExtra("fileUrl", link)
            putExtra("fileName", "$fileName.${format}")
        }
        startService(intent)
        showMessage("Downloading...")
    }


    override fun listeners() {
        super.listeners()

        binding.downloadBtn.singleClick {
            if (PermissionUtils.arePermissionsGranted(this, contentPermissions)){
                downloadFile()
            }else{
                checkPermission()
            }
        }

    }




}