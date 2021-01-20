package com.example.agenda.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.agenda.R
import com.example.agenda.dao.AlunoDAO
import com.example.agenda.model.Aluno
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListaAlunosActivity : AppCompatActivity() {

    private var listaDeAlunos: ListView? = null
    private val dao = AlunoDAO
    private var adapter: ArrayAdapter<Aluno>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)
        listaDeAlunos = findViewById<ListView>(R.id.activity_lista_alunos_listview)

        dao.salva(Aluno("Marcelo", "1122223333", "marcelo@gmail.com"))
        dao.salva(Aluno("Flavia", "1144445555", "flavia@gmail.com "))

        configuraLista()
        configuraNovoAluno()
    }

    override fun onResume() {
        super.onResume()

        adapter?.run {
            clear()
            addAll(dao.todos())
        }

    }

    private fun configuraNovoAluno() {
        val botaoAdicionar = findViewById<FloatingActionButton>(R.id.lista_alunos_fab_novo_aluno)
        abreFormularioModoInsereAluno(botaoAdicionar)
    }

    private fun abreFormularioModoInsereAluno(botaoAdicionar: FloatingActionButton) {
        botaoAdicionar.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, FormularioAlunoActivity::class.java))
        })
    }

    private fun configuraLista() {
        val alunos = dao.todos()
        configuraAdapter(alunos)
        configuraItemClickListener(alunos)
        configuraItemLongClickListener(alunos)
    }

    private fun configuraItemLongClickListener(alunos: ArrayList<Aluno>) {
        listaDeAlunos?.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val alunoEscolhido = alunos.get(position)
            dao.remove(alunoEscolhido)
            adapter?.remove(alunoEscolhido)
            adapter?.notifyDataSetChanged()
            true
        }
    }

    private fun configuraItemClickListener(alunos: ArrayList<Aluno>) {
        listaDeAlunos?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val alunoEscolhido = alunos.get(i)
            abreFormularioModoEditaAluno(alunoEscolhido)
        }
    }

    private fun abreFormularioModoEditaAluno(alunoEscolhido: Aluno) {
        startActivity(Intent(this, FormularioAlunoActivity::class.java).putExtra(ConstantesActivities.CHAVE_ALUNO, alunoEscolhido))
    }

    private fun configuraAdapter(alunos: ArrayList<Aluno>) {
        adapter = ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos)
        listaDeAlunos?.adapter = this.adapter
    }

}