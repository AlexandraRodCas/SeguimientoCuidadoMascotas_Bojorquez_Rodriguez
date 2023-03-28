package alexandra.rodriguez.seguimientocuidadomascotas.historial

import VacunasMuestra
import alexandra.rodriguez.seguimientocuidadomascotas.HistorialcActivity
import alexandra.rodriguez.seguimientocuidadomascotas.Mascota
import alexandra.rodriguez.seguimientocuidadomascotas.R
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView

class PadecimientosActivity : AppCompatActivity() {
    var botonesMenuV=ArrayList<VacunasMuestra>()
    var adapter: AdaptadorPadecimientos? =null
    lateinit var mascota: Mascota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_padecimientos)
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
        adapter = AdaptadorPadecimientos(this, botonesMenuV)

        var gridBotones: GridView = findViewById(R.id.mascotasPadecimientos) as GridView

        gridBotones.adapter = adapter

        btn_back.setOnClickListener {
            var intento = Intent(this, HistorialcActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }

        btn_añadir.setOnClickListener {
            var intento = Intent(this, AgregarpadeActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }
    }

    fun cargarBotones(){
        botonesMenuV.add(VacunasMuestra("Disnea",
            R.drawable.padecimientos,"3 de enero 2022", mascota))
        botonesMenuV.add(VacunasMuestra("Anorexia canina parcial",
            R.drawable.padecimientos,"4 de novimebre 20224", mascota))
    }

    class AdaptadorPadecimientos: BaseAdapter {
        var botones = ArrayList<VacunasMuestra>()
        var contexto: Context? = null

        constructor(contexto: Context, productos:ArrayList<VacunasMuestra>){
            this.botones = productos
            this.contexto = contexto
        }

        override fun getCount(): Int {
            return botones.size
        }

        override fun getItem(p0: Int): Any {
            return botones[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var boton=botones[p0]
            var inflador= LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.cell_perfil, null)

            val imagen = vista.findViewById(R.id.icono) as ImageView
            val nombre = vista.findViewById(R.id.item) as TextView
            val fecha  = vista.findViewById(R.id.item2) as TextView

            imagen.setImageResource(boton.image)
            nombre.setText(boton.name)
            fecha.setText(boton.fecha)


            return vista
        }
    }
}