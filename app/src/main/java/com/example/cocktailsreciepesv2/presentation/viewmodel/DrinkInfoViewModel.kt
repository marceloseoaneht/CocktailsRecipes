package com.example.cocktailsreciepesv2.presentation.viewmodel

import androidx.lifecycle.*
import com.example.cocktailsreciepesv2.domain.model.CustomError
import com.example.cocktailsreciepesv2.domain.model.DrinkInfo
import com.example.cocktailsreciepesv2.domain.repository.DrinkInfoRepository
import com.example.cocktailsreciepesv2.domain.model.Resource
import com.example.cocktailsreciepesv2.presentation.util.toResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DrinkInfoViewModel(private val drinkInfoRepository: DrinkInfoRepository) : ViewModel() {

    //region Livedata
    val drinkInfo: LiveData<DrinkInfo>
        get() = _drinkInfo
    private val _drinkInfo = MutableLiveData<DrinkInfo>()

    val loading: LiveData<Boolean>
        get() = _loading
    private val _loading = MutableLiveData<Boolean>()

    val error: LiveData<Int>
        get() = _error
    private val _error = MutableLiveData<Int>()
    //endregion

    fun getDrinkInfo(drinkId: Int) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = drinkInfoRepository.getDrinkInfo(drinkId)
                when (res) {
                    is Resource.Success -> {
                        _drinkInfo.postValue(res.data!!)
                    }
                    is Resource.Error -> {
                        _error.postValue(res.error.toResource())
                    }
                }

            } catch (e: Exception) {
                _error.postValue(CustomError.DATA_ERROR.toResource())
            }
            _loading.postValue(false)
        }
    }

}