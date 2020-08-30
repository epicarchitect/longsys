package longsys.database.entities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import longsys.database.entities.analyse_groups.AnalyseGroupEntity
import longsys.database.entities.analyse_groups.AnalyseGroupsDao
import longsys.database.entities.analyses.AnalyseEntity
import longsys.database.entities.analyses.AnalysesDao
import longsys.database.entities.course_analyse_events.CourseAnalyseEventEntity
import longsys.database.entities.course_analyse_events.CourseAnalyseEventsDao
import longsys.database.entities.course_analyse_groups.CourseAnalyseGroupEntity
import longsys.database.entities.course_analyse_groups.CourseAnalyseGroupsDao
import longsys.database.entities.course_analyses.CourseAnalyseEntity
import longsys.database.entities.course_analyses.CourseAnalysesDao
import longsys.database.entities.course_drug_events.CourseDrugEventEntity
import longsys.database.entities.course_drug_events.CourseDrugEventsDao
import longsys.database.entities.course_drugs.CourseDrugEntity
import longsys.database.entities.course_drugs.CourseDrugsDao
import longsys.database.entities.courses.CourseEntity
import longsys.database.entities.courses.CoursesDao
import longsys.database.entities.drug_groups.DrugGroupEntity
import longsys.database.entities.drug_groups.DrugGroupsDao
import longsys.database.entities.drugs.DrugEntity
import longsys.database.entities.drugs.DrugsDao

@Database(entities = [
    DrugGroupEntity::class,
    DrugEntity::class,
    CourseDrugEventEntity::class,
    AnalyseGroupEntity::class,
    AnalyseEntity::class,
    CourseAnalyseEventEntity::class,
    CourseEntity::class,
    CourseDrugEntity::class,
    CourseAnalyseEntity::class,
    CourseAnalyseGroupEntity::class
], version = 48, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun drugGroupsDao(): DrugGroupsDao
    abstract fun drugsDao(): DrugsDao
    abstract fun courseDrugEventsDao(): CourseDrugEventsDao
    abstract fun analyseGroupsDao(): AnalyseGroupsDao
    abstract fun analysesDao(): AnalysesDao
    abstract fun courseAnalyseEventsDao(): CourseAnalyseEventsDao
    abstract fun coursesDao(): CoursesDao
    abstract fun courseDrugsDao(): CourseDrugsDao
    abstract fun courseAnalysesDao(): CourseAnalysesDao
    abstract fun courseAnalyseGroupsDao(): CourseAnalyseGroupsDao

    companion object {
        var instance: AppDatabase? = null

        operator fun invoke(context: Context) =
            instance ?: build(context).also {
                instance = it
            }

        fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}