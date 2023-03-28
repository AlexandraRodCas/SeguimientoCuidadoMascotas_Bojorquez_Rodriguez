package alexandra.rodriguez.seguimientocuidadomascotas.frecres

import alexandra.rodriguez.seguimientocuidadomascotas.Mascota
import alexandra.rodriguez.seguimientocuidadomascotas.R
import alexandra.rodriguez.seguimientocuidadomascotas.freccard.MedicionmActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ComoRespActivity : AppCompatActivity() {
    lateinit var mascota: Mascota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_como_resp)

        val btn_back: ImageView = findViewById(R.id.back) as ImageView

        val bundle = intent.extras
        if(bundle != null){

            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), bundle.getString("edad").toString() )
        }

        btn_back.setOnClickListener {
            var intento = Intent(this, MedicionMRespActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }
    }
}