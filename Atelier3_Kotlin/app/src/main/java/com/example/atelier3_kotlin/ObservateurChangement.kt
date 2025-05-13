package com.example.atelier3_kotlin

/**
 * Interface pour recevoir les changements de valeur du sujet.
 */
interface ObservateurChangement {
    fun changement(valeur: Int)
}