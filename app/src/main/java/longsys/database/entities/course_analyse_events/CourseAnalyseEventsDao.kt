package longsys.database.entities.course_analyse_events

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseAnalyseEventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: CourseAnalyseEventEntity)

    @Delete
    fun delete(entity: CourseAnalyseEventEntity)

    @Query("DELETE FROM course_analyse_events")
    fun deleteAll()

    @Query("DELETE FROM course_analyse_events WHERE courseAnalyseGroupId in (SELECT id FROM course_analyse_groups WHERE courseId = :id)")
    fun deleteByCourseId(id: Int)

    @Query("DELETE FROM course_analyse_events WHERE courseAnalyseGroupId = :id")
    fun deleteByCourseAnalyseGroupId(id: Int)

    @Query("SELECT * FROM course_analyse_events WHERE time >= :timeStart AND time <= :timeEnd AND courseAnalyseGroupId in (SELECT id FROM course_analyse_groups WHERE courseId = :courseId) ORDER BY time")
    fun getLiveEntities(courseId: Int, timeStart: Long, timeEnd: Long): LiveData<List<CourseAnalyseEventEntity>>

    @Query("SELECT * FROM course_analyse_events WHERE time >= :timeStart AND time <= :timeEnd AND courseAnalyseGroupId in (SELECT id FROM course_analyse_groups WHERE courseId = :courseId) ORDER BY time")
    fun getEntities(courseId: Int, timeStart: Long, timeEnd: Long):List<CourseAnalyseEventEntity>

    @Query("SELECT * FROM course_analyse_events")
    fun getEntities(): List<CourseAnalyseEventEntity>

    @Query("SELECT * FROM course_analyse_events WHERE id = :id")
    fun getEntityById(id: Int): CourseAnalyseEventEntity?

}