package longsys.database.entities.course_analyse_groups

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseAnalyseGroupsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: CourseAnalyseGroupEntity)

    @Delete
    fun delete(entity: CourseAnalyseGroupEntity)

    @Query("DELETE FROM course_analyse_groups WHERE courseId = :courseId")
    fun deleteByCourseId(courseId: Int)

    @Query("SELECT * FROM course_analyse_groups WHERE id = :id")
    fun getEntityById(id: Int): CourseAnalyseGroupEntity?

    @Query("SELECT * FROM course_analyse_groups WHERE courseId = :courseId ORDER BY time")
    fun getLiveEntitiesByCourseId(courseId: Int): LiveData<List<CourseAnalyseGroupEntity>>

}