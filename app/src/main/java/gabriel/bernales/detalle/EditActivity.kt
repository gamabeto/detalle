package gabriel.bernales.detalle

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {
    private lateinit var nombreEditText: EditText
    private lateinit var apaEditText: EditText
    private lateinit var amaEditText: EditText
    private lateinit var rutEditText: EditText
    private lateinit var fonoEditText: EditText
    private lateinit var soltCheck: CheckBox
    private lateinit var guardarButton: Button
    private lateinit var eliminarButton: Button
    private lateinit var miBaseDeDatos: MiBaseDeDates

    private var entityId: Long = -1 // Variable para almacenar el ID de la entidad a editar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        nombreEditText = findViewById(R.id.etEditNombres)
        apaEditText = findViewById(R.id.etEditApellidoPat)
        amaEditText = findViewById(R.id.etEditApellidoMat)
        rutEditText = findViewById(R.id.etEditRut)
        fonoEditText = findViewById(R.id.etEditFono)
        soltCheck = findViewById(R.id.cbEditBooleano)
        guardarButton = findViewById(R.id.btnEditar)
        eliminarButton = findViewById(R.id.btnEliminar)

        miBaseDeDatos = MiBaseDeDates(this)

        // Obtener el ID de la entidad a editar de los extras del intent
        entityId = intent.getLongExtra("ENTITY_ID", -1)

        // Cargar los datos de la entidad desde la base de datos y mostrarlos en los campos de edición
        cargarDatosDeEntidad()

        guardarButton.setOnClickListener {
            // Obtener los datos de los campos de edición
            val nombre = nombreEditText.text.toString()
            val apellidopa = apaEditText.text.toString()
            val apellidoma = amaEditText.text.toString()
            val rut = rutEditText.text.toString()
            val fono = fonoEditText.text.toString()
            val soltero = soltCheck.isChecked

            // Verificar que el ID de la entidad sea válido
            if (entityId != -1L && nombre.isNotBlank() && rut.isNotBlank()) {
                // Crear una nueva instancia de MiEntidad con los datos actualizados
                val entidadActualizada = MiEntidad(
                    id = entityId,
                    texto1 = nombre,
                    texto2 = apellidopa,
                    texto3 = apellidoma,
                    numero1 = rut.toInt(),
                    numero2 = fono.toInt(),
                    booleano = soltero
                )

                // Actualizar la entidad en la base de datos
                val filasActualizadas = miBaseDeDatos.actualizarRegistro(entidadActualizada)

                if (filasActualizadas > 0) {
                    // Mostrar un mensaje de éxito o realizar alguna acción después de actualizar
                    // Puedes usar un Toast o AlertDialog para mostrar un mensaje al usuario
                } else {
                    // Manejar el caso de error al actualizar la entidad
                    // Puedes mostrar un mensaje de error o realizar una acción apropiada.
                }
            } else {
                // Mostrar un mensaje de error si falta información requerida o el ID no es válido
            }
        }

        eliminarButton.setOnClickListener {
            // Verificar que el ID de la entidad sea válido
            if (entityId != -1L) {
                // Eliminar la entidad de la base de datos
                val filasEliminadas = miBaseDeDatos.eliminarRegistro(entityId)

                if (filasEliminadas > 0) {
                    // Mostrar un mensaje de éxito o realizar alguna acción después de eliminar
                    // Puedes usar un Toast o AlertDialog para mostrar un mensaje al usuario
                    finish() // Cerrar la actividad después de eliminar
                } else {
                    // Manejar el caso de error al eliminar la entidad
                    // Puedes mostrar un mensaje de error o realizar una acción apropiada.
                }
            } else {
                // Mostrar un mensaje de error si el ID no es válido
            }
        }
    }

    private fun cargarDatosDeEntidad() {
        // Verificar que el ID de la entidad sea válido
        if (entityId != -1L) {
            // Obtener la entidad de la base de datos usando el ID
            val entidad = miBaseDeDatos.obtenerRegistros().find { it.id == entityId }

            if (entidad != null) {
                // Mostrar los datos de la entidad en los campos de edición
                nombreEditText.setText(entidad.texto1)
                apaEditText.setText(entidad.texto2)
                amaEditText.setText(entidad.texto3)
                rutEditText.setText(entidad.numero1.toString())
                fonoEditText.setText(entidad.numero2.toString())
                soltCheck.isChecked = entidad.booleano
            } else {
                // Manejar el caso de un ID no encontrado
                // Por ejemplo, puedes mostrar un mensaje de error o realizar una acción apropiada.
            }
        } else {
            // Manejar el caso de un ID no válido
        }
    }
}