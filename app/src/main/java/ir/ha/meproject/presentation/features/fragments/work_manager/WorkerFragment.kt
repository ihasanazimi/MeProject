package ir.ha.meproject.presentation.features.fragments.work_manager

import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.databinding.FragmentTemp1Binding

class WorkerFragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate) {

    private lateinit var workManager: WorkManager

    override fun listeners() {
        super.listeners()

        binding.btn.setOnClickListener {
            setOnwTimeWorkRequest()
        }


    }

    private fun setOnwTimeWorkRequest(){
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
        workManager = WorkManager.getInstance(requireContext())
        workManager.enqueue(uploadRequest)
        workManager.getWorkInfoByIdLiveData(uploadRequest.id).observe(viewLifecycleOwner){
            binding.textView.text = it.state.name
            Log.i(TAG, "setOnwTimeWorkRequest: ${it.state.name}")
        }
    }




}