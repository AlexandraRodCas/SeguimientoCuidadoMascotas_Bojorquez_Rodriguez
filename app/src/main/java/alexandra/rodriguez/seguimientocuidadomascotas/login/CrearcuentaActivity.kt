package alexandra.rodriguez.seguimientocuidadomascotas.login

import alexandra.rodriguez.seguimientocuidadomascotas.DuenoperfilActivity
import alexandra.rodriguez.seguimientocuidadomascotas.MainActivity
import alexandra.rodriguez.seguimientocuidadomascotas.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class CrearcuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crearcuenta)

        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val btn_ingresar: Button = findViewById(R.id.btn_ingresar) as Button

        btn_back.setOnClickListener {
            var intento = Intent(this, MainActivity::class.java)
            this.startActivity(intento)
        }

        btn_ingresar.setOnClickListener {
            var intento = Intent(this, DuenoperfilActivity::class.java)
            this.startActivity(intento)
        }
    }
}