package gabriel.bernales.detalle

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MiDao {
    @Insert
    suspend fun insertar(miEntidad: MiEntidad)

    @Query("SELECT * FROM mi_tabla")
    suspend fun obtenerTodos(): List<MiEntidad>
}