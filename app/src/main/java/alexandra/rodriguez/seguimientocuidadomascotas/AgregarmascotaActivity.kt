package alexandra.rodriguez.seguimientocuidadomascotas

import alexandra.rodriguez.seguimientocuidadomascotas.freccard.MedicionmActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class AgregarmascotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregarmascota)

        val btn_continuar:Button = findViewById(R.id.btn_continuar)  as Button
        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val imagen: ImageView = findViewById(R.id.imageM) as ImageView

        val bundle = intent.extras
        if(bundle != null){
            imagen.setImageResource(bundle.getInt("image"))
        }

        btn_back.setOnClickListener {
            var intento = Intent(this, NuevapetActivity::class.java)
            this.startActivity(intento)
        }
        btn_continuar.setOnClickListener {
            var intento = Intent(this, DuenoperfilActivity::class.java)
            this.startActivity(intento)
        }
    }
}