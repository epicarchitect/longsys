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

        add( // Тестостерон пропинат +
            -32,
            7,
            7,
            1,
            0,
            50.0,
            7,
            0
        )

        add( // Мастерон пропионат +
            -29,
            7,
            7,
            1,
            0,
            25.0,
            7,
            0
        )

        add( // Оксиметолон +
            -27,
            7,
            89,
            1,
            1,
            50.0,
            18,
            0
        )

        add( // Тестостерон ципионат +
            -30,
            7,
            82,
            1,
            4,
            250.0,
            21,
            0
        )

        add( // Дростанолон энантат +
            -28,
            7,
            82,
            1,
            4,
            100.0,
            21,
            0
        )

        add( // Анастразол +
            -24,
            11,
            17,
            1,
            2,
            0.5,
            21,
            0
        )

        add( // Анастразол +
            -24,
            19,
            81,
            1,
            1,
            0.5,
            21,
            0
        )

        add( // Анастразол +
            -24,
            83,
            89,
            1,
            2,
            0.5,
            21,
            0
        )

        add( // ХГЧ +
            -20,
            42,
            54,
            1,
            3,
            500.0,
            22,
            0
        )

        add( // ХГЧ +
            -20,
            70,
            82,
            1,
            3,
            500.0,
            22,
            0
        )

        add( // Тестостерон пропионат +
            -32,
            85,
            85,
            1,
            0,
            50.0,
            7,
            0
        )

        add( // Мастерон пропионат +
            -29,
            85,
            85,
            1,
            0,
            25.0,
            7,
            0
        )

        add( // Тестостерон пропионат +
            -32,
            87,
            89,
            1,
            1,
            100.0,
            7,
            0
        )

        add( // Мастерон пропионат +
            -29,
            87,
            89,
            1,
            1,
            100.0,
            7,
            0
        )

        add( // Кломифен +
            -23,
            94,
            107,
            1,
            0,
            50.0,
            21,
            0
        )

        add( // Трибулус +
            -2,
            94,
            121,
            1,
            0,
            8000.0,
            21,
            0
        )

        add( // Трибулус +
            -2,
            94,
            121,
            1,
            0,
            8000.0,
            21,
            0
        )

        add( // Соматотропин
            -21,
            97,
            121,
            1,
            0,
            2.0,
            22,
            0
        )

        add( // Тамоксифен +
            -22,
            108,
            121,
            1,
            0,
            20.0,
            21,
            0
        )

        add( // Тамоксифен +
            -22,
            122,
            149,
            1,
            0,
            5.0,
            21,
            0
        )

        list.add( // Прегненолон +
            CourseDrugModel(
                drug = DrugModel(-3),
                course = course,
                timeStart = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * 122),
                timeEnd = course.timeEnd,
                activeDays = 1,
                stopDays = 0,
                count = 5.0,
                hour = 21,
                minute = 0
            )
        )

        list.add( // Витамин D +
            CourseDrugModel(
                drug = DrugModel(-5),
                course = course,
                timeStart = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * 122),
                timeEnd = course.timeEnd,
                activeDays = 1,
                stopDays = 0,
                count = 3000.0,
                hour = 21,
                minute = 0
            )
        )

        list.add( // Цинк
            CourseDrugModel(
                drug = DrugModel(-4),
                course = course,
                timeStart = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * 122),
                timeEnd = course.timeEnd,
                activeDays = 1,
                stopDays = 0,
                count = 50.0,
                hour = 21,
                minute = 0
            )
        )

        list.add( // Гептрал
            CourseDrugModel(
                drug = DrugModel(-18),
                course = course,
                timeStart = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * 140),
                timeEnd = course.timeEnd,
                activeDays = 1,
                stopDays = 0,
                count = 400.0,
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
            time = calendarByTime(course.timeStart.timeInMillis + COUNT_MILLIS_IN_DAY * 122).copy {
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