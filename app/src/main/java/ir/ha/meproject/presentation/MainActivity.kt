package ir.ha.meproject.presentation

import android.Manifest
import android.content.Intent
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.R
import ir.ha.meproject.common.base.BaseActivity
import ir.ha.meproject.common.extensions.isTIRAMISUPlus
import ir.ha.meproject.common.extensions.isUPSIDE_DOWN_CAKE_Plus
import ir.ha.meproject.common.security_and_permissions.PermissionUtils
import ir.ha.meproject.databinding.ActivityMainBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


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

        EventBus.getDefault().register(this)
        binding.downloadBtn.title("Press to download file" , "please waite to complete download..")

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
            val link = "https://nicmusic.musicmelnet.com/upload/2024/08/20/Relaxing%20Music%20for%20Stress%20Relief%201.mp3"
            val fileName = link.substringBeforeLast('.', "").substringAfterLast('/')
            val format = link.substringAfterLast('.', "")
            putExtra("fileUrl", link)
            putExtra("fileName", "$fileName.${format}")
        }
        startService(intent)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event : DownloadService.Companion.MessageEvent){
        Log.e(this@MainActivity::class.java.simpleName, "onMessageEvent: ${event.message.name} ", )
        when(event.message){

            DownloadService.Companion.MessageEnum.STARTED -> {
                showMessage( "Download is " + DownloadService.Companion.MessageEnum.STARTED.name)
                binding.downloadBtn.showLoading(true)
            }
            DownloadService.Companion.MessageEnum.COMPLETED -> {
                showMessage("Download is " + DownloadService.Companion.MessageEnum.COMPLETED.name)
                binding.downloadBtn.showLoading(false)
            }
            DownloadService.Companion.MessageEnum.FAILED -> {
                showMessage("Download is " + DownloadService.Companion.MessageEnum.FAILED.name)
                binding.downloadBtn.showLoading(false)
            }
            else -> Log.e(this@MainActivity::class.java.simpleName, "onMessageEvent: ", )

        }
    }

    override fun listeners() {
        super.listeners()

        binding.downloadBtn.clickListener {
            if (PermissionUtils.arePermissionsGranted(this, contentPermissions)) {
                downloadFile()
            } else {
                checkPermission()
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}