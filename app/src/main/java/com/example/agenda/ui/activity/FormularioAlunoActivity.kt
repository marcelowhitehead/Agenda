package com.example.agenda.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.agenda.R
import com.example.agenda.dao.AlunoDAO
import com.example.agenda.model.Aluno
import com.example.agenda.ui.activity.ConstantesActivities.Companion.CHAVE_ALUNO

class FormularioAlunoActivity : AppCompatActivity() {

    private var aluno = Aluno()
    private val dao = AlunoDAO
    private lateinit var campoEmail: EditText
    private lateinit var campoTelefone: EditText
    private lateinit var campoNome: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_aluno)

        inicializacaoDosCampos()
        carregaAluno()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_formulario_aluno_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.activity_formulario_aluno_menu_salvar){
            finalizaFormulario()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun carregaAluno() {
        val dados = intent
        if (dados.hasExtra("aluno")) {
            aluno = dados.getSerializableExtra(CHAVE_ALUNO) as Aluno
            preencheCampos()
            findViewById<TextView>(R.id.activity_formulario_aluno_title).setText(com.example.agenda.R.string.editar_aluno)
        }
    }

    private fun preencheCampos() {
        campoNome.setText(aluno.nome)
        campoTelefone.setText(aluno.telefone)
        campoEmail.setText(aluno.email)
    }

    private fun inicializacaoDosCampos() {
        campoNome = findViewById<EditText>(R.id.activity_formulario_aluno_nome)
        campoTelefone = findViewById<EditText>(R.id.activity_formulario_aluno_telefone)
        campoEmail = findViewById(R.id.activity_formulario_aluno_email)
    }

    private fun finalizaFormulario() {
        preencheAluno()
        if (aluno.temIdValido()) {
            dao.edita(aluno)
        } else {
            dao.salva(aluno)
        }
        finish()
    }

    private fun preencheAluno() {
        val nome = campoNome.text.toString()
        val telefone = campoTelefone.text.toString()
        val email = campoEmail.text.toString()

        aluno.nome = nome
        aluno.telefone = telefone
        aluno.email = email
    }
}