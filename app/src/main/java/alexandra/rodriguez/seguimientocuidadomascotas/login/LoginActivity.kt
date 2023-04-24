package alexandra.rodriguez.seguimientocuidadomascotas.login

import alexandra.rodriguez.seguimientocuidadomascotas.DuenoperfilActivity
import alexandra.rodriguez.seguimientocuidadomascotas.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN=123
    val COD_LOGOUT=323
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        val btn_registrarse: TextView = findViewById(R.id.tv_crearCuenta)
        val btn_contra: TextView = findViewById(R.id.tv_olvidasteContra)
        val btn_ingresar: Button = findViewById(R.id.btn_ingresar)
        val sign_in_google: Button = findViewById(R.id.sign_in_google)

        btn_registrarse.setOnClickListener{
            val intentR: Intent = Intent(this, CrearcuentaActivity::class.java)
            startActivity(intentR)
        }

        btn_contra.setOnClickListener{
            val intentC: Intent = Intent(this, ContrasenaActivity::class.java)
            startActivity(intentC)
        }

        btn_ingresar.setOnClickListener{
            val et_correo: EditText = findViewById(R.id.et_correo)
            val et_contra: EditText = findViewById(R.id.et_contra)
            valida_ingreso()
            et_correo.setText("")
            et_contra.setText("")
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_google.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun valida_ingreso(){
        val et_correo: EditText = findViewById(R.id.et_correo)
        val et_contra: EditText = findViewById(R.id.et_contra)

        var correo: String = et_correo.text.toString()
        var contra: String = et_contra.text.toString()

        if(!correo.isNullOrBlank() && !contra.isNullOrBlank()){
            ingresaFirebase(correo, contra)
        }else{
            Toast.makeText(this, "Ingresar datos",
                Toast.LENGTH_SHORT).show()
        }

    }

    private fun ingresaFirebase(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser

                    val intent: Intent = Intent(this, DuenoperfilActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.

                    showAlert()

                }
            }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

        if(requestCode == COD_LOGOUT){
            signOut()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                Toast.makeText(this, "sesión terminada", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if(account!=null){
            val intent = Intent(this, DuenoperfilActivity::class.java)
            intent.putExtra("name", account.displayName)
            intent.putExtra("email", account.email)
            startActivityForResult(intent, COD_LOGOUT)
        }

    }
}