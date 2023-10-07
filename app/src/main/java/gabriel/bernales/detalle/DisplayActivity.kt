package gabriel.bernales.detalle

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DisplayActivity : AppCompatActivity() {
    private lateinit var tvDatosAlmacenados: TextView
    private lateinit var btnVolver: Button
    private lateinit var btnGuardar: Button
    private lateinit var miBaseDeDatos: MiBaseDeDates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        tvDatosAlmacenados = findViewById(R.id.tvDatosAlmacenados)
        btnVolver = findViewById(R.id.btnVolver)
        btnGuardar = findViewById(R.id.btnGuardar) // Inicializamos el botón para guardar

        miBaseDeDatos = MiBaseDeDates(this)

        btnVolver.setOnClickListener {
            finish()
        }

        // Mostrar resultados de la base de datos
        mostrarResultados()

        btnGuardar.setOnClickListener {
            // Agrega aquí la lógica para guardar datos en la base de datos
            val miEntidad = MiEntidad(
                id = 0,
                texto1 = "Nuevo Nombre", // Reemplaza con los datos que desees guardar
                texto2 = "Nuevo Apellido Paterno",
                texto3 = "Nuevo Apellido Materno",
                numero1 = 123456, // Reemplaza con los datos que desees guardar
                numero2 = 987654, // Reemplaza con los datos que desees guardar
                booleano = true // Reemplaza con los datos que desees guardar
            )

            val idInsertado = miBaseDeDatos.insertarRegistro(miEntidad)

            if (idInsertado != -1L) {
                // Aquí puedes realizar alguna acción después de guardar los datos
            } else {
                // Maneja el caso de error al insertar el registro
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun mostrarResultados() {
        GlobalScope.launch {
            val datos = miBaseDeDatos.obtenerRegistros()
            val resultado = StringBuilder()
            for (dato in datos) {
                resultado.append("NOMBRE: ${dato.texto1}\n")
                resultado.append("APELLIDO PATERNO: ${dato.texto2}\n")
                resultado.append("APELLIDO MATERNO: ${dato.texto3}\n")
                resultado.append("RUT: ${dato.numero1}\n")
                resultado.append("TELEFONO: ${dato.numero2}\n")
                resultado.append("ES SOLTERO: ${dato.booleano}\n")
                resultado.append("\n")
            }
            runOnUiThread {
                tvDatosAlmacenados.text = resultado.toString()
            }
        }
    }
}