package alexandra.rodriguez.seguimientocuidadomascotas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView

class NuevapetActivity : AppCompatActivity() {
    var mascotasPerfilD=ArrayList<Mascota>()
    var adapter: AdaptadorMascotas? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevapet)
        cargarBotones()
        adapter = AdaptadorMascotas(this, mascotasPerfilD)

        var gridPelis: GridView = findViewById(R.id.mascotas)

        gridPelis.adapter = adapter

        val btn_back: ImageView = findViewById(R.id.back) as ImageView

        btn_back.setOnClickListener {
            var intento = Intent(this, DuenoperfilActivity::class.java)
            this.startActivity(intento)
        }
    }

    fun cargarBotones(){
        mascotasPerfilD.add(Mascota("Canino", R.drawable.dog, "9 años"))
        mascotasPerfilD.add(Mascota("Felino", R.drawable.cat, "6 años"))
        mascotasPerfilD.add(Mascota("Ave", R.drawable.bird, "2 años"))
        mascotasPerfilD.add(Mascota("Pez", R.drawable.pez, "New Pet"))
        mascotasPerfilD.add(Mascota("Reptil", R.drawable.lizard, "2 años"))
        mascotasPerfilD.add(Mascota("Insecto", R.drawable.insecto, "New Pet"))
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
            var vista = inflador.inflate(R.layout.cell_especie, null)

            var imagen = vista.findViewById(R.id.image_cell) as ImageView
            var especie = vista.findViewById(R.id.especie_cell) as TextView

            imagen.setImageResource(mascota.image)
            especie.setText(mascota.nombre)

            imagen.setOnClickListener {
                var intento = Intent(contexto, AgregarmascotaActivity::class.java)
                intento.putExtra("image", mascota.image)
                contexto!!.startActivity(intento)
            }

            return vista
        }
    }
}