package longsys.database.repositories.analyse_groups

import android.content.Context
import longsys.App.Companion.async
import longsys.constants.VOID_ID
import longsys.database.entities.AppDatabase
import longsys.database.entities.analyse_groups.AnalyseGroupEntity
import longsys.database.repositories.IdCounter

class AnalyseGroupsRepository private constructor(context: Context) {

    val dao = AppDatabase(context).analyseGroupsDao()
    val idCounter = IdCounter(context)

    init {
        async {
            dao.saveList(StaticStorage.list(context))
        }
    }

    fun entityWithId(e: AnalyseGroupEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: AnalyseGroupEntity) =
        entityWithId(entity).also {
            dao.save(it)
        }

    fun delete(entity: AnalyseGroupEntity) = dao.delete(entity)

    fun getLiveEntities() = dao.getLiveEntities()

    fun getEntities() = dao.getEntities()

    fun getEntityById(id: Int) = dao.getEntityById(id)

    companion object {
        var instance: AnalyseGroupsRepository? = null

        operator fun invoke(context: Context) =
            instance ?: AnalyseGroupsRepository(context.applicationContext).also {
                instance = it
            }
    }
}