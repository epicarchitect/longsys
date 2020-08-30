package application.database.entities.analyse_groups

import androidx.room.Entity
import androidx.room.PrimaryKey
import application.constants.VOID_ID

@Entity(tableName = "analyse_groups")
data class AnalyseGroupEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val name: String = "",
    val description: String = ""
)