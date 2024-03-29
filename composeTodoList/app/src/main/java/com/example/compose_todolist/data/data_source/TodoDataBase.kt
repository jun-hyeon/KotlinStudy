package com.example.compose_todolist.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose_todolist.domain.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDataBase : RoomDatabase(){
    abstract fun todoDao() : TodoDao
}