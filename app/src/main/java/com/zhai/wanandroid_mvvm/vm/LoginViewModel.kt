package com.zhai.wanandroid_mvvm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beyondsoft.mvvm.base.BaseViewModel
import com.zhai.wanandroid_mvvm.model.bean.User
import com.zhai.wanandroid_mvvm.model.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.beyondsoft.mvvm.Result

class LoginViewModel : BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }
    private val _uiState : MutableLiveData<LoginUIModel> = MutableLiveData()
    val uiState : LiveData<LoginUIModel>
    get() = _uiState

    fun login(userName: String, pwd: String) {
        // Dispatchers.Default默认是在子线程
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {showLoading()}
            val result = loginRepository.login(userName, pwd)
            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    updateUIStatus(showDialog = false, successData = result.data)
                } else if (result is Result.Error) {
                    updateUIStatus(showDialog = false, errorMsg = result.exception.message)
                }
            }
        }
    }

    fun register(userName: String, pwd: String) {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {showLoading()}
            val result = loginRepository.register(userName, pwd)
            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    updateUIStatus(isLogin = false, showDialog = false, successData = result.data)
                } else if(result is Result.Error){
                    updateUIStatus(isLogin = false, showDialog = false, errorMsg = result.exception.message)
                }
            }
        }
    }

    private fun showLoading() {
        updateUIStatus(true)
    }

    private fun updateUIStatus(isLogin: Boolean = true, showDialog: Boolean = false, errorMsg: String? = null, successData: User? = null) {
        _uiState.value = LoginUIModel(isLogin, showDialog, errorMsg, successData)
    }

    data class LoginUIModel(
        var isLogin : Boolean,
        var showDialog: Boolean,
        val errorMsg: String?,
        val successData: User?
    )
}