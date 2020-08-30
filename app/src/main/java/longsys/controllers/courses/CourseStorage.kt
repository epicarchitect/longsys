package longsys.controllers.courses

import longsys.controllers.analyses.AnalyseModel
import longsys.controllers.course_analyse_groups.CourseAnalyseGroupModel
import longsys.controllers.course_analyses.CourseAnalyseModel
import longsys.controllers.course_drugs.CourseDrugModel
import longsys.controllers.drugs.DrugModel
import longsys.extentions.*

class CourseStorage(val course: CourseModel) {

    fun courseDrugs(): ArrayList<CourseDrugModel> {
        val list = ArrayList<CourseDrugModel>()

        fun add(
            drugId: Int,
            dayStart: Long,
            dayEnd: Long,
            activeDays: Int,
            stopDays: Int,
            count: Double,
            hour: Int,
            minute: Int
        ) = list.add(
            CourseDrugModel(
                    drug = DrugModel(drugId),
                    course = course,
                    timeStart = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * dayStart),
                    timeEnd = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * dayEnd),
                    activeDays = activeDays,
                    stopDays = stopDays,
                    count = count,
                    hour = hour,
                    minute = minute
                )
            )

        add( // Тестостерон пропинат
            -24,
            7,
            45,
            1,
            1,
            100.0,
            21,
            0
        )

        add( // Мастерон
            -23,
            7,
            45,
            1,
            1,
            50.0,
            21,
            0
        )

        add( // Оксиментол
            -22,
            7,
            45,
            1,
            1,
            50.0,
            18,
            0
        )

        add( // Анастразол
            -20,
            11,
            44,
            1,
            2,
            0.5,
            21,
            0
        )

        add( // ХГЧ
            -16,
            42,
            42,
            1,
            0,
            500.0,
            21,
            0
        )

        add( // ГР
            -17,
            50,
            77,
            1,
            0,
            2.0,
            21,
            0
        )

        add( // Кломифен
            -19,
            50,
            63,
            1,
            0,
            50.0,
            21,
            0
        )

        add( // Тамоксифен 20
            -18,
            64,
            77,
            1,
            0,
            20.0,
            21,
            0
        )

        list.add( // Тамоксифен 10
            CourseDrugModel(
                drug = DrugModel(-18),
                course = course,
                timeStart = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * 78),
                timeEnd = course.timeEnd,
                activeDays = 1,
                stopDays = 0,
                count = 10.0,
                hour = 21,
                minute = 0
            )
        )

        return list
    }

    fun beforeCourseAnalyseGroup() =
        CourseAnalyseGroupModel(
            course = course,
            name = "Анализы до курса",
            time = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * 2).copy {
                hour(8)
                minute(0)
                second(0)
            }
        )

    fun onCourseAnalyseGroup() =
        CourseAnalyseGroupModel(
            course = course,
            name = "Анализы на курсе",
            time = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * 21).copy {
                hour(8)
                minute(0)
                second(0)
            }
        )

    fun afterCourseAnalyseGroup() =
        CourseAnalyseGroupModel(
            course = course,
            name = "Анализы после курса",
            time = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * 78).copy {
                hour(8)
                minute(0)
                second(0)
            }
        )

    fun beforeCourseAnalyses(group: CourseAnalyseGroupModel): ArrayList<CourseAnalyseModel> {
        val list = ArrayList<CourseAnalyseModel>()

        fun add(
            analyseId: Int,
            isMandatory: Boolean = false
        ) = list.add(
            CourseAnalyseModel(
                analyse = AnalyseModel(analyseId),
                courseAnalyseGroup = group,
                isMandatory = isMandatory
            )
        )

        add(-25, true) // Глобулин
        add(-27, true) // ЛГ
        add(-24, true) // Тестостерон
        add(-26, true) // ФСГ
        add(-22, true) // Пролактин
        add(-23, true) // Эстрадиол
        add(-2)  // Кортизол
        add(-3)  // ПСА общий
        add(-5)  // Т4
        add(-6)  // Т3
        add(-7)  // ТТГ
        add(-21, true) // Анализ крови
        add(-14, true) // АЛТ
        add(-13, true) // АСТ
        add(-11, true) // гамма-ГТ
        add(-8, true)  // Глюкоза
        add(-10, true) // Креатин
        add(-9, true)  // Мочевина
        add(-20, true) // Белок общий
        add(-15, true) // Триглицериды
        add(-19, true) // Фосфатаза
        add(-12, true) // Билирубин
        add(-18, true) // Холестерин общий
        add(-17, true) // Холестерин - ЛПВП (липопротеины высокой плотности)
        add(-16, true) // Холестерин - ЛПОНП (липопротеины очень низкой плотности)
        add(-4) // Дигидротестостерон

        return list
    }

    fun onCourseAnalyses(group: CourseAnalyseGroupModel): ArrayList<CourseAnalyseModel> {
        val list = ArrayList<CourseAnalyseModel>()

        fun add(
            analyseId: Int,
            isMandatory: Boolean = false
        ) = list.add(
            CourseAnalyseModel(
                analyse = AnalyseModel(analyseId),
                courseAnalyseGroup = group,
                isMandatory = isMandatory
            )
        )

        add(-25, true) // Глобулин
        add(-27, true) // ЛГ
        add(-24, true) // Тестостерон
        add(-26, true) // ФСГ
        add(-22, true) // Пролактин
        add(-23, true) // Эстрадиол

        return list
    }

    fun afterCourseAnalyses(group: CourseAnalyseGroupModel): ArrayList<CourseAnalyseModel> {
        val list = ArrayList<CourseAnalyseModel>()

        fun add(
            analyseId: Int,
            isMandatory: Boolean = false
        ) = list.add(
            CourseAnalyseModel(
                analyse = AnalyseModel(analyseId),
                courseAnalyseGroup = group,
                isMandatory = isMandatory
            )
        )

        add(-25, true) // Глобулин
        add(-27, true) // ЛГ
        add(-24, true) // Тестостерон
        add(-26, true) // ФСГ
        add(-22, true) // Пролактин
        add(-23, true) // Эстрадиол
        add(-2)  // Кортизол
        add(-3)  // ПСА общий
        add(-5)  // Т4
        add(-6)  // Т3
        add(-7)  // ТТГ
        add(-21, true) // Анализ крови
        add(-14, true) // АЛТ
        add(-13, true) // АСТ
        add(-11, true) // гамма-ГТ
        add(-8, true)  // Глюкоза
        add(-10, true) // Креатин
        add(-9, true)  // Мочевина
        add(-20, true) // Белок общий
        add(-15, true) // Триглицериды
        add(-19, true) // Фосфатаза
        add(-12, true) // Билирубин
        add(-18, true) // Холестерин общий
        add(-17, true) // Холестерин - ЛПВП (липопротеины высокой плотности)
        add(-16, true) // Холестерин - ЛПОНП (липопротеины очень низкой плотности)
        add(-4) // Дигидротестостерон

        return list
    }

}