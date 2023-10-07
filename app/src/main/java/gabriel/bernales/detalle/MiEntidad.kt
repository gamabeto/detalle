package gabriel.bernales.detalle

import androidx.room.Entity

@Entity(tableName = "mi_tabla")
data class MiEntidad(
    val id: Long,
    val texto1: String,
    val texto2: String,
    val texto3: String,
    val numero1: Int,
    val numero2: Int,
    val booleano: Boolean
)