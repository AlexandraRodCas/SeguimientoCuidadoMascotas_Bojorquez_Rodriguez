package alexandra.rodriguez.seguimientocuidadomascotas

import alexandra.rodriguez.seguimientocuidadomascotas.adapters.BotonesAdaptador
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class SignosvActivity : AppCompatActivity() {
    var botonesMenuSignos=ArrayList<BotonesMenu>()
    var adapter: BotonesAdaptador? =null
    lateinit var mascota: Mascota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signosv)

        val bundle = intent.extras
        val btn_back: ImageView = findViewById(R.id.back) as ImageView

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
        adapter = BotonesAdaptador(this, botonesMenuSignos)

        var gridBotones: GridView = findViewById(R.id.mascotasBotonesS)

        gridBotones.adapter = adapter

        btn_back.setOnClickListener {
            var intento = Intent(this, MascotasperfilActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }
    }

    fun cargarBotones(){
        botonesMenuSignos.add(BotonesMenu("Frecuencia cardiaca", R.drawable.frecuenciacardiaca, mascota))
        botonesMenuSignos.add(BotonesMenu("Frecuencia respiratoria", R.drawable.frecuenciarespiratoria, mascota))
        botonesMenuSignos.add(BotonesMenu("Temperatura", R.drawable.temperatura, mascota))
        botonesMenuSignos.add(BotonesMenu("Peso", R.drawable.peso, mascota))
    }
}