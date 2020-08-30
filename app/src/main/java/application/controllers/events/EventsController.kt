package application.controllers.events

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import application.controllers.course_analyse_events.CourseAnalyseEventModel
import application.controllers.course_analyse_events.CourseAnalyseEventsController
import application.controllers.course_drug_events.CourseDrugEventModel
import application.controllers.course_drug_events.CourseDrugEventsController

class EventsController private constructor(context: Context) {

    val drugEventsController = CourseDrugEventsController(context)
    val analyseEventsController = CourseAnalyseEventsController(context)

    fun getLiveEvents(courseId: Int, timeStart: Long, timeEnd: Long): LiveData<List<EventModel>> =
        MediatorLiveData<List<EventModel>>().also { mediator ->
            mediator.value = ArrayList()

            mediator.addSource(drugEventsController.getLiveEvents(courseId, timeStart, timeEnd)) { drugs ->
                val newList = ArrayList<EventModel>()

                mediator.value?.forEach { event ->
                    if (event.type !is CourseDrugEventModel) {
                        newList.add(event)
                    }
                }

                drugs.forEach { drug ->
                    newList.add(
                        EventModel(
                            drug.id,
                            drug,
                            drug.time
                        )
                    )
                }

                mediator.value = newList
            }

            mediator.addSource(analyseEventsController.getLiveEvents(courseId, timeStart, timeEnd)) { analyses ->
                val newList = ArrayList<EventModel>()

                mediator.value?.forEach { event ->
                    if (event.type !is CourseAnalyseEventModel) {
                        newList.add(event)
                    }
                }

                analyses.forEach { analyse ->
                    newList.add(
                        EventModel(
                            analyse.id,
                            analyse,
                            analyse.time
                        )
                    )
                }

                mediator.value = newList
            }
        }

    fun getEvents(courseId: Int, timeStart: Long, timeEnd: Long): List<EventModel> {
        val list = ArrayList<EventModel>()

        drugEventsController.getEvents(courseId, timeStart, timeEnd)
            .map { drug ->
                list.add(
                    EventModel(
                        drug.id,
                        drug,
                        drug.time
                    )
                )
            }

        analyseEventsController.getEvents(courseId, timeStart, timeEnd)
            .map { analyse ->
                list.add(
                    EventModel(
                        analyse.id,
                        analyse,
                        analyse.time
                    )
                )
            }

        return list
    }

    companion object {
        var instance: EventsController? = null

        operator fun invoke(context: Context) =
            instance ?: EventsController(context.applicationContext).also {
                instance = it
            }
    }
}