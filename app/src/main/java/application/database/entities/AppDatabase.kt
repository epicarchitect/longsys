package application.database.entities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import application.database.entities.analyse_groups.AnalyseGroupEntity
import application.database.entities.analyse_groups.AnalyseGroupsDao
import application.database.entities.analyses.AnalyseEntity
import application.database.entities.analyses.AnalysesDao
import application.database.entities.course_analyse_events.CourseAnalyseEventEntity
import application.database.entities.course_analyse_events.CourseAnalyseEventsDao
import application.database.entities.course_analyse_groups.CourseAnalyseGroupEntity
import application.database.entities.course_analyse_groups.CourseAnalyseGroupsDao
import application.database.entities.course_analyses.CourseAnalyseEntity
import application.database.entities.course_analyses.CourseAnalysesDao
import application.database.entities.course_drug_events.CourseDrugEventEntity
import application.database.entities.course_drug_events.CourseDrugEventsDao
import application.database.entities.course_drugs.CourseDrugEntity
import application.database.entities.course_drugs.CourseDrugsDao
import application.database.entities.courses.CourseEntity
import application.database.entities.courses.CoursesDao
import application.database.entities.drug_groups.DrugGroupEntity
import application.database.entities.drug_groups.DrugGroupsDao
import application.database.entities.drugs.DrugEntity
import application.database.entities.drugs.DrugsDao

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