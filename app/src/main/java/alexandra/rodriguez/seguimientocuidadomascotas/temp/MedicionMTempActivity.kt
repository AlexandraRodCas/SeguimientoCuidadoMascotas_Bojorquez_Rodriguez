package alexandra.rodriguez.seguimientocuidadomascotas.temp

import alexandra.rodriguez.seguimientocuidadomascotas.Mascota
import alexandra.rodriguez.seguimientocuidadomascotas.R
import alexandra.rodriguez.seguimientocuidadomascotas.freccard.ComoCardActivity
import alexandra.rodriguez.seguimientocuidadomascotas.frecres.RespiradActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MedicionMTempActivity : AppCompatActivity() {
    lateinit var mascota: Mascota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicion_mtemp)
        val btn_comoMedir: Button = findViewById(R.id.btn_comoMedir) as Button
        val btn_añadir: Button = findViewById(R.id.btn_añadir) as Button
        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val bundle = intent.extras
        if(bundle != null){

            val nombreM: TextView = findViewById(R.id.nombreMas)
            Log.d("NOMBRE", bundle.getString("nombre").toString())
            nombreM.setText(bundle.getString("nombre").toString())

            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), bundle.getString("edad").toString() )
        }

        btn_back.setOnClickListener {
            var intento = Intent(this, TemperaturadActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }

        btn_comoMedir.setOnClickListener {
            var intento = Intent(this, ComoTempActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)

            this.startActivity(intento)
        }
        btn_añadir.setOnClickListener {
            val builder = AlertDialog.Builder(this@MedicionMTempActivity)
            val view = layoutInflater.inflate(R.layout.dato_temp_alert, null)


            builder.setView(view)
            val dialog = builder.create()
            dialog.show()

            val btn_cancelar: Button = view.findViewById(R.id.btn_cancelar) as Button
            val btn_agregar: Button = view.findViewById(R.id.btn_agregar) as Button
            btn_cancelar.setOnClickListener {
                dialog.dismiss()
            }
            btn_agregar.setOnClickListener {
                Toast.makeText(this, "datos agregados", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

        }
    }
}