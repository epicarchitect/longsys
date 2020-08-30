package application.database.repositories.analyse_groups

import android.content.Context
import application.database.entities.analyse_groups.AnalyseGroupEntity
import longsys.bb35.extreme.bulk.max.R

object StaticStorage {

    fun list(context: Context): List<AnalyseGroupEntity> {
        fun string(res: Int) = context.getString(res)

        fun item(id: Int, nameRes: Int, descRes: Int = -1) =
            AnalyseGroupEntity(
                id,
                string(nameRes),
                if (descRes == -1) "" else string(descRes)
            )

        return listOf(
            item(-12, R.string.analyse_group_0),
            item(-11, R.string.analyse_group_1),
            item(-10, R.string.analyse_group_2),
            item(-9, R.string.analyse_group_3),
            item(-8, R.string.analyse_group_4),
            item(-7, R.string.analyse_group_5),
            item(-6, R.string.analyse_group_6),
            item(-5, R.string.analyse_group_7, R.string.analyse_group_7_desc),
            item(-4, R.string.analyse_group_8, R.string.analyse_group_8_desc),
            item(-3, R.string.analyse_group_9, R.string.analyse_group_9_desc),
            item(-2, R.string.analyse_group_10, R.string.analyse_group_10_desc)
        )
    }

}