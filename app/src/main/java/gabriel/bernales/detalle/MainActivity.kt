package gabriel.bernales.detalle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var apaEditText: EditText
    private lateinit var amaEditText: EditText
    private lateinit var rutEditText: EditText
    private lateinit var fonoEditText: EditText
    private lateinit var guardarButton: Button
    private lateinit var soltCheck: CheckBox
    private lateinit var miBaseDeDatos: MiBaseDeDates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombreEditText = findViewById(R.id.etNombres)
        apaEditText = findViewById(R.id.etApellidoPat)
        amaEditText = findViewById(R.id.etApellidoMat)
        rutEditText = findViewById(R.id.etRut)
        fonoEditText = findViewById(R.id.etFono)
        guardarButton = findViewById(R.id.btnLimpiar)
        soltCheck = findViewById(R.id.cbBooleano)

        // Inicializar la base de datos
        miBaseDeDatos = MiBaseDeDates(this)

        guardarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val apellidopa = apaEditText.text.toString()
            val apellidoma = amaEditText.text.toString()
            val rut = rutEditText.text.toString()
            val fono = fonoEditText.text.toString()
            val soltero = soltCheck.isChecked

            if (nombre.isNotBlank() && rut.isNotBlank()) {
                val miEntidad = MiEntidad(
                    id = 0,
                    texto1 = nombre,
                    texto2 = apellidopa,
                    texto3 = apellidoma,
                    numero1 = rut.toInt(),
                    numero2 = fono.toInt(),
                    booleano = soltero
                )

                val idInsertado = miBaseDeDatos.insertarRegistro(miEntidad)

                if (idInsertado != -1L) {
                    // Limpiar los campos del formulario
                    nombreEditText.text.clear()
                    apaEditText.text.clear()
                    amaEditText.text.clear()
                    rutEditText.text.clear()
                    fonoEditText.text.clear()
                    soltCheck.isChecked = false
                } else {
                    // Manejar error al insertar el registro
                }
            } else {
                // Mostrar mensaje de error si falta información requerida
            }
        }

        // Configurar el botón btnContinuar para abrir DisplayActivity
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        btnContinuar.setOnClickListener {
            val intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)
        }
    }
}














