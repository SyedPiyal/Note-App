package com.piyal.mvvmnoteapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.piyal.mvvmnoteapp.api.NotesAPI
import com.piyal.mvvmnoteapp.models.NoteRequest
import com.piyal.mvvmnoteapp.models.NoteResponse
import com.piyal.mvvmnoteapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
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

        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.createNote(noteRequest)
        handleResponse(response,"Note Created")
    }


    suspend fun deleteNote(noteId:String){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.deleteNote(noteId)
        handleResponse(response,"Note Deleted")
    }
    suspend fun updateNote(noteId:String, noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.updateNote(noteId,noteRequest)
        handleResponse(response,"Note Updated")
    }
    private fun handleResponse(response: Response<NoteResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {

            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}