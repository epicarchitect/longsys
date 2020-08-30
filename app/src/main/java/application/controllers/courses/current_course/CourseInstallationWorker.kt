package application.controllers.courses.current_course

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import application.constants.VOID_ID
import application.controllers.course_analyse_groups.CourseAnalyseGroupsController
import application.controllers.course_analyses.CourseAnalysesController
import application.controllers.course_drugs.CourseDrugsController
import application.controllers.courses.CourseStorage
import application.controllers.courses.CoursesController

class CourseInstallationWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    override fun doWork(): Result {
        val courseId = inputData.getInt("courseId", VOID_ID)

        CoursesController(applicationContext).getCourseById(courseId)?.let { course ->
            val courseDrugsController = CourseDrugsController(applicationContext)
            val courseAnalysesController = CourseAnalysesController(applicationContext)
            val courseAnalyseGroupsController = CourseAnalyseGroupsController(applicationContext)

            val storage = CourseStorage(course)

            courseDrugsController.deleteByCourseId(course.id)
            courseAnalyseGroupsController.deleteByCourseId(course.id)

            storage.courseDrugs().forEach { drug ->
                courseDrugsController.save(drug)
            }

            storage.beforeCourseAnalyseGroup().let {
                val group = courseAnalyseGroupsController.save(it)
                storage.beforeCourseAnalyses(group).forEach { analyse ->
                    courseAnalysesController.save(analyse)
                }
            }

            storage.onCourseAnalyseGroup().let {
                val group = courseAnalyseGroupsController.save(it)
                storage.onCourseAnalyses(group).forEach { analyse ->
                    courseAnalysesController.save(analyse)
                }
            }

            storage.afterCourseAnalyseGroup().let {
                val group = courseAnalyseGroupsController.save(it)
                storage.afterCourseAnalyses(group).forEach { analyse ->
                    courseAnalysesController.save(analyse)
                }
            }

            return Result.success()
        }

        return Result.failure()
    }

}