package com.senac.msgtexto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var mensagem: EditText
    private lateinit var telefone: EditText
    val CODIGO_RETORNO = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mensagem = findViewById<EditText>(R.id.et_mensagem)
        telefone = findViewById<EditText>(R.id.et_telefone)
    }

    fun enviarMensagem(view: View) {
        val numTel = telefone.text.toString();
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            type = "text/plain"
            data = Uri.parse("smsto:$numTel")
            putExtra("sms_body", mensagem.text.toString())
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, CODIGO_RETORNO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_RETORNO && resultCode == RESULT_OK) {
            AlertDialog.Builder(this)
                .setTitle("Envio")
                .setMessage("Mensagem enviado com sucesso!")
                .setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }.show()
        }
    }
}
