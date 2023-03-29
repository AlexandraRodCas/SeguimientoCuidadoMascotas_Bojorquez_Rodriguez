package alexandra.rodriguez.seguimientocuidadomascotas.frecres

import alexandra.rodriguez.seguimientocuidadomascotas.*
import alexandra.rodriguez.seguimientocuidadomascotas.freccard.CardiacadActivity
import alexandra.rodriguez.seguimientocuidadomascotas.freccard.ComoCardActivity
import alexandra.rodriguez.seguimientocuidadomascotas.temp.MedicionMTempActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MedicionMRespActivity : AppCompatActivity() {
    var datoManual=ArrayList<Manual>()
    var adapter: ManualAdaptador? =null
    lateinit var mascota: Mascota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicion_mresp)
        val btn_comoMedir: Button = findViewById(R.id.btn_comoMedir) as Button
        val btn_añadir: Button = findViewById(R.id.btn_añadir) as Button
        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val bundle = intent.extras
        if(bundle != null){

            val nombreM: TextView = findViewById(R.id.nombreMas)
            Log.d("NOMBRE", bundle.getString("nombre").toString())
            nombreM.setText(bundle.getString("nombre").toString())

            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), bundle.getString("edad").toString() )
        }

        cargarDatos()
        adapter = ManualAdaptador(this, datoManual)

        var gridBotones: GridView = findViewById(R.id.gridCardiaca)
        gridBotones.adapter = adapter

        btn_back.setOnClickListener {
            var intento = Intent(this, RespiradActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            this.startActivity(intento)
        }

        btn_comoMedir.setOnClickListener {
            var intento = Intent(this, ComoRespActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)

            this.startActivity(intento)
        }
        btn_añadir.setOnClickListener {
            val builder = AlertDialog.Builder(this@MedicionMRespActivity)
            val view = layoutInflater.inflate(R.layout.dato_resp_alert, null)


            builder.setView(view)
            val dialog = builder.create()
            dialog.show()

            val btn_cancelar: Button = view.findViewById(R.id.btn_cancelar) as Button
            val btn_agregar: Button = view.findViewById(R.id.btn_agregar) as Button
            btn_cancelar.setOnClickListener {
                dialog.dismiss()
            }
            btn_agregar.setOnClickListener {
                Toast.makeText(this, "datos agregados", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

        }
    }

    fun cargarDatos(){
        datoManual.add(Manual("9 de marzo", "15xmin - 8:30am", "14xmin - 1:40pm", ""))
        datoManual.add(Manual("8 de marzo", "16xmin - 10:10am", "15xmin - 3:50pm", ""))
    }

    class ManualAdaptador : BaseAdapter {
        var botones = ArrayList<Manual>()
        var contexto: Context? = null

        constructor(contexto: Context, productos: ArrayList<Manual>) {
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
            var boton = botones[p0]
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.cell_resp, null)

            val fecha = vista.findViewById(R.id.fecha) as TextView
            val nombre = vista.findViewById(R.id.frec) as TextView
            val frec2 = vista.findViewById(R.id.frec2) as TextView

            fecha.setText(boton.fecha)
            nombre.setText(boton.dialogo1)
            frec2.setText(boton.dialogo2)

            return vista
        }
    }
}