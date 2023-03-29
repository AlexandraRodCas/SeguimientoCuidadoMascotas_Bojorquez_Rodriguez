package alexandra.rodriguez.seguimientocuidadomascotas

import alexandra.rodriguez.seguimientocuidadomascotas.adapters.BotonesAdaptador
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        botonesMenuInfo.add(BotonesMenu("Artículos", R.drawable.articulo, mascota))
        botonesMenuInfo.add(BotonesMenu("Galeria", R.drawable.galeria, mascota))
        botonesMenuInfo.add(BotonesMenu("Gastos", R.drawable.presupuesto, mascota))
        botonesMenuInfo.add(BotonesMenu("Calendario", R.drawable.calendario, mascota))
        botonesMenuInfo.add(BotonesMenu("Directorio veterinarios", R.drawable.directorio, mascota))
    }

}