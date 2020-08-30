package application.ui.pages.courses.course.analyses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import application.constants.VOID_ID
import application.controllers.course_analyse_groups.CourseAnalyseGroupModel
import application.controllers.course_analyse_groups.CourseAnalyseGroupsController
import application.controllers.course_analyses.CourseAnalyseModel
import application.controllers.course_analyses.CourseAnalysesController
import application.controllers.courses.CourseModel
import application.controllers.courses.current_course.CurrentCourseController
import application.extentions.app
import application.extentions.hide
import application.extentions.show
import application.ui.MainActivity
import application.ui.pages.courses.course.analyses.analyses.CourseAnalyseDialog
import application.ui.pages.courses.course.analyses.analyses.CourseAnalysesListAdapter
import application.ui.pages.courses.course.analyses.groups.CourseAnalyseGroupDialog
import application.ui.pages.courses.course.analyses.groups.CourseAnalyseGroupsListAdapter
import application.ui.pages.faq.QuestionDialog
import application.ui.pages.faq.QuestionModel
import kotlinx.android.synthetic.main.course_analyses_fragment.*
import longsys.bb35.extreme.bulk.max.R

class CourseAnalysesFragment : Fragment() {

    val courseId get() = arguments?.getInt("courseId", VOID_ID) ?: VOID_ID

    lateinit var groupsController: CourseAnalyseGroupsController
    lateinit var analysesController: CourseAnalysesController

    val backCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            showGroups()
        }
    }

    val isMutable get() = CurrentCourseController(app()).getCurrentCourseId() == courseId

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.course_analyses_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        groupsController = CourseAnalyseGroupsController(app())
        analysesController = CourseAnalysesController(app())

        buttonAboutRules.setOnClickListener {
            showAboutRules()
        }

        MainActivity.instance
            ?.onBackPressedDispatcher
            ?.addCallback(viewLifecycleOwner, backCallback)

        rvGroups.layoutManager = LinearLayoutManager(context)
        rvAnalyses.layoutManager = LinearLayoutManager(context)
        rvAnalysesNotMandatory.layoutManager = LinearLayoutManager(context)
        buttonBack.setOnClickListener {
            showGroups()
        }
        showGroups()
    }

    override fun onResume() {
        super.onResume()
        if (isMutable) {
            fab.show()
        } else {
            fab.hide()
        }
    }

    fun showGroups() {
        groupsLayout.show()
        analysesLayout.hide()
        analysesScroll.scrollTo(0, 0)

        backCallback.isEnabled = false
        tvTitle.text = "Группы анализов"
        buttonBack.hide()

        rvGroups.adapter = CourseAnalyseGroupsListAdapter().apply {
            onClickShow {
                showAnalyses(it)
            }

            onClick { model, _ ->
                val dialog = CourseAnalyseGroupDialog.build(model)
                dialog.isImmutable = !isMutable
                dialog.setModeView()
                dialog.show(parentFragmentManager, "")
            }

            groupsController.getLiveCourseAnalyseGroups(courseId).observe(viewLifecycleOwner, Observer {
                list = it
            })
        }

        if (isMutable) {
            fab.show()
        } else {
            fab.hide()
        }

        fab.setOnClickListener {
            CourseAnalyseGroupDialog.build(CourseAnalyseGroupModel(course = CourseModel(id = courseId))).apply {
                setModeEdit()
            }.show(this)
        }
    }

    fun showAnalyses(group: CourseAnalyseGroupModel) {
        groupsLayout.hide()
        analysesLayout.show()

        backCallback.isEnabled = true
        tvTitle.text = group.name
        buttonBack.show()

        val onClickListener = { model: CourseAnalyseModel, _: Int ->
            CourseAnalyseDialog.build(model.copy(courseAnalyseGroup = group)).apply {
                setModeView()
                isImmutable = !isMutable
            }.show(this)
        }

        val analysesAdapter = CourseAnalysesListAdapter()
        analysesAdapter.onClick(onClickListener)
        rvAnalyses.adapter = analysesAdapter

        val analysesNotMandatoryAdapter = CourseAnalysesListAdapter()
        analysesNotMandatoryAdapter.onClick(onClickListener)
        rvAnalysesNotMandatory.adapter = analysesNotMandatoryAdapter

        analysesController.getLiveCourseAnalyses(group.id).observe(viewLifecycleOwner, Observer {
            val list = ArrayList<CourseAnalyseModel>()
            val listNotMandatory = ArrayList<CourseAnalyseModel>()

            it.forEach { analyse ->
                if (analyse.isMandatory) {
                   list.add(analyse)
                } else {
                    listNotMandatory.add(analyse)
                }
            }

            analysesAdapter.list = list
            analysesNotMandatoryAdapter.list = listNotMandatory

            if (listNotMandatory.isEmpty()) {
                tvNotMandatory.hide()
            } else {
                tvNotMandatory.show()
            }
        })

        if (isMutable) {
            fab.show()
        } else {
            fab.hide()
        }

        fab.setOnClickListener {
            CourseAnalyseDialog.build(CourseAnalyseModel(courseAnalyseGroup = group)).apply {
                setModeEdit()
            }.show(this)
        }
    }

    fun showAboutRules() {
        val title = "Правила сдачи анализов"

        val content = "- Анализы сдаются утром, натощак – между последним приемом пищи и взятием крови должно пройти не менее 8 – 12 часов." +
                "\nВечером предшествующего дня рекомендуется необильный ужин. Желательно за 1 – 2 дня до обследования исключить из рациона жирное и жареное. Накануне исследования лечь спать в обычное время и встать не позднее чем за 1 час до взятия крови;" +
                "\n\n- Анализы необходимо сдавать строго в определенное время суток, обычно между 7:00 и 9:00 часами утра. Референсные значения большинства анализов, приведенных в справочной литературе, установлены именно для этого промежутка времени;" +
                "\n\n- За 24 часа перед сдачей - исключите секс и мастурбацию." +
                "\nОни могут повлиять как на уровни Пролактина, так и на уровни ЛГ, ФСГ и Тестостерона;" +
                "\n\n- Период воздержания от приема алкоголя должен быть не менее 24 ч. Если накануне состоялось застолье или было посещение бани или сауны – необходимо перенести лабораторное исследование на 1 – 2 дня;" +
                "\n\n- За 2 часа до взятия крови необходимо воздержаться от курения. Курение приводит к увеличению концентрации гемоглобина, количества эритроцитов, среднего объёма эритроцита (MCV) и снижению количества лейкоцитов;" +
                "\n\n- Не следует сдавать кровь после рентгенологических исследований, физиотерапевтических и лечебных процедур, способных оказать влияние на результаты анализов;" +
                "\n\n- Исключите физическую нагрузку, как минимум за 48 часов до сдачи анализов." +
                "\nТренировки (в т.ч. бег, подъем по лестнице) сильно влияют на уровни андрогенов и гормонов щитовидной железы;" +
                "\n\n- Перед процедурой следует отдохнуть 10 – 15 минут и успокоиться;" +
                "\n\n- Рекомендуется отказаться от приема лекарственных препаратов перед сдачей крови на исследование, то есть забор крови производится до приема лекарственных препаратов;" +
                "\n\n- Учитывая суточные ритмы изменения показателей крови повторные исследования целесообразно проводить в одно и то же время;" +
                "\n\n- В разных лабораториях могут применяться разные методы исследования и единицы измерения. Чтобы оценка результатов обследования была корректной и была приемлемость результатов, желательно проводить исследования в одной и той же лаборатории, в одно и то же время;"

        QuestionDialog(QuestionModel(0, title, content)).show(parentFragmentManager, "")
    }

    companion object {
        fun build(courseId: Int) =
            CourseAnalysesFragment().apply {
                arguments = bundleOf(
                    "courseId" to courseId
                )
            }
    }
}