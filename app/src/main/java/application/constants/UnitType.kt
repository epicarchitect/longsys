package application.constants

import android.content.Context
import longsys.bb35.extreme.bulk.max.R

enum class UnitType {
    AMPOULES,
    VIALS,
    FLACONS,
    INJECTION,
    CAPSULES,
    TABLETS,
    THINGS,
    GRAMS,
    MILLIGRAMS,
    MILLILITERS,
    UNITS,
    ME;

    companion object {
        operator fun get(i: Int) = when (i) {
            0 -> AMPOULES
            1 -> VIALS
            2 -> FLACONS
            3 -> INJECTION
            4 -> CAPSULES
            5 -> TABLETS
            6 -> THINGS
            7 -> GRAMS
            8 -> MILLIGRAMS
            9 -> MILLILITERS
            10 -> UNITS
            11 -> ME
            else -> AMPOULES
        }

        operator fun get(i: UnitType) = when (i) {
            AMPOULES -> 0
            VIALS -> 1
            FLACONS -> 2
            INJECTION -> 3
            CAPSULES -> 4
            TABLETS -> 5
            THINGS -> 6
            GRAMS -> 7
            MILLIGRAMS -> 8
            MILLILITERS -> 9
            UNITS -> 10
            ME -> 11
        }

        fun toString(c: Context, type: UnitType, fullName: Boolean = false) =
            c.getString(
                when {
                    fullName -> when (type) {
                        AMPOULES -> R.string.ampoules
                        VIALS -> R.string.vials
                        FLACONS -> R.string.flacons
                        INJECTION -> R.string.injection
                        CAPSULES -> R.string.capsules
                        TABLETS -> R.string.tablets
                        THINGS -> R.string.things
                        GRAMS -> R.string.grams
                        MILLIGRAMS -> R.string.milligrams
                        MILLILITERS -> R.string.milliliters
                        UNITS -> R.string.units
                        ME -> R.string.me
                    }

                    else -> when (type) {
                        AMPOULES -> R.string.ampoules
                        VIALS -> R.string.vials
                        FLACONS ->R.string.flacons
                        INJECTION -> R.string.injection
                        CAPSULES -> R.string.capsules
                        TABLETS -> R.string.tablets
                        THINGS -> R.string.things
                        GRAMS -> R.string.grams_short
                        MILLIGRAMS -> R.string.milligrams_short
                        MILLILITERS -> R.string.milliliters_short
                        UNITS -> R.string.units_short
                        ME -> R.string.me
                    }
                }
            )
    }
}