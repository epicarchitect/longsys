package longsys.database.entities.course_drugs

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDrugsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: CourseDrugEntity)

    @Delete
    fun delete(entity: CourseDrugEntity)

    @Query("DELETE FROM course_drugs WHERE courseId = :courseId")
    fun deleteByCourseId(courseId: Int)

    @Query("SELECT * FROM course_drugs WHERE courseId = :courseId ORDER BY timeStart")
    fun getLiveEntitiesByCourseId(courseId: Int): LiveData<List<CourseDrugEntity>>

    @Query("SELECT * FROM course_drugs WHERE id = :id")
    fun getEntityById(id: Int): CourseDrugEntity?

}