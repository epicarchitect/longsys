package longsys.database.entities.course_analyses

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseAnalysesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: CourseAnalyseEntity)

    @Delete
    fun delete(entity: CourseAnalyseEntity)

    @Query("DELETE FROM course_analyses WHERE courseAnalyseGroupId in (SELECT id FROM course_analyse_groups WHERE courseId = :courseId)")
    fun deleteByCourseId(courseId: Int)

    @Query("SELECT * FROM course_analyses WHERE courseAnalyseGroupId = :groupId ORDER BY analyseId")
    fun getEntities(groupId: Int): List<CourseAnalyseEntity>

    @Query("SELECT * FROM course_analyses WHERE courseAnalyseGroupId = :groupId ORDER BY analyseId")
    fun getLiveEntities(groupId: Int): LiveData<List<CourseAnalyseEntity>>

    @Query("SELECT * FROM course_analyses WHERE id = :id")
    fun getEntityById(id: Int): CourseAnalyseEntity?

}