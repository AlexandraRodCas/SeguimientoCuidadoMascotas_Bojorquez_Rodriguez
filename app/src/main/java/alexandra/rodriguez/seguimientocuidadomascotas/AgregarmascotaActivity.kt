package alexandra.rodriguez.seguimientocuidadomascotas

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.Period

class AgregarmascotaActivity : AppCompatActivity() {
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth
    private val REQUEST_CODE = 1
    private lateinit var especie: String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregarmascota)

        val btn_continuar:Button = findViewById(R.id.btn_continuar)  as Button
        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val imagen: ImageView = findViewById(R.id.imageM) as ImageView

        val editText = findViewById<EditText>(R.id.et_fechana_mas)
        val editText2 = findViewById<EditText>(R.id.et_contacto_mas)
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
        editText2.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario implementar este método
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Este método se llama cada vez que se cambia el texto del EditText
                val text = s.toString()

                if (text.length == 2 && before == 0) {
                    // Si el usuario acaba de escribir el día, agregamos el primer '/'
                    editText.setText("$text/")
                    editText.setSelection(editText.text.length)
                } else if (text.length == 5 && before == 0) {
                    // Si el usuario acaba de escribir el mes, agregamos el segundo '/'
                    editText.setText("$text/")
                    editText.setSelection(editText.text.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No es necesario implementar este método
            }
        })

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
            finish()
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
                if (validarFecha(fechana)) {
                    val actividad = hashMapOf(
                        "nombreMascota" to nombre_mas,
                        "raza" to raza_mas,
                        "tamaño" to tamaño_mas,
                        "alergia" to alergia_mas,
                        "señas" to señas_mas,
                        "historial" to historial_mas,
                        "fecha nacimiento" to fechana,
                        "edad" to calcularEdad(fechana),
                        "veterinario" to veterinario_mas,
                        "contatcto" to contacto_mas,
                        "email" to usuario.currentUser?.email.toString(),
                        "especie" to especie
                    )

                    storage.collection("mascotas")
                        .add(actividad)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Mascota agregada", Toast.LENGTH_SHORT).show()
                            if (bundle != null) {
                                var mascota = Mascota(nombre_mas, bundle.getInt("image"), fechana)
                                DuenoperfilActivity.mascotasPerfilD.add(mascota)
                            }

                        }.addOnSuccessListener {
                            Toast.makeText(this, "Guardado con exito", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this,
                                "Fallo al guardar" + it.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    var intento = Intent(this, DuenoperfilActivity::class.java)
                    this.startActivity(intento)
                }else{
                    Toast.makeText(this, "Fecha de nacimiento invalida", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun validarFecha(fecha: String): Boolean {
        val dateRegex = Regex("^\\d{2}/\\d{2}/\\d{4}$")
        if (!dateRegex.matches(fecha)) {
            return false
        }
        val partesFecha = fecha.split("/")
        val dia = partesFecha[0].toInt()
        val mes = partesFecha[1].toInt()
        val anio = partesFecha[2].toInt()
        val fechaNac = LocalDate.of(anio, mes, dia)
        if (fechaNac.isAfter(LocalDate.now())){
            return false
        }else{
            return dia in 1..31 && mes in 1..12 && anio <= 2023
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calcularEdad(fechaNacimiento: String): String {
        val partesFecha = fechaNacimiento.split("/")
        val dia = partesFecha[0].toInt()
        val mes = partesFecha[1].toInt()
        val anio = partesFecha[2].toInt()
        val fechaNac = LocalDate.of(anio, mes, dia)
        val edad = Period.between(fechaNac, LocalDate.now()).years
        return "$edad años"
    }
}