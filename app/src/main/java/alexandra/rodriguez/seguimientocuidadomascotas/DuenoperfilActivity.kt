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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DuenoperfilActivity : AppCompatActivity() {
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth
    var adapter: AdaptadorMascotas? =null

    companion object{
        var mascotasPerfilD = ArrayList<Mascota>()
        var first = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duenoperfil)
        adapter = AdaptadorMascotas(this, mascotasPerfilD)

        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

        if(first){
            cargarBotones()
            first = false
        }

        var gridPelis: GridView = findViewById(R.id.mascotas)

        gridPelis.adapter = adapter

        gridPelis.invalidate()

        val btn_back: ImageView = findViewById(R.id.back) as ImageView

        btn_back.setOnClickListener {
            finish()
        }
    }

    fun cargarBotones(){
        val correo:String = usuario.currentUser?.email.toString()
        storage.collection("mascotas")
            .whereEqualTo("email", usuario.currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                mascotasPerfilD.removeLast()
                it.forEach{
                    var  image:Int = 0
                    if(it.getString("especie").equals("Felino")){
                        image = R.drawable.cat
                    }
                    if(it.getString("especie").equals("Canino")){
                        image = R.drawable.dog
                    }
                    if(it.getString("especie").equals("Ave")){
                        image = R.drawable.bird
                    }
                    if(it.getString("especie").equals("Pez")){
                        image = R.drawable.pez
                    }
                    if(it.getString("especie").equals("Reptil")){
                        image = R.drawable.lizard
                    }
                    if(it.getString("especie").equals("Insecto")){
                        image = R.drawable.insecto
                    }

                    var nombre:String = it.getString("nombreMascota").toString()
                    var edadString = it.getString("edad").toString()


                    var act = Mascota(nombre, image, edadString)

                    mascotasPerfilD.add(act)

                }
                mascotasPerfilD.add(Mascota(" ", R.drawable.nueva, "New Pet"))

            }

            .addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }

        mascotasPerfilD.add(Mascota("Timon", R.drawable.timon, "9 años"))
        mascotasPerfilD.add(Mascota("Odin", R.drawable.odin, "6 años"))
        mascotasPerfilD.add(Mascota("Silver", R.drawable.silver, "2 años"))
        mascotasPerfilD.add(Mascota(" ", R.drawable.nueva, "New Pet"))

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
                if(mascota.edad.equals("New Pet")){
                    var intento2 = Intent(contexto, NuevapetActivity::class.java)
                    contexto!!.startActivity(intento2)
                }else{
                    contexto!!.startActivity(intento)
                }
            }

            return vista
        }
    }
}