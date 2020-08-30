package application.controllers

import application.constants.VOID_ID
import java.io.Serializable

open class BaseModel(open val id: Int = VOID_ID) : Serializable {

    val isVoid get() = id == VOID_ID

    val isImmutable get() = id < VOID_ID

}