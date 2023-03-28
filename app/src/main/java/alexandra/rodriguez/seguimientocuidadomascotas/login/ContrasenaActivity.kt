package alexandra.rodriguez.seguimientocuidadomascotas.login

import alexandra.rodriguez.seguimientocuidadomascotas.MainActivity
import alexandra.rodriguez.seguimientocuidadomascotas.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class ContrasenaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrasena)

        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val btn_continuar: Button = findViewById(R.id.btn_continuar) as Button

        btn_back.setOnClickListener {
            var intento = Intent(this, MainActivity::class.java)
            this.startActivity(intento)
        }

        btn_continuar.setOnClickListener {
            var intento = Intent(this, CodigoActivity::class.java)
            this.startActivity(intento)
        }
    }
}