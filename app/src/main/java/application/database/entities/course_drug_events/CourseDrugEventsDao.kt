package application.database.entities.course_drug_events

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDrugEventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: CourseDrugEventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveList(list: List<CourseDrugEventEntity>)

    @Delete
    fun delete(entity: CourseDrugEventEntity)

    @Query("DELETE FROM course_drug_events")
    fun deleteAll()

    @Query("DELETE FROM course_drug_events WHERE courseDrugId in (SELECT id FROM course_drugs WHERE courseId = :id)")
    fun deleteByCourseId(id: Int)

    @Query("DELETE FROM course_drug_events WHERE courseDrugId = :courseDrugId")
    fun deleteByCourseDrugId(courseDrugId: Int)

    @Query("SELECT * FROM course_drug_events WHERE time >= :timeStart AND time <= :timeEnd AND courseDrugId in (SELECT id FROM course_drugs WHERE courseId = :courseId) ORDER BY time")
    fun getLiveEntities(courseId: Int, timeStart: Long, timeEnd: Long): LiveData<List<CourseDrugEventEntity>>

    @Query("SELECT * FROM course_drug_events WHERE time >= :timeStart AND time <= :timeEnd AND courseDrugId in (SELECT id FROM course_drugs WHERE courseId = :courseId) ORDER BY time")
    fun getEntities(courseId: Int, timeStart: Long, timeEnd: Long): List<CourseDrugEventEntity>

    @Query("SELECT * FROM course_drug_events")
    fun getEntities(): List<CourseDrugEventEntity>

    @Query("SELECT * FROM course_drug_events WHERE id = :id")
    fun getEntityById(id: Int): CourseDrugEventEntity?

}