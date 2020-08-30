package longsys.database.repositories.drug_groups

import android.content.Context
import longsys.R
import longsys.database.entities.drug_groups.DrugGroupEntity

object StaticStorage {

    fun list(context: Context): List<DrugGroupEntity> {
        fun string(res: Int) = context.getString(res)

        fun item(id: Int, nameRes: Int) =
            DrugGroupEntity(
                id,
                string(nameRes)
            )

        return listOf(
            item(-7, R.string.drug_group_0),
            item(-6, R.string.drug_group_1),
            item(-5, R.string.drug_group_2),
            item(-4, R.string.drug_group_3),
            item(-3, R.string.drug_group_4),
            item(-2, R.string.drug_group_5)
        )
    }

}