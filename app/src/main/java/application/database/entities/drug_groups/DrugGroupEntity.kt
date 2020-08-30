package application.database.entities.drug_groups

import androidx.room.Entity
import androidx.room.PrimaryKey
import application.constants.VOID_ID

@Entity(tableName = "drug_groups")
data class DrugGroupEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val name: String = ""
)