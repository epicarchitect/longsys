package application.database.repositories.drugs

import android.content.Context
import application.App.Companion.async
import application.constants.VOID_ID
import application.database.entities.AppDatabase
import application.database.entities.drugs.DrugEntity
import application.database.repositories.IdCounter

class DrugsRepository private constructor(context: Context) {

    val dao = AppDatabase(context).drugsDao()
    val idCounter = IdCounter(context)

    init {
        async {
            dao.saveList(StaticStorage.list(context))
        }
    }

    fun entityWithId(e: DrugEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: DrugEntity) =
        entityWithId(entity).also {
            dao.save(it)
        }

    fun delete(entity: DrugEntity) = dao.delete(entity)

    fun getEntityById(id: Int) = dao.getEntityById(id)

    fun getLiveEntitiesByGroupId(groupId: Int) = dao.getLiveEntitiesByGroupId(groupId)

    fun getEntitiesByGroupId(groupId: Int) = dao.getEntitiesByGroupId(groupId)

    companion object {
        var instance: DrugsRepository? = null

        operator fun invoke(context: Context) =
            instance ?: DrugsRepository(context.applicationContext).also {
                instance = it
            }
    }
}