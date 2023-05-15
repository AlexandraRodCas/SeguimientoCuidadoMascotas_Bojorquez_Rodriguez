package alexandra.rodriguez.seguimientocuidadomascotas.pesito

import alexandra.rodriguez.seguimientocuidadomascotas.Mascota
import alexandra.rodriguez.seguimientocuidadomascotas.MedicionDatos
import alexandra.rodriguez.seguimientocuidadomascotas.R
import alexandra.rodriguez.seguimientocuidadomascotas.SignosvActivity
import alexandra.rodriguez.seguimientocuidadomascotas.adapters.ManualAdaptador
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

class PesodActivity : AppCompatActivity() {
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth
    private lateinit var correo: String
    var datoManual= ArrayList<MedicionDatos>()
    var adapter: ManualAdaptador? =null
    lateinit var mascota: Mascota

    companion object{
        var first = true
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesod)

        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()
        correo = usuario.currentUser?.email.toString()


        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val bundle = intent.extras


        // Crea un GradientDrawable para el fondo de la SeekBar
        val color1 = Color.parseColor("#D0E9E9") // rojo
        val color2 = Color.parseColor("#A0CD68") // amarillo
        val color3 = Color.parseColor("#F7E2BB") // verde
        val color4 = Color.parseColor("#F2AAAE") // azul

        val colors = intArrayOf(color1, color2, color3, color4)
        val positions = floatArrayOf(0.0f, 0.33f, 0.66f, 1.0f)

        val gradient = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)

        gradient.gradientType = GradientDrawable.LINEAR_GRADIENT
        gradient.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        gradient.cornerRadii = floatArrayOf(16f, 16f, 16f, 16f, 16f, 16f, 16f, 16f)
        gradient.gradientRadius = 360f

        val gradientDrawable = LayerDrawable(arrayOf(gradient))
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBar.progressDrawable = gradientDrawable
        seekBar.isEnabled = false

        if(bundle != null){

            val nombreM: TextView = findViewById(R.id.nombreMas)
            nombreM.setText(bundle.getString("nombre").toString())

            var imagenS: String = bundle.getString("uri").toString()
            val imagenUri = Uri.parse(imagenS)
            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), imagenUri, bundle.getString("edad").toString() )        }
        cargarDatos()
        btn_back.setOnClickListener {
            var intento = Intent(this, SignosvActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            intento.putExtra("uri", mascota.imageUri.toString())
            this.startActivity(intento)
        }
        val btn_añadir: Button = findViewById(R.id.btn_añadir) as Button
        btn_añadir.setOnClickListener{
            datosAgregadoHoy()
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarDatos(){
        datoManual = ArrayList()
        val pattern = Pattern.compile("\\d+\\.\\d+")
        var peso: ArrayList<Double> = ArrayList()
        var pesoT: ArrayList<Double> = ArrayList()
        var pesoActualT = ""
        storage.collection("Peso")
            .whereEqualTo("email", correo)
            .whereEqualTo("mascota", mascota.nombre)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    var fechas: ArrayList<String> = ArrayList()
                    val pesoActual: TextView = findViewById(R.id.pesoActual)
                    for (document in documents) {
                        val fechaActual: String = document.getString("fecha").toString()
                        if (fechas.size > 0) {
                            if (fechaExiste(fechas, fechaActual)) {
                                continue
                            } else {
                                fechas.add(fechaActual)
                            }
                        } else {
                            fechas.add(fechaActual)
                        }
                    }
                    if (fechas.isNotEmpty()) {
                        // Ordenar la lista de fechas utilizando el comparador personalizado
                        val fechasOrdenadas = fechas.sortedWith(Comparator { fecha1, fecha2 ->
                            compararFechas(fecha1, fecha2)
                        })
                        val fechasOrdenadasSeparadas = ordenarFechas(fechasOrdenadas)

                        for (fecha in fechasOrdenadasSeparadas) {
                            for (document in documents) {
                                if(fechasOrdenadasSeparadas.first().equals(document.getString("fecha"))){
                                    pesoActualT = document.getString("kg").toString()
                                    pesoActual.text = pesoActualT
                                }
                                if (fecha == document.getString("fecha")) {
                                    val pesoD = document.getString("kg").toString()
                                    if (coincideMesYFechaActual(fecha)) {
                                        val matcher = pattern.matcher(pesoD)
                                        if (matcher.find()) {
                                            val pesoDouble = matcher.group().toDouble()
                                            peso.add(pesoDouble)
                                        }
                                    }

                                    val matcherT = pattern.matcher(pesoD)
                                    if (matcherT.find()) {
                                        val pesoDoubleT = matcherT.group().toDouble()
                                        pesoT.add(pesoDoubleT)
                                    }
                                }
                            }
                        }

                        val progreso: TextView = findViewById(R.id.progreso) as TextView
                        val et_meta: TextView = findViewById(R.id.meta) as TextView
                        val cambioMes: TextView = findViewById(R.id.cambioMes) as TextView
                        var pesoMes = 0.0
                        var pesoTotal = 0.0

                        val str = et_meta.text
                        val target = "kg"
                        val target2 = "Kg"

                        // Obtén el subconjunto del string antes del target
                        val subStr = str.substring(0, str.indexOf(target))
                        // Obtén el subconjunto del string antes del target
                        Log.d("pesoActualT", pesoActualT)
                        //var subStr2 = ""
                        //if(pesoActualT.length > 0){
                        //    subStr2 = pesoActualT.substring(0, pesoActualT.indexOf(target2))
                        //}

                            if (pesoT.isNotEmpty()) {
                                if(peso.isNotEmpty()){
                                    pesoMes = peso.last() - peso.last()
                                }
                                pesoTotal = pesoT.first() - pesoT.last()
                                if(pesoMes < 0){
                                    pesoMes = pesoMes*-1
                                    cambioMes.text = "Perdió $pesoMes kg"
                                }else {
                                    cambioMes.text = "Ganó $pesoMes kg"
                                }
                                if(pesoTotal < 0){
                                    pesoTotal = pesoTotal*-1
                                    progreso.text = "Perdió $pesoTotal kg"
                                } else{
                                    progreso.text = "Ganó $pesoTotal kg"
                                }
                            }
                        val imageView: ImageView = findViewById(R.id.circle)

                        // Obtén la posición actual del ImageView
                        val currentX = imageView.x
                        val currentY = imageView.y
                        var newX = currentX
                        // Calcula la nueva posición a la que quieres mover el ImageView
                        //if(subStr2.toInt() < subStr.toInt()){
                        //    newX = currentX// Desplazamiento en el eje X
                        //}
                        newX = currentX + 2000

                        // Crea una animación de desplazamiento desde la posición actual a la nueva posición
                        val animation = TranslateAnimation(0f, newX - currentX, 0f, currentY)
                        animation.duration = 1000 // Duración de la animación en milisegundos

                        // Aplica la animación al ImageView y actualiza su posición final
                        imageView.startAnimation(animation)
                        imageView.x = newX
                        imageView.y = currentY

                    }

                    }else{
                    Toast.makeText(this, "no se ha ingresado datos", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun coincideMesYFechaActual(fechaString: String): Boolean {
        val formatoFecha = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.getDefault())
        val fecha = LocalDate.parse(fechaString, formatoFecha)
        val mesFecha = fecha.monthValue
        val añoFecha = fecha.year
        val mesActual = LocalDate.now().monthValue
        val añoActual = LocalDate.now().year
        return mesFecha == mesActual && añoFecha == añoActual
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun datosAgregadoHoy(){
        // Obtener la fecha actual
        val currentDate = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale.getDefault()).format(Date())
        var fechaIgual:Boolean = false
        storage.collection("Peso")
            .whereEqualTo("email", correo)
            .whereEqualTo("mascota", mascota.nombre)
            .whereEqualTo("fecha", currentDate)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if(documentSnapshot.size()>0){
                    Toast.makeText(this, "Ya ha registrado el peso hoy", Toast.LENGTH_SHORT).show()
                }else{
                    dialog()
                }

            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun compararFechas(fecha1: String, fecha2: String): Int {
        val format = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale.getDefault())
        val date1 = format.parse(fecha1)
        val date2 = format.parse(fecha2)

        return date1.compareTo(date2)

    }

    fun ordenarFechas(fechas: List<String>): List<String> {
        val format = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale.getDefault())
        return fechas.sortedByDescending { format.parse(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dialog(){

            val builder = AlertDialog.Builder(this@PesodActivity)
            val view = layoutInflater.inflate(R.layout.dato_peso_alert, null)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()

            val btn_cancelar: Button = view.findViewById(R.id.btn_cancelar) as Button
            val btn_agregar: Button = view.findViewById(R.id.btn_agregar) as Button
            val numberPickerU: NumberPicker = view.findViewById(R.id.numberPickerU) as NumberPicker
            val numberPickerD: NumberPicker = view.findViewById(R.id.numberPickerD) as NumberPicker
            val item: TextView = view.findViewById(R.id.item) as TextView
            numberPickerD.maxValue = 10
            numberPickerD.minValue = 0

            // Establecer los valores mínimos y máximos
            numberPickerU.minValue = 0
            numberPickerU.maxValue = 10

            // Crear un arreglo de String con los valores de 10 en 10
            val displayedValues = arrayOfNulls<String>(11)
            val step = BigDecimal("0.1")
            var value = BigDecimal("0.0")

            for (i in 0..10) {
                displayedValues[i] = value.toString()
                value += step
            }

            // Establecer los valores a mostrar en el NumberPicker
            numberPickerU.displayedValues = displayedValues

            item.setText(String.format("%s Kg", numberPickerU.value))
            numberPickerU.setOnValueChangedListener { _, _, newVal ->
                val df = DecimalFormat("#0.0")
                val sum = numberPickerD.value + numberPickerU.value* 0.1
                item.text = String.format("%s Kg", df.format(sum))
            }
            numberPickerD.setOnValueChangedListener { _, _, newVal ->
                val df = DecimalFormat("#0.0")
                val sum = numberPickerD.value + numberPickerU.value* 0.1
                item.text = String.format("%s Kg", df.format(sum))
            }

            btn_cancelar.setOnClickListener {
                dialog.dismiss()
            }
            btn_agregar.setOnClickListener {

                if(!item.text.equals("0 Kg")){

                    val horaActual = LocalTime.now()
                    val hora = horaActual.hour
                    var minutos: String = horaActual.minute.toString()
                    if (horaActual.minute < 10) {
                        minutos = "0" + horaActual.minute
                    }

                    val currentDate =
                        SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale.getDefault()).format(
                            Date()
                        )

                    val actividad = hashMapOf(
                        "fecha" to currentDate,
                        "hora" to convertirHora24("$hora:$minutos"),
                        "mascota" to mascota.nombre,
                        "kg" to item.text.toString(),
                        "email" to usuario.currentUser?.email.toString()
                    )

                    storage.collection("Peso")
                        .add(actividad)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Peso agregada", Toast.LENGTH_SHORT).show()
                            cargarDatos()
                            dialog.dismiss()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this,
                                "Fallo al guardar" + it.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                }else{
                    Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()
                }


        }
    }

    fun fechaExiste(lista: java.util.ArrayList<String>, fecha:String): Boolean{
        for(fechaL in lista){
            if(fechaL.equals(fecha)){
                return true
            }
        }
        return false
    }

    fun convertirHora24(hora: String): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateObj = sdf.parse(hora)
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(dateObj)
    }


}