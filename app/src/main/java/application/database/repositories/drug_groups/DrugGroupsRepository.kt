package application.database.repositories.drug_groups

import android.content.Context
import application.App
import application.constants.VOID_ID
import application.database.entities.AppDatabase
import application.database.entities.drug_groups.DrugGroupEntity
import application.database.repositories.IdCounter

class DrugGroupsRepository private constructor(context: Context) {

    val dao = AppDatabase(context).drugGroupsDao()
    val idCounter = IdCounter(context)

    init {
        App.async {
            dao.saveList(StaticStorage.list(context))
        }
    }

    fun entityWithId(e: DrugGroupEntity) =
        if (e.id == VOID_ID) e.copy(id = idCounter.nextId()) else e

    fun save(entity: DrugGroupEntity) =
        entityWithId(entity).also {
            dao.save(it)
        }

    fun delete(entity: DrugGroupEntity) = dao.delete(entity)

    fun getLiveEntities() = dao.getLiveEntities()

    fun getEntities() = dao.getEntities()

    fun getEntityById(id: Int) = dao.getEntityById(id)

    companion object {
        var instance: DrugGroupsRepository? = null

        operator fun invoke(context: Context) =
            instance ?: DrugGroupsRepository(context.applicationContext).also {
                instance = it
            }
    }
}