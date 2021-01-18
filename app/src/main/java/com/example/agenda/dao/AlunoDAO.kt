package com.example.agenda.dao

import com.example.agenda.model.Aluno

object AlunoDAO {

    private val alunos = ArrayList<Aluno>()
    private var contadorDeId: Int = 1;

    fun salva(alunoCriado: Aluno) {
        alunos.add(alunoCriado)
        Aluno.setId(alunoCriado, contadorDeId)
        atualizaIds()
    }

    private fun atualizaIds() {
        contadorDeId++
    }

    fun edita(aluno: Aluno) {
        alunos.find { it.id == aluno.id }?.let {
            val posicaoAluno: Int = alunos.indexOf(it)
            alunos.set(posicaoAluno, aluno)
        }
    }

    fun todos(): ArrayList<Aluno> {
        return ArrayList(this.alunos)
    }

}
