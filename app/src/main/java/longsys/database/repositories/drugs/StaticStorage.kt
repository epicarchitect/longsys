package longsys.database.repositories.drugs

import android.content.Context
import longsys.R
import longsys.constants.UnitType
import longsys.constants.UnitType.*
import longsys.database.entities.drugs.DrugEntity

object StaticStorage {

    fun list(context: Context): List<DrugEntity> {
        fun string(res: Int) = context.getString(res)

        fun item(id: Int, groupId: Int, nameRes: Int, descRes: Int, unitType: UnitType = MILLIGRAMS) =
            DrugEntity(
                id,
                groupId,
                string(nameRes),
                string(descRes),
                UnitType[unitType]
            )

        return listOf(
            item(-24, -7, R.string.drug_type_0_name, R.string.drug_type_0_desc, MILLIGRAMS),
            item(-23, -7, R.string.drug_type_1_name, R.string.drug_type_1_desc, MILLIGRAMS),
            item(-22, -7, R.string.drug_type_2_name, R.string.drug_type_2_desc, MILLIGRAMS),
            item(-21, -7, R.string.drug_type_3_name, R.string.drug_type_3_desc, MILLIGRAMS),

            item(-20, -6, R.string.drug_type_4_name, R.string.drug_type_4_desc, MILLIGRAMS),

            item(-19, -5, R.string.drug_type_5_name, R.string.drug_type_5_desc, MILLIGRAMS),
            item(-18, -5, R.string.drug_type_6_name, R.string.drug_type_6_desc, MILLIGRAMS),

            item(-17, -4, R.string.drug_type_7_name, R.string.drug_type_7_desc, ME),

            item(-16, -3, R.string.drug_type_8_name, R.string.drug_type_8_desc, ME),
            item(-15, -3, R.string.drug_type_9_name, R.string.drug_type_9_desc),
            item(-14, -3, R.string.drug_type_10_name, R.string.drug_type_10_desc, FLACONS),
            item(-13, -3, R.string.drug_type_11_name, R.string.drug_type_11_desc, MILLIGRAMS),
            item(-12, -3, R.string.drug_type_12_name, R.string.drug_type_12_desc, CAPSULES),
            item(-11, -3, R.string.drug_type_13_name, R.string.drug_type_13_desc, UNITS),

            item(-10, -2, R.string.drug_type_14_name, R.string.drug_type_14_desc, CAPSULES),
            item(-9, -2, R.string.drug_type_15_name, R.string.drug_type_15_desc, CAPSULES),
            item(-8, -2, R.string.drug_type_16_name, R.string.drug_type_16_desc, CAPSULES),
            item(-7, -2, R.string.drug_type_17_name, R.string.drug_type_17_desc, CAPSULES),
            item(-6, -2, R.string.drug_type_18_name, R.string.drug_type_18_desc, CAPSULES),
            item(-5, -2, R.string.drug_type_19_name, R.string.drug_type_19_desc, CAPSULES),
            item(-4, -2, R.string.drug_type_20_name, R.string.drug_type_20_desc, CAPSULES),
            item(-3, -2, R.string.drug_type_21_name, R.string.drug_type_21_desc, CAPSULES),
            item(-2, -2, R.string.drug_type_22_name, R.string.drug_type_22_desc, TABLETS)
        )
    }

}