package longsys.database.entities.analyses

import androidx.room.Entity
import androidx.room.PrimaryKey
import longsys.constants.VOID_ID

@Entity(tableName = "analyses")
data class AnalyseEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val groupId: Int = VOID_ID,
    val name: String = "",
    val description: String = ""
)
