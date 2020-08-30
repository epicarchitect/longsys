package application.database.entities.courses

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoursesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: CourseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveList(list: List<CourseEntity>)

    @Query("DELETE FROM courses WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM courses ORDER BY timeStart DESC")
    fun getLiveEntities(): LiveData<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :id")
    fun getLiveEntityById(id: Int): LiveData<CourseEntity?>

    @Query("SELECT * FROM courses WHERE id = :id")
    fun getEntityById(id: Int): CourseEntity?

}