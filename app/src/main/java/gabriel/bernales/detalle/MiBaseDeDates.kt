package gabriel.bernales.detalle

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MiBaseDeDates(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mi_base_de_datos"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "mi_tabla"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TEXTO1 = "texto1"
        private const val COLUMN_TEXTO2 = "texto2"
        private const val COLUMN_TEXTO3 = "texto3"
        private const val COLUMN_NUMERO1 = "numero1"
        private const val COLUMN_NUMERO2 = "numero2"
        private const val COLUMN_BOOLEANO = "booleano"

        private const val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TEXTO1 TEXT, $COLUMN_TEXTO2 TEXT, $COLUMN_TEXTO3 TEXT, " +
                    "$COLUMN_NUMERO1 INTEGER, $COLUMN_NUMERO2 INTEGER, $COLUMN_BOOLEANO INTEGER);"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertarRegistro(miEntidad: MiEntidad): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TEXTO1, miEntidad.texto1)
        values.put(COLUMN_TEXTO2, miEntidad.texto2)
        values.put(COLUMN_TEXTO3, miEntidad.texto3)
        values.put(COLUMN_NUMERO1, miEntidad.numero1)
        values.put(COLUMN_NUMERO2, miEntidad.numero2)
        values.put(COLUMN_BOOLEANO, if (miEntidad.booleano) 1 else 0)
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun obtenerRegistros(): List<MiEntidad> {
        val registros = ArrayList<MiEntidad>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.run { getColumnIndex(COLUMN_ID) })
                val texto1 = cursor.getString(cursor.run { getColumnIndex(COLUMN_TEXTO1) })
                val texto2 = cursor.getString(cursor.run { getColumnIndex(COLUMN_TEXTO2) })
                val texto3 = cursor.getString(cursor.run { getColumnIndex(COLUMN_TEXTO3) })
                val numero1 = cursor.getInt(cursor.run { getColumnIndex(COLUMN_NUMERO1) })
                val numero2 = cursor.getInt(cursor.run { getColumnIndex(COLUMN_NUMERO2) })
                val booleano = cursor.getInt(cursor.run { getColumnIndex(COLUMN_BOOLEANO) }) == 1

                val miEntidad = MiEntidad(id, texto1, texto2, texto3, numero1, numero2, booleano)
                registros.add(miEntidad)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return registros
    }

    fun actualizarRegistro(miEntidad: MiEntidad): Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TEXTO1, miEntidad.texto1)
        values.put(COLUMN_TEXTO2, miEntidad.texto2)
        values.put(COLUMN_TEXTO3, miEntidad.texto3)
        values.put(COLUMN_NUMERO1, miEntidad.numero1)
        values.put(COLUMN_NUMERO2, miEntidad.numero2)
        values.put(COLUMN_BOOLEANO, if (miEntidad.booleano) 1 else 0)

        val filasActualizadas = db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ID = ?",
            arrayOf(miEntidad.id.toString())
        )
        db.close()
        return filasActualizadas
    }

    fun eliminarRegistro(id: Long): Int {
        val db = writableDatabase
        val filasEliminadas = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return filasEliminadas
    }
}