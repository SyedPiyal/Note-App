package com.piyal.mvvmnoteapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.piyal.mvvmnoteapp.api.NotesAPI
import com.piyal.mvvmnoteapp.models.NoteRequest
import com.piyal.mvvmnoteapp.models.NoteResponse
import com.piyal.mvvmnoteapp.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class NoteRepository @Inject constructor(private val notesAPI: NotesAPI){

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData : LiveData<NetworkResult<List<NoteResponse>>>
        get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData : LiveData<NetworkResult<String>>
        get() = _statusLiveData

    suspend fun getNotes(){

        _notesLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.getNotes()

        if (response.isSuccessful && response.body() != null) {
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("Something Went Wrong")))
        } else {
            _notesLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun createNote(noteRequest: NoteRequest){

    }
    suspend fun deleteNote(noteId:String){

    }
    suspend fun updateNote(noteId:String, noteRequest: NoteRequest){

    }
}