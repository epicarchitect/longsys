package longsys.database.entities.drugs

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DrugsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: DrugEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveList(list: List<DrugEntity>)

    @Delete
    fun delete(entity: DrugEntity)

    @Query("SELECT * FROM drugs WHERE id = :id")
    fun getEntityById(id: Int): DrugEntity?

    @Query("SELECT * FROM drugs WHERE groupId = :groupId")
    fun getLiveEntitiesByGroupId(groupId: Int): LiveData<List<DrugEntity>>

    @Query("SELECT * FROM drugs WHERE groupId = :groupId")
    fun getEntitiesByGroupId(groupId: Int): List<DrugEntity>

}