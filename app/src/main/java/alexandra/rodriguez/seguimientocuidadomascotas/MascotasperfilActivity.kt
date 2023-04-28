package alexandra.rodriguez.seguimientocuidadomascotas

import alexandra.rodriguez.seguimientocuidadomascotas.adapters.BotonesAdaptador
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MascotasperfilActivity : AppCompatActivity() {
    var botonesMenuPerfilM=ArrayList<BotonesMenu>()
    var adapter: BotonesAdaptador? =null
    lateinit var mascota: Mascota


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascotasperfil)

        val bundle = intent.extras

        if(bundle != null){

            val imageM: ImageView = findViewById(R.id.imageM)
            val nombreM: TextView = findViewById(R.id.nombreM)
            val edadM: TextView = findViewById(R.id.edadM)

            if(edadM.equals("New Pet")){
                finish()
            }

            imageM.setImageResource(bundle.getInt("image"))
            nombreM.setText(bundle.getString("nombre").toString())
            edadM.setText(bundle.getString("edad").toString())
            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), bundle.getString("edad").toString() )
        }
        cargarBotones()
        adapter = BotonesAdaptador(this, botonesMenuPerfilM)

        var gridPelis: GridView = findViewById(R.id.mascotasBotones)

        gridPelis.adapter = adapter

        val btn_menu: ImageView = findViewById(R.id.menu) as ImageView

        btn_menu.setOnClickListener {
            var intento = Intent(this, MenuActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }
    }

    fun cargarBotones(){
        botonesMenuPerfilM.add(BotonesMenu("Signos vitales", R.drawable.frecuenciacardiaca, mascota))
        botonesMenuPerfilM.add(BotonesMenu("Comportamiento", R.drawable.comportamiento, mascota))
        botonesMenuPerfilM.add(BotonesMenu("Historial Clinico", R.drawable.historial, mascota))
        botonesMenuPerfilM.add(BotonesMenu("Información General", R.drawable.informacion, mascota))
    }

}