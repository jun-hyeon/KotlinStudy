package com.bignerdranch.criminalintent

import androidx.lifecycle.ViewModel
import com.bignerdranch.criminalintent.database.CrimeRepository

class CrimeListViewModel : ViewModel() {


    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()

    fun addCrime(crime: Crime){
        crimeRepository.addCrime(crime)
    }

}