package com.example.agenda.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
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

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.activity_lista_alunos_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        val itemId = item.itemId
        if (itemId == R.id.activity_lista_alunos_menu_remover) {
            val menuInfo = item.menuInfo as AdapterContextMenuInfo
            val alunoEscolhido = adapter?.getItem(menuInfo.position)
            alunoEscolhido?.let {
                dao.remove(it)
                adapter?.remove(alunoEscolhido)
            }
        }

        return super.onContextItemSelected(item)
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
        registerForContextMenu(listaDeAlunos)
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