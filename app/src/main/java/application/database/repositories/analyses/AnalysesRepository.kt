package application.database.repositories.analyses

import android.content.Context
import application.App
import application.constants.VOID_ID
import application.database.entities.AppDatabase
import application.database.entities.analyses.AnalyseEntity
import application.database.repositories.IdCounter

class AnalysesRepository private constructor(context: Context) {

    val dao = AppDatabase(context).analysesDao()
    val idCounter = IdCounter(context)

    init {
        App.async {
            dao.saveList(StaticStorage.list(context))
        }
    }

    fun entityWithId(e: AnalyseEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: AnalyseEntity) =
        entityWithId(entity).also {
            dao.save(it)
        }

    fun delete(entity: AnalyseEntity) = dao.delete(entity)

    fun getLiveEntitiesByGroupId(groupId: Int) = dao.getLiveEntitiesByGroupId(groupId)

    fun getEntitiesByGroupId(groupId: Int) = dao.getEntitiesByGroupId(groupId)

    fun getEntityById(id: Int) = dao.getEntityById(id)

    companion object {
        var instance: AnalysesRepository? = null

        operator fun invoke(context: Context) =
            instance ?: AnalysesRepository(context.applicationContext).also {
                instance = it
            }
    }
}