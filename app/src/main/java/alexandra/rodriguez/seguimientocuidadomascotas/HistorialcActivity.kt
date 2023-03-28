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
    var adapter: AdaptadorBotonesH? =null
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
        adapter = AdaptadorBotonesH(this, botonesMenuSignosH)

        var gridBotones: GridView = findViewById(R.id.mascotasBotonesH)

        gridBotones.adapter = adapter
    }

    fun cargarBotones(){
        botonesMenuSignosH.add(BotonesMenu("Vacunación", R.drawable.vacuna_icono, mascota))
        botonesMenuSignosH.add(BotonesMenu("Padecimientos", R.drawable.padecimientos, mascota))
        botonesMenuSignosH.add(BotonesMenu("Enfermedades", R.drawable.enfermedades, mascota))
    }

    class AdaptadorBotonesH: BaseAdapter {
        var botones = ArrayList<BotonesMenu>()
        var contexto: Context? = null

        constructor(contexto: Context, productos:ArrayList<BotonesMenu>){
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
            val shape  = vista.findViewById(R.id.shape) as LinearLayout

            imagen.setImageResource(boton.image)
            nombre.setText(boton.name)

            shape.setOnClickListener{

                if(boton.name.equals("Vacunación")){
                    var intentoC = Intent(contexto, VacunasActivity::class.java)
                    intentoC.putExtra("nombre",  boton.mascota.nombre)
                    intentoC.putExtra("image",  boton.mascota.image)
                    intentoC.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intentoC)
                }
                if(boton.name.equals("Padecimientos")){
                    var intento = Intent(contexto, PadecimientosActivity::class.java)
                    intento.putExtra("nombre",  boton.mascota.nombre)
                    intento.putExtra("image",  boton.mascota.image)
                    intento.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intento)
                }
                if(boton.name.equals("Enfermedades")){
                    var intento = Intent(contexto, EnfermedadesActivity::class.java)
                    intento.putExtra("nombre",  boton.mascota.nombre)
                    intento.putExtra("image",  boton.mascota.image)
                    intento.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intento)
                }

            }
            return vista
        }
    }
}