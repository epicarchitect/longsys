package application.database.entities.drug_groups

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DrugGroupsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: DrugGroupEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveList(list: List<DrugGroupEntity>)

    @Delete
    fun delete(entity: DrugGroupEntity)

    @Query("SELECT * FROM drug_groups")
    fun getLiveEntities(): LiveData<List<DrugGroupEntity>>

    @Query("SELECT * FROM drug_groups")
    fun getEntities(): List<DrugGroupEntity>

    @Query("SELECT * FROM drug_groups WHERE id = :id")
    fun getEntityById(id: Int): DrugGroupEntity?

}