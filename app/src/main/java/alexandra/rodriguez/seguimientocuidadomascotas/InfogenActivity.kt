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

class InfogenActivity : AppCompatActivity() {
    var botonesMenuInfo=ArrayList<BotonesMenu>()
    var adapter: BotonesAdaptador? =null
    lateinit var mascota: Mascota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infogen)
        val bundle = intent.extras

        if(bundle != null){
            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), bundle.getString("edad").toString() )
        }

        cargarBotones()
        adapter = BotonesAdaptador(this, botonesMenuInfo)

        var gridBotones: GridView = findViewById(R.id.mascotasBotonesIn)

        gridBotones.adapter = adapter
    }

    fun cargarBotones(){
        botonesMenuInfo.add(BotonesMenu("Artículos", R.drawable.vacuna_icono, mascota))
        botonesMenuInfo.add(BotonesMenu("Galeria", R.drawable.padecimientos, mascota))
        botonesMenuInfo.add(BotonesMenu("Gastos", R.drawable.enfermedades, mascota))
        botonesMenuInfo.add(BotonesMenu("Calendario", R.drawable.padecimientos, mascota))
        botonesMenuInfo.add(BotonesMenu("Directorio veterinarios", R.drawable.enfermedades, mascota))
    }


}