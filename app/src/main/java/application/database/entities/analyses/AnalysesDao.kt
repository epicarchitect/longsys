package application.database.entities.analyses

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AnalysesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: AnalyseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveList(list: List<AnalyseEntity>)

    @Delete
    fun delete(entity: AnalyseEntity)

    @Query("SELECT * FROM analyses WHERE id = :id")
    fun getEntityById(id: Int): AnalyseEntity?

    @Query("SELECT * FROM analyses WHERE groupId = :groupId")
    fun getLiveEntitiesByGroupId(groupId: Int): LiveData<List<AnalyseEntity>>

    @Query("SELECT * FROM analyses WHERE groupId = :groupId")
    fun getEntitiesByGroupId(groupId: Int): List<AnalyseEntity>

}