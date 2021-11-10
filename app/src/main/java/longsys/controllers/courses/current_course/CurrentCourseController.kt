package longsys.controllers.courses.current_course

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.*
import longsys.constants.COUNT_DAYS_IN_COURSE
import longsys.constants.VOID_ID
import longsys.controllers.courses.CourseModel
import longsys.controllers.courses.CoursesController
import longsys.extentions.COUNT_MILLIS_IN_DAY
import java.util.*

class CurrentCourseController private constructor(val context: Context) {

    val preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    val coursesController =
        CoursesController(
            context
        )
    val liveCurrentCourseId = MutableLiveData<Int?>(getCurrentCourseId())
    val liveCurrentCourse = Transformations.switchMap(liveCurrentCourseId) {
        if (it == null) MutableLiveData<CourseModel?>(null)
        else coursesController.getLiveCourseById(it)
    }

    init {
        coursesController.getLiveCourses().observeForever {
            update()
        }
    }

    fun update() {
        val id = getCurrentCourseId()
        if (liveCurrentCourseId.value != id) {
            liveCurrentCourseId.value =  id
        }
    }

    fun complete() {
        setCurrentCourseId(VOID_ID)
        liveCurrentCourse.value?.let {
            coursesController.save(
                it.copy(
                    timeCompletion = Calendar.getInstance()
                )
            )
        }
    }

    fun changeEnabledNotifications(enabled: Boolean) =
        liveCurrentCourse.value?.let {
            if (it.isNotificationsEnabled != enabled) {
                coursesController.save(
                    it.copy(
                        isNotificationsEnabled = enabled
                    )
                )
            }
        }

    fun changeDaysBeforeNotifyAnalyse(days: Int) =
        liveCurrentCourse.value?.let {
            if (it.daysBeforeNotifyAnalyse != days) {
                coursesController.save(
                    it.copy(
                        daysBeforeNotifyAnalyse = days
                    )
                )
            }
        }

    fun changeTimeStart(time: Calendar, onDoneListener: (CourseModel, isSucceeded: Boolean) -> Unit = { _, _ -> }) =
        liveCurrentCourse.value?.let {
            install(
                it.copy(
                    timeStart = time,
                    timeEnd = Calendar.getInstance().apply {
                        timeInMillis = time.timeInMillis + COUNT_MILLIS_IN_DAY * COUNT_DAYS_IN_COURSE
                    }
                ),
                onDoneListener
            )
        }

    fun changeTimeEnd(time: Calendar, onDoneListener: (CourseModel, isSucceeded: Boolean) -> Unit = { _, _ -> }) =
        liveCurrentCourse.value?.let {
            install(it.copy(timeEnd = time), onDoneListener)
        }

    fun install(model: CourseModel, onDoneListener: (CourseModel, isSucceeded: Boolean) -> Unit = { _, _ -> }) {
        val course = coursesController.save(model)
        val manager = WorkManager.getInstance(context)
        val request = OneTimeWorkRequest.Builder(CourseInstallationWorker::class.java)
            .setInputData(
                Data.Builder()
                    .putInt("courseId", course.id)
                    .build()
            )
            .build()

        manager.getWorkInfoByIdLiveData(request.id).observeForever { info ->
            info?.let {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        onDoneListener(course, true)
                        setCurrentCourseId(course.id)
                    }

                    WorkInfo.State.FAILED -> onDoneListener(course, false)
                }
            }
        }

        manager.enqueueUniqueWork(
            "CurrentCourseController.install",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }


    fun setCurrentCourseId(id: Int) {
        preferences.edit().putInt(CURRENT_COURSE_ID, id).apply()
        update()
    }

    fun getCurrentCourseId() =
        preferences.getInt(CURRENT_COURSE_ID, VOID_ID)

    companion object {
        const val NAME = "CurrentCourseController"
        const val CURRENT_COURSE_ID = "CURRENT_COURSE_ID"

        var instance: CurrentCourseController? = null

        operator fun invoke(context: Context) =
            instance ?: CurrentCourseController(context.applicationContext).also {
                instance = it
            }
    }
}