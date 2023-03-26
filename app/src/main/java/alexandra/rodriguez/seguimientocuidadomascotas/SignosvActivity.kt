package alexandra.rodriguez.seguimientocuidadomascotas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class SignosvActivity : AppCompatActivity() {
    var botonesMenuSignos=ArrayList<BotonesMenu>()
    var adapter: AdaptadorBotonesS? =null
    lateinit var mascota: Mascota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signosv)

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
        adapter = AdaptadorBotonesS(this, botonesMenuSignos)

        var gridPelis: GridView = findViewById(R.id.mascotasBotonesS)

        gridPelis.adapter = adapter
    }

    fun cargarBotones(){
        botonesMenuSignos.add(BotonesMenu("Signos vitales", R.drawable.frecuenciacardiaca, mascota))
        botonesMenuSignos.add(BotonesMenu("Comportamiento", R.drawable.comportamiento, mascota))
        botonesMenuSignos.add(BotonesMenu("Historial Clinico", R.drawable.historial, mascota))
        botonesMenuSignos.add(BotonesMenu("Información General", R.drawable.informacion, mascota))
    }

    class AdaptadorBotonesS: BaseAdapter {
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
                var intento = Intent(contexto, MascotasperfilActivity::class.java)

                if(boton.name.equals("Signos vitales")){
                    var intento = Intent(contexto, SignosvActivity::class.java)
                    contexto!!.startActivity(intento)
                }
                if(boton.name.equals("Comportamiento")){
                    var intento = Intent(contexto, ComportActivity::class.java)
                    contexto!!.startActivity(intento)
                }
                if(boton.name.equals("Historial Clinico")){
                    var intento = Intent(contexto, HistorialcActivity::class.java)
                    contexto!!.startActivity(intento)
                }
                if(boton.name.equals("Información General")){
                    var intento = Intent(contexto, InfogenActivity::class.java)
                    contexto!!.startActivity(intento)
                }
            }
            return vista
        }
    }
}