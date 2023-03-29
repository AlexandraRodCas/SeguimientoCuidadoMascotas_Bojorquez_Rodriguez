package alexandra.rodriguez.seguimientocuidadomascotas.historial

import alexandra.rodriguez.seguimientocuidadomascotas.*
import alexandra.rodriguez.seguimientocuidadomascotas.adapters.VacunasAdaptador
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class VacunasActivity : AppCompatActivity() {
    var botonesMenuV=ArrayList<VacunasMuestra>()
    var adapter: VacunasAdaptador? =null
    lateinit var mascota: Mascota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacunas)
        val bundle = intent.extras

        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val btn_añadir: Button = findViewById(R.id.btn_añadir) as Button

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
        adapter = VacunasAdaptador(this, botonesMenuV)

        var gridBotones: GridView = findViewById(R.id.mascotasVacunas) as GridView

        gridBotones.adapter = adapter

        btn_back.setOnClickListener {
            var intento = Intent(this, HistorialcActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }

        btn_añadir.setOnClickListener {
            var intento = Intent(this, AgregarvacunaActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }
    }

    fun cargarBotones(){
        botonesMenuV.add(
            VacunasMuestra("Puppy",
            R.drawable.vacuna_icono,"1 de febrero de 2014", mascota)
        )
        botonesMenuV.add(
            VacunasMuestra("Polivalente",
            R.drawable.vacuna_icono,"20 de febrero de 2014", mascota)
        )
        botonesMenuV.add(
            VacunasMuestra("Antirrabica",
            R.drawable.vacuna_icono,"25 de febrero de 2014", mascota)
        )
        botonesMenuV.add(
            VacunasMuestra("Leishmaniosis",
            R.drawable.vacuna_icono,"25 de febrero de 2014", mascota)
        )
    }
}