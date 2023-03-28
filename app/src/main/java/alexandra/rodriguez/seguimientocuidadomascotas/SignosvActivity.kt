package alexandra.rodriguez.seguimientocuidadomascotas

import alexandra.rodriguez.seguimientocuidadomascotas.freccard.CardiacadActivity
import alexandra.rodriguez.seguimientocuidadomascotas.frecres.RespiradActivity
import alexandra.rodriguez.seguimientocuidadomascotas.pesito.PesodActivity
import alexandra.rodriguez.seguimientocuidadomascotas.temp.TemperaturadActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        adapter = AdaptadorBotonesS(this, botonesMenuSignos)

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

                if(boton.name.equals("Frecuencia cardiaca")){
                    var intentoC = Intent(contexto, CardiacadActivity::class.java)
                    intentoC.putExtra("nombre",  boton.mascota.nombre)
                    intentoC.putExtra("image",  boton.mascota.image)
                    intentoC.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intentoC)
                }
                if(boton.name.equals("Frecuencia respiratoria")){
                    var intento = Intent(contexto, RespiradActivity::class.java)
                    intento.putExtra("nombre",  boton.mascota.nombre)
                    intento.putExtra("image",  boton.mascota.image)
                    intento.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intento)
                }
                if(boton.name.equals("Temperatura")){
                    var intento = Intent(contexto, TemperaturadActivity::class.java)
                    intento.putExtra("nombre",  boton.mascota.nombre)
                    intento.putExtra("image",  boton.mascota.image)
                    intento.putExtra("edad", boton.mascota.edad)
                    contexto!!.startActivity(intento)
                }
                if(boton.name.equals("Peso")){
                    var intento = Intent(contexto, PesodActivity::class.java)
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