package alexandra.rodriguez.seguimientocuidadomascotas.login

import alexandra.rodriguez.seguimientocuidadomascotas.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class CodigoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_codigo)

        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val btn_cotinuar: Button = findViewById(R.id.btn_cotinuar) as Button

        btn_back.setOnClickListener {
            var intento = Intent(this, ContrasenaActivity::class.java)
            this.startActivity(intento)
        }

        btn_cotinuar.setOnClickListener {
            var intento = Intent(this, NuevacontrasenaActivity::class.java)
            this.startActivity(intento)
        }
    }
}