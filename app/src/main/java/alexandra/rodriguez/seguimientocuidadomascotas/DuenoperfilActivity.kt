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

class DuenoperfilActivity : AppCompatActivity() {
    var mascotasPerfilD=ArrayList<Mascota>()
    var adapter: AdaptadorMascotas? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duenoperfil)
        cargarBotones()
        adapter = AdaptadorMascotas(this, mascotasPerfilD)

        var gridPelis: GridView = findViewById(R.id.mascotas)

        gridPelis.adapter = adapter

        val btn_back: ImageView = findViewById(R.id.back) as ImageView

        btn_back.setOnClickListener {
            finish()
        }
    }

    fun cargarBotones(){
        mascotasPerfilD.add(Mascota("Timon", R.drawable.timon, "9 años"))
        mascotasPerfilD.add(Mascota("Odin", R.drawable.odin, "6 años"))
        mascotasPerfilD.add(Mascota("Silver", R.drawable.silver, "2 años"))
        mascotasPerfilD.add(Mascota(" ", R.drawable.duda, "New Pet"))
    }

    class AdaptadorMascotas: BaseAdapter {
        var botones = ArrayList<Mascota>()
        var contexto: Context?=null

        constructor(contexto: Context, productos:ArrayList<Mascota>){
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
            var mascota=botones[p0]
            var inflador= LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.cell_mascota, null)

            var imagen = vista.findViewById(R.id.image_cell) as ImageView
            var nombre = vista.findViewById(R.id.mascota_nombre_cell) as TextView
            var edad = vista.findViewById(R.id.mascota_edad_cell) as TextView

            imagen.setImageResource(mascota.image)
            nombre.setText(mascota.nombre)
            edad.setText(mascota.edad)

            imagen.setOnClickListener {
                var intento = Intent(contexto, MascotasperfilActivity::class.java)
                intento.putExtra("nombre", mascota.nombre)
                intento.putExtra("image", mascota.image)
                intento.putExtra("edad", mascota.edad)
                contexto!!.startActivity(intento)
                if(mascota.edad.equals("New Pet")){
                    var intento2 = Intent(contexto, NuevapetActivity::class.java)
                    contexto!!.startActivity(intento2)
                }
            }

            return vista
        }
    }
}