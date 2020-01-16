package com.djekagoran.mymovieapp.ui.favorite_crew.favoritecrew

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.djekagoran.mymovieapp.data.DataManager
import com.djekagoran.mymovieapp.data.model.Crew
import javax.inject.Inject

class FavoriteCrewViewModel @Inject constructor(private val appDataManager: DataManager):  ViewModel() {

    private var dataActor: LiveData<List<Crew>>? = null

    fun getActors(): LiveData<List<Crew>>? {
        dataActor = appDataManager.favoriteCrew
        return dataActor
    }

}
