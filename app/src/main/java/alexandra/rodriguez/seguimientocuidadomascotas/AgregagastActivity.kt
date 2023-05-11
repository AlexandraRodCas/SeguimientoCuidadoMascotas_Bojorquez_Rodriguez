package alexandra.rodriguez.seguimientocuidadomascotas

import alexandra.rodriguez.seguimientocuidadomascotas.infogen.GastosActivity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class AgregagastActivity : AppCompatActivity() {
    lateinit var mascota: Mascota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregagast)

        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val btn_continuar: Button = findViewById(R.id.btn_continuar) as Button
        val nombreMas: TextView = findViewById(R.id.nombreMas) as TextView

        val bundle = intent.extras
        if(bundle != null){
            nombreMas.setText(bundle.getString("nombre").toString())
            var imagenS: String = bundle.getString("uri").toString()
            val imagenUri = Uri.parse(imagenS)
            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), imagenUri, bundle.getString("edad").toString() )        }

        btn_back.setOnClickListener {
            var intento = Intent(this, GastosActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            intento.putExtra("uri", mascota.imageUri.toString())
            this.startActivity(intento)
            finish()
        }

        btn_continuar.setOnClickListener {
            var intento = Intent(this, GastosActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            intento.putExtra("uri", mascota.imageUri.toString())
            this.startActivity(intento)
        }
    }
}