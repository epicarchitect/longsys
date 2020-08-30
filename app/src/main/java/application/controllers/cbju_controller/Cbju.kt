package application.controllers.cbju_controller

// cbju - кбжу (калории белки жиры углеводы)
data class Cbju(
    var weight: Double = 0.0,
    var activityLevel: ActivityLevel = ActivityLevel.SUPER_MIN,
    var deficitLevel: DeficitLevel = DeficitLevel.MIDDLE,
    var proteinLevel: ProteinLevel = ProteinLevel.MIN,
    var fatLevel: FatLevel = FatLevel.BAD,
    var waterLevel: WaterLevel = WaterLevel.UNIVERSAL
) {

    enum class ActivityLevel(val value: Int) {
        SUPER_MIN(28), // Сидячая работа
        MIN(30),       // Минимум активности
        MIDDLE(32),    // Средняя активность
        MAX(34);       // Высокая активность
    }

    enum class DeficitLevel(val value: Double) {
        MIDDLE(0.15), // Средняя скорость похудения
        MAX(0.2)      // Быстрая скорость похудения
    }

    enum class ProteinLevel(val value: Double) {
        MIN(1.2),       // Минимальный уровень
        UNIVERSAL(1.5), // Универсальный уровень
        SPORT_1(1.7),   // "Спортивный" уровень №1
        SPORT_2(2.0),   // "Спортивный" уровень №2
        MAX(2.2),       // Максимальный уровень
    }

    enum class FatLevel(val value: Double) {
        BAD(0.8),      // Минимально разумный уровень
        UNIVERSAL(1.0) // Универсальный уровень
    }

    enum class WaterLevel(val value: Int) {
        UNIVERSAL(32), // Универсальный уровень
        MIDDLE(34),    // Средние нагрузки
        HIGH(36),      // Высокие нагрузки
    }

    fun calories() = weight * activityLevel.value - (weight * activityLevel.value) * deficitLevel.value

    fun proteins() = weight * proteinLevel.value

    fun fats() = weight * fatLevel.value

    fun carbs() = (calories() - proteins() * 4 - fats() * 9) / 4

    fun water() = weight * waterLevel.value

}