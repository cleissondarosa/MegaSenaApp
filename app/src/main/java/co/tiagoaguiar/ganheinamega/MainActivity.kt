package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number) // variaveis que estao no xml, aqui iremos conectar o front com o back
        val txtResult: TextView = findViewById(R.id.txtResult) // variaveis que estao no xml, aqui iremos conectar o front com o back
        val btnGenerate: Button = findViewById(R.id.btn_generator) // variaveis que estao no xml, aqui iremos conectar o front com o back
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result",null)
        if (result != null)
            txtResult.text = "Ultima posta foi: $result"



        btnGenerate.setOnClickListener {
            // evento de click que acontece no botao
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        if (text.isEmpty()) {
            Toast.makeText(this, "Digite um número entre 6 e 15", Toast.LENGTH_LONG)
                .show() //exibe uma mensagem informando que precisa digitar corretamente entre 6 e 15
            return
        }
        val qtd = text.toInt() // converte variavel text para inteiro para fazer o if abaixo
        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Digite um número entre 6 e 15", Toast.LENGTH_LONG)
                .show() //exibe uma mensagem informando que precisa digitar corretamente entre 6 e 15
            return
        }
        val numbers = mutableSetOf<Int>() // isso é uma lista de numeros
        val random = Random() // variavel que vai gerar numeros aleatorios

        while (true) {
            val number = random.nextInt(60) // defino a quantidade de numeros da minha lista
            numbers.add(number + 1) // como a lista é de 0...até 59, aqui eu irei somar sempre com mais um para fechar 60
            if (numbers.size == qtd) {
                break
            } // Se a lista for igual a quantidade que eu informei ao gerar vai sair do loop(While)
        }
        txtResult.text =
            numbers.joinToString(" - ") // exibe o resultado final e separa os numeros com esse -
        val editor = prefs.edit()
        editor.putString("result",txtResult.text.toString())
        editor.apply()
        Toast.makeText(this, "Números gerados com sucesso", Toast.LENGTH_SHORT).show()


    }

}