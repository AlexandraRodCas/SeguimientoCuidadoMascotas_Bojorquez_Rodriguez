package alexandra.rodriguez.seguimientocuidadomascotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class ComportActivity : AppCompatActivity() {
    lateinit var mascota: Mascota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comport)

        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val btn_continuar: Button = findViewById(R.id.btn_confirmar) as Button
        val nombreMas: TextView = findViewById(R.id.nombreM) as TextView
        val imageM: ImageView = findViewById(R.id.imageM) as ImageView

        val bundle = intent.extras
        if(bundle != null){
            imageM.setImageResource(bundle.getInt("image"))
            nombreMas.setText(bundle.getString("nombre").toString())
            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), bundle.getString("edad").toString() )
        }

        btn_back.setOnClickListener {
            var intento = Intent(this, MascotasperfilActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }

        btn_continuar.setOnClickListener {
            Toast.makeText(this, "Comportamiento agregado", Toast.LENGTH_SHORT).show()
            var intento = Intent(this, MascotasperfilActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }
    }
}