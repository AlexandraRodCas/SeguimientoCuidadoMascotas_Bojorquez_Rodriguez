package alexandra.rodriguez.seguimientocuidadomascotas

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class AgregarmascotaActivity : AppCompatActivity() {
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth
    private val REQUEST_CODE = 1
    private lateinit var especie: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregarmascota)

        val btn_continuar:Button = findViewById(R.id.btn_continuar)  as Button
        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val imagen: ImageView = findViewById(R.id.imageM) as ImageView

        val bundle = intent.extras
        if(bundle != null){
            especie = bundle.getString("especie").toString()
            imagen.setImageResource(bundle.getInt("image"))
        }

        imagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE)
        }

        btn_back.setOnClickListener {
            var intento = Intent(this, NuevapetActivity::class.java)
            this.startActivity(intento)
        }
        btn_continuar.setOnClickListener {
            val et_nombre_mas: EditText = findViewById(R.id.nombre_mas)
            val et_fechana: EditText = findViewById(R.id.et_fechana_mas)
            val et_raza_mas: EditText = findViewById(R.id.et_raza_mas)
            val et_tamaño_mas: EditText = findViewById(R.id.et_tamaño_mas)
            val et_alergia_mas: EditText = findViewById(R.id.et_alergia_mas)
            val et_señas_mas: EditText = findViewById(R.id.et_señas_mas)

            val et_historial_mas: EditText = findViewById(R.id.et_historial_mas)
            val et_veterinario_mas: EditText = findViewById(R.id.et_veterinario_mas)
            val et_contacto_mas: EditText = findViewById(R.id.et_contacto_mas)

            val nombre_mas: String = et_nombre_mas.text.toString()
            val fechana: String = et_fechana.text.toString()
            val raza_mas: String = et_raza_mas.text.toString()
            val tamaño_mas: String = et_tamaño_mas.text.toString()
            val alergia_mas: String = et_alergia_mas.text.toString()
            val señas_mas: String = et_señas_mas.text.toString()

            var historial_mas: String = et_historial_mas.text.toString()
            var veterinario_mas: String = et_veterinario_mas.text.toString()
            var contacto_mas: String = et_contacto_mas.text.toString()
            storage = FirebaseFirestore.getInstance()
            usuario = FirebaseAuth.getInstance()


            Log.d("IMAGEN", imagen.drawableState.toString())

            if(!nombre_mas.isNullOrBlank() && !fechana.isNullOrBlank() && !raza_mas.isNullOrBlank() && !tamaño_mas.isNullOrBlank() && !alergia_mas.isNullOrBlank() &&
                !señas_mas.isNullOrBlank() && !historial_mas.isNullOrBlank() && !veterinario_mas.isNullOrBlank() && !contacto_mas.isNullOrBlank()){
                val actividad = hashMapOf("nombreMascota" to nombre_mas, "raza" to raza_mas, "tamaño" to tamaño_mas,
                    "alergia" to alergia_mas, "señas" to señas_mas, "historial" to historial_mas, "fecha nacimiento" to fechana,
                    "veterinario" to veterinario_mas, "contatcto" to contacto_mas,  "email" to usuario.currentUser?.email.toString(),
                "especie" to especie)

                storage.collection("mascotas")
                    .add(actividad)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Mascota agregada", Toast.LENGTH_SHORT).show()
                        if(bundle != null){
                            var mascota = Mascota(nombre_mas, bundle.getInt("image"), fechana)
                            DuenoperfilActivity.mascotasPerfilD.add(mascota)
                        }

                    }.addOnSuccessListener {
                        Toast.makeText(this, "Guardado con exito", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this, "Fallo al guardar"+it.toString(), Toast.LENGTH_SHORT).show()
                    }
                var intento = Intent(this, DuenoperfilActivity::class.java)
                this.startActivity(intento)

            }else{
                Toast.makeText(this, "Ingresar datos",
                    Toast.LENGTH_SHORT).show()
            }



        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val imageUri: Uri? = data?.data
            // Utiliza la imagen seleccionada
            val imagen: ImageView = findViewById(R.id.imageM) as ImageView
            Glide.with(this)
                .load(imageUri)
                .into(imagen)
        }

    }
}