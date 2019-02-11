package br.edu.jgsilveira.portfolio.netfrixson.view

import android.support.v7.widget.Toolbar

interface IActivityFragment {

    fun setupActionBar(toolbar: Toolbar)

    fun bindData()

    fun attachListeners()

    fun attachObservers()

    fun detachObservers()

}