package alexandra.rodriguez.seguimientocuidadomascotas

import alexandra.rodriguez.seguimientocuidadomascotas.login.ContrasenaActivity
import alexandra.rodriguez.seguimientocuidadomascotas.login.CrearcuentaActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.hide()
        val btn_registrarse: TextView = findViewById(R.id.tv_crearCuenta)
        val btn_contra: TextView = findViewById(R.id.tv_olvidasteContra)
        val btn_ingresar: Button = findViewById(R.id.btn_ingresar)

        btn_registrarse.setOnClickListener{
            val intentR: Intent = Intent(this, CrearcuentaActivity::class.java)
            startActivity(intentR)
        }

        btn_contra.setOnClickListener{
            val intentC: Intent = Intent(this, ContrasenaActivity::class.java)
            startActivity(intentC)
        }

        btn_ingresar.setOnClickListener{
            val intentI: Intent = Intent(this, DuenoperfilActivity::class.java)
            startActivity(intentI)
        }
    }
}