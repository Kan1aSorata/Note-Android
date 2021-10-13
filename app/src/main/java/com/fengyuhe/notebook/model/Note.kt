package com.fengyuhe.notebook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) var id : Long = 0,
    @ColumnInfo var create_time : String = "",
    @ColumnInfo var update_time : String = "",
    @ColumnInfo var title : String = "",
    @ColumnInfo var content : String = ""
) : Serializable