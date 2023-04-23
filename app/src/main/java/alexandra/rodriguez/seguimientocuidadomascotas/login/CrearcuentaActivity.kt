package alexandra.rodriguez.seguimientocuidadomascotas.login

import alexandra.rodriguez.seguimientocuidadomascotas.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CrearcuentaActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crearcuenta)
        auth = Firebase.auth

        val btn_back: ImageView = findViewById(R.id.back) as ImageView
        val btn_ingresar: Button = findViewById(R.id.btn_ingresarReg) as Button

        btn_back.setOnClickListener {
            var intento = Intent(this, LoginActivity::class.java)
            this.startActivity(intento)
        }

        btn_ingresar.setOnClickListener {
            valida_registro()
            var intento = Intent(this, LoginActivity::class.java)
            this.startActivity(intento)

        }
    }
    private fun valida_registro(){
        val et_ombre: EditText = findViewById(R.id.et_nombreReg)
        val et_telefono: EditText = findViewById(R.id.et_telefonoReg)
        val et_ciudad: EditText = findViewById(R.id.et_ciudadReg)
        val et_estado: EditText = findViewById(R.id.et_estadoReg)
        val et_pais: EditText = findViewById(R.id.et_paisReg)
        val et_nacimiento: EditText = findViewById(R.id.et_nacimientoReg)

        val et_correo: EditText = findViewById(R.id.et_correoReg)
        val et_contra1: EditText = findViewById(R.id.et_contraReg)
        val et_contra2: EditText = findViewById(R.id.et_contra2Reg)

        val nombre: String = et_ombre.text.toString()
        val telefono: String = et_telefono.text.toString()
        val ciudad: String = et_ciudad.text.toString()
        val estado: String = et_estado.text.toString()
        val pais: String = et_pais.text.toString()
        val fecha_nacimiento: String = et_nacimiento.text.toString()

        var correo: String = et_correo.text.toString()
        var contra1: String = et_contra1.text.toString()
        var contra2: String = et_contra2.text.toString()

        //if(!correo.isNullOrBlank() && !contra1.isNullOrBlank() && !nombre.isNullOrBlank() && !telefono.isNullOrBlank() && !ciudad.isNullOrBlank() &&
          //  !contra2.isNullOrBlank() && !estado.isNullOrBlank() && !pais.isNullOrBlank() && !fecha_nacimiento.isNullOrBlank()){
        if(!correo.isNullOrBlank() && !contra1.isNullOrBlank() && !contra2.isNullOrBlank()){
            Log.d("EN IF VAIN", fecha_nacimiento)
            if(contra1.length>6 && contra2.length>6) {
                if (contra1 == contra2) {
                    registrarFirebase(correo, contra1)
                } else {
                    Toast.makeText(this, "Las contraseña no coinciden", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Las contraseña debe tener por lo menos 6 caracteres", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Ingresar datos",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarFirebase(email: String, password: String){

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    Toast.makeText(baseContext, "${user?.email}se ha creado correctamente", Toast.LENGTH_SHORT).show()

                    //Metodo para actualizar la vista
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }
}