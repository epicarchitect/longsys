package longsys.database.entities.analyse_groups

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AnalyseGroupsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: AnalyseGroupEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveList(list: List<AnalyseGroupEntity>)

    @Delete
    fun delete(entity: AnalyseGroupEntity)

    @Query("SELECT * FROM analyse_groups")
    fun getLiveEntities(): LiveData<List<AnalyseGroupEntity>>

    @Query("SELECT * FROM analyse_groups")
    fun getEntities(): List<AnalyseGroupEntity>

    @Query("SELECT * FROM analyse_groups WHERE id = :id")
    fun getEntityById(id: Int): AnalyseGroupEntity?

}