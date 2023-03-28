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
    var adapter: AdaptadorBotonesI? =null
    lateinit var mascota: Mascota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infogen)
        val bundle = intent.extras

        if(bundle != null){
            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), bundle.getString("edad").toString() )
        }

        cargarBotones()
        adapter = AdaptadorBotonesI(this, botonesMenuInfo)

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

    class AdaptadorBotonesI: BaseAdapter {
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
                if(boton.name.equals("Artículos")){
                    var intentoC = Intent(contexto, VacunasActivity::class.java)
                    intentoC.putExtra("nombre",  boton.mascota.nombre)
                    intentoC.putExtra("image",  boton.mascota.image)
                    intentoC.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intentoC)
                }
                if(boton.name.equals("Galeria")){
                    var intento = Intent(contexto, PadecimientosActivity::class.java)
                    intento.putExtra("nombre",  boton.mascota.nombre)
                    intento.putExtra("image",  boton.mascota.image)
                    intento.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intento)
                }
                if(boton.name.equals("Gastos")){
                    var intento = Intent(contexto, EnfermedadesActivity::class.java)
                    intento.putExtra("nombre",  boton.mascota.nombre)
                    intento.putExtra("image",  boton.mascota.image)
                    intento.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intento)
                }
                if(boton.name.equals("Calendario")){
                    var intento = Intent(contexto, PadecimientosActivity::class.java)
                    intento.putExtra("nombre",  boton.mascota.nombre)
                    intento.putExtra("image",  boton.mascota.image)
                    intento.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intento)
                }
                if(boton.name.equals("Directorio veterinarios")){
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