package gabriel.bernales.detalle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class InitActivity : AppCompatActivity() {
    private lateinit var btnIngresar: Button
    private lateinit var btnLista: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnLista = findViewById(R.id.btnLista)


        btnIngresar.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        btnLista.setOnClickListener {

            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }
}

