package br.unipar.viagemapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var destinoViagem: EditText
    private lateinit var dateDisplay: TextView
    private lateinit var countDisplay: TextView
    private lateinit var localListView: ListView
    private lateinit var editTextNumber1: EditText
    private lateinit var editTextNumber2: EditText
    private lateinit var textViewResult: TextView
    private var destinos: ArrayList<Destino> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        destinoViagem = findViewById(R.id.destinoViagem)
        dateDisplay = findViewById(R.id.dateDisplay)
        countDisplay = findViewById(R.id.countDisplay)
        localListView = findViewById(R.id.localListView)
        editTextNumber1 = findViewById(R.id.editTextNumber1)
        editTextNumber2 = findViewById(R.id.editTextNumber2)
        textViewResult = findViewById(R.id.textViewResult)
        val addDestinoButton = findViewById<Button>(R.id.Inserir)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val somaButton = findViewById<Button>(R.id.buttonAdd)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())
        localListView.adapter = adapter

        addDestinoButton.setOnClickListener {
            val name = destinoViagem.text.toString().trim()
            if (name.isNotEmpty()) {
                val currentDate = SimpleDateFormat("dd/MM/yyyy").format(Date())
                val destino = Destino(name, currentDate)
                destinos.add(destino)
                adapter.add("$name - $currentDate")
                adapter.notifyDataSetChanged()
                destinoViagem.text.clear()
                updateCountDisplay()
            } else {
                Toast.makeText(this, "Por favor, preencha o campo destino.", Toast.LENGTH_SHORT).show()
            }
        }

        resetButton.setOnClickListener {
            destinos.clear()
            adapter.clear()
            updateCountDisplay()
        }

        somaButton.setOnClickListener {
            realizarSoma()
        }

        updateDate()
    }

    private fun updateCountDisplay() {
        countDisplay.text = "Quantidade de viagens: ${destinos.size}"
    }

    private fun updateDate() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        dateDisplay.text = "Data atual: $currentDate"
    }

    private fun realizarSoma() {
        val numero1 = editTextNumber1.text.toString().trim()
        val numero2 = editTextNumber2.text.toString().trim()

        if (numero1.isNotEmpty() && numero2.isNotEmpty()) {
            val resultado = numero1.toDoubleOrNull()?.let { n1 ->
                numero2.toDoubleOrNull()?.let { n2 ->
                    n1 + n2
                }
            }

            if (resultado != null) {
                textViewResult.text = String.format("Resultado: %.2f", resultado)
                val currentDate = SimpleDateFormat("dd/MM/yyyy").format(Date())
                adapter.add("Soma: ${resultado} - $currentDate")
                adapter.notifyDataSetChanged()
                destinos.add(Destino("Soma: $resultado", currentDate))
            } else {
                textViewResult.text = "Por favor, insira valores válidos."
            }
        } else {
            textViewResult.text = "Por favor, preencha ambos os campos."
        }
    }
}

data class Destino(val nome: String, val data: String)