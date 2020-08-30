package longsys.database.entities.drugs

import androidx.room.Entity
import androidx.room.PrimaryKey
import longsys.constants.UnitType
import longsys.constants.VOID_ID

@Entity(tableName = "drugs")
data class DrugEntity(
    @PrimaryKey
    val id: Int = VOID_ID,
    val groupId: Int = VOID_ID,
    val name: String = "",
    val description: String = "",
    val unitType: Int = UnitType[UnitType.AMPOULES]
    //val count: Double = 0.0
)