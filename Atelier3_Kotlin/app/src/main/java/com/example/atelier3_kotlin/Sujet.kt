package com.example.atelier3_kotlin

// Sujet.kt
/**
 * Interface représentant un sujet observable.
 */
interface Sujet {
    fun ajouterObservateur(obs: Class<ObservateurChangement>)
    fun enleverObservateur(obs: ObservateurChangement)
    fun avertirObservateurs()
}