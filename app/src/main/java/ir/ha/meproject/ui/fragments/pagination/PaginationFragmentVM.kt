package ir.ha.meproject.ui.fragments.pagination

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import ir.ha.meproject.utility.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PaginationFragmentVM : BaseViewModel() {


    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing

    val pager = Pager(PagingConfig(pageSize = 20)) { SamplePagingSource() }.flow.cachedIn(viewModelScope)


    fun refresh() {
        Log.i(TAG, "refresh function is called: ")
        _isRefreshing.value = true
        pager // This forces the PagingSource to refresh
        _isRefreshing.value = false
    }


    fun showLoading(show : Boolean){
        _isRefreshing.value = show
    }




}