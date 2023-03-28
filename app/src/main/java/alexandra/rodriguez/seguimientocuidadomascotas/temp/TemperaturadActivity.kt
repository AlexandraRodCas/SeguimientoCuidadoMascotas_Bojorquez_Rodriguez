package alexandra.rodriguez.seguimientocuidadomascotas.temp

import alexandra.rodriguez.seguimientocuidadomascotas.Mascota
import alexandra.rodriguez.seguimientocuidadomascotas.R
import alexandra.rodriguez.seguimientocuidadomascotas.SignosvActivity
import alexandra.rodriguez.seguimientocuidadomascotas.frecres.MedicionMRespActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class TemperaturadActivity : AppCompatActivity() {
    lateinit var mascota: Mascota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperaturad)

        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val btn_manual: Button = findViewById(R.id.btn_manual) as Button
        val bundle = intent.extras
        if(bundle != null){

            val nombreM: TextView = findViewById(R.id.nombreMas)
            Log.d("NOMBRE", bundle.getString("nombre").toString())
            nombreM.setText(bundle.getString("nombre").toString())

            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), bundle.getString("edad").toString() )
        }

        btn_back.setOnClickListener {
            var intento = Intent(this, SignosvActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }

        btn_manual.setOnClickListener {
            var intento = Intent(this, MedicionMTempActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }
    }
}