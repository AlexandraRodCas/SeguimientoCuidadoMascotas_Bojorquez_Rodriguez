package alexandra.rodriguez.seguimientocuidadomascotas

import alexandra.rodriguez.seguimientocuidadomascotas.historial.EnfermedadesActivity
import alexandra.rodriguez.seguimientocuidadomascotas.historial.PadecimientosActivity
import alexandra.rodriguez.seguimientocuidadomascotas.historial.VacunasActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class HistorialcActivity : AppCompatActivity() {
    var botonesMenuSignosH=ArrayList<BotonesMenu>()
    var adapter: BotonesAdaptador? =null
    lateinit var mascota: Mascota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historialc)
        val bundle = intent.extras

        if(bundle != null){

            val imageM: ImageView = findViewById(R.id.imageM)
            val nombreM: TextView = findViewById(R.id.nombreM)
            val edadM: TextView = findViewById(R.id.edadM)

            imageM.setImageResource(bundle.getInt("image"))
            nombreM.setText(bundle.getString("nombre").toString())
            edadM.setText(bundle.getString("edad").toString())

            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), bundle.getString("edad").toString() )
        }

        cargarBotones()
        adapter = BotonesAdaptador(this, botonesMenuSignosH)

        var gridBotones: GridView = findViewById(R.id.mascotasBotonesH)

        gridBotones.adapter = adapter
    }

    fun cargarBotones(){
        botonesMenuSignosH.add(BotonesMenu("Vacunación", R.drawable.vacuna_icono, mascota))
        botonesMenuSignosH.add(BotonesMenu("Padecimientos", R.drawable.padecimientos, mascota))
        botonesMenuSignosH.add(BotonesMenu("Enfermedades", R.drawable.enfermedades, mascota))
    }
}