package longsys.database.repositories.analyses

import android.content.Context
import longsys.R
import longsys.database.entities.analyses.AnalyseEntity

object StaticStorage {

    fun list(context: Context): List<AnalyseEntity> {
        fun string(res: Int) = context.getString(res)

        fun item(id: Int, groupId: Int, nameRes: Int, descRes: Int) =
            AnalyseEntity(
                id,
                groupId,
                string(nameRes),
                string(descRes)
            )

        return listOf(
            item(-27, -12, R.string.analyse_type_0_name, R.string.analyse_type_0_desc),
            item(-26, -12, R.string.analyse_type_1_name, R.string.analyse_type_1_desc),
            item(-25, -12, R.string.analyse_type_2_name, R.string.analyse_type_2_desc),
            item(-24, -12, R.string.analyse_type_3_name, R.string.analyse_type_3_desc),
            item(-23, -12, R.string.analyse_type_4_name, R.string.analyse_type_4_desc),
            item(-22, -12, R.string.analyse_type_5_name, R.string.analyse_type_5_desc),

            item(-21, -11, R.string.analyse_type_6_name, R.string.analyse_type_6_desc),

            item(-20, -10, R.string.analyse_type_7_name, R.string.analyse_type_7_desc),
            item(-19, -10, R.string.analyse_type_8_name, R.string.analyse_type_8_desc),

            item(-18, -9, R.string.analyse_type_9_name, R.string.analyse_type_9_desc),
            item(-17, -9, R.string.analyse_type_10_name, R.string.analyse_type_10_desc),
            item(-16, -9, R.string.analyse_type_11_name, R.string.analyse_type_11_desc),
            item(-15, -9, R.string.analyse_type_12_name, R.string.analyse_type_12_desc),

            item(-14, -8, R.string.analyse_type_13_name, R.string.analyse_type_13_desc),
            item(-13, -8, R.string.analyse_type_14_name, R.string.analyse_type_14_desc),
            item(-12, -8, R.string.analyse_type_15_name, R.string.analyse_type_15_desc),
            item(-11, -8, R.string.analyse_type_16_name, R.string.analyse_type_16_desc),

            item(-10, -7, R.string.analyse_type_17_name, R.string.analyse_type_17_desc),
            item(-9, -7, R.string.analyse_type_18_name, R.string.analyse_type_18_desc),

            item(-8, -6, R.string.analyse_type_19_name, R.string.analyse_type_19_desc),

            item(-7, -5, R.string.analyse_type_20_name, R.string.analyse_type_20_desc),
            item(-6, -5, R.string.analyse_type_21_name, R.string.analyse_type_21_desc),
            item(-5, -5, R.string.analyse_type_22_name, R.string.analyse_type_22_desc),

            item(-4, -4, R.string.analyse_type_23_name, R.string.analyse_type_23_desc),

            item(-3, -3, R.string.analyse_type_24_name, R.string.analyse_type_24_desc),

            item(-2, -2, R.string.analyse_type_25_name, R.string.analyse_type_25_desc)
        )
    }

}