package com.example.agenda

import android.app.Application
import com.example.agenda.dao.AlunoDAO
import com.example.agenda.model.Aluno

class AgendaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        criaAlunosTeste()
    }

    private fun criaAlunosTeste() {
        val dao = AlunoDAO
        dao.salva(Aluno("Marcelo", "1122223333", "marcelo@gmail.com"))
        dao.salva(Aluno("Flavia", "1144445555", "flavia@gmail.com "))
    }

}
