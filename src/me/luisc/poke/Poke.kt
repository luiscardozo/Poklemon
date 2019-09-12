package me.luisc.poke

import java.util.*

enum class Pokemones(val id: Int){BULBASAUR(1), CHARMANDER(2), SQUIRTLE(3), PIKACHU(4)}

fun main(args: Array<String>) {
    println("Elige in pokémon")

    for((index, p) in Pokemones.values().withIndex()){
        println("${index + 1}) $p")
    }

    val scanner = Scanner(System.`in`)
    val opcion = scanner.nextInt()

    val pokemon: Pokemon =  generarPokemon(opcion)

    mostrarDatos(pokemon)

    val random = Random()
    val numeroAlAzar = 1 + random.nextInt(4)
    val pokemonSalvaje: Pokemon = generarPokemon(numeroAlAzar)

    println("Un ${pokemonSalvaje.nombre} salvaje ha aparecido!\n")

    do {
        println("${pokemon.nombre} HP: ${pokemon.hp} | ${pokemonSalvaje.nombre} HP: ${pokemonSalvaje.hp}")

        println("Elige un ataque: ")
        for((index, a) in pokemon.listaAtaques.withIndex()){
            println("${index}: ${a.nombre}")
        }

        val ataqueSeleccionado = scanner.nextInt()

        if(procesarAtaque(pokemon, pokemonSalvaje, ataqueSeleccionado)){
            break
        }

        val ataqueAleatorio : Int = 1 + random.nextInt(pokemonSalvaje.listaAtaques.size)
        if(procesarAtaque(pokemonSalvaje, pokemon, ataqueAleatorio)){
            break
        } else {
            println("Los 2 pokemones siguen de pie!!")
            println("continuamos...")
        }

    }while (pokemon.hp > 0 && pokemonSalvaje.hp > 0)
}

fun procesarAtaque(pokemonAtacante: Pokemon, pokemonDefensor: Pokemon, ataqueSeleccionado: Int): Boolean {
    val ataque = pokemonAtacante.obtenerAtaque(ataqueSeleccionado)
    println("${pokemonAtacante.nombre} ha usado ${ataque.nombre}")

    val valorDanho = calcularDanho(pokemonAtacante.ataque, pokemonAtacante.defensa, ataque)

    println("${pokemonDefensor.nombre} ha recibido $valorDanho puntos de daño!")
    pokemonDefensor.hp -= valorDanho

    if(pokemonDefensor.hp <= 0){
        println("${pokemonDefensor.nombre} se agotó")
        println("${pokemonAtacante.nombre} ganó la batalla!")
        return true
    }
    return false
}

fun calcularDanho(valorAtaque: Int, valorDefensa: Int, ataque: Ataque): Int
        = ((((2*1 + 10.0) / 250 ) * (valorAtaque / valorDefensa) * ataque.poder +2) * 1.5).toInt()

fun mostrarDatos(pokemon: Pokemon) {
    println("Has elegido a ${pokemon.nombre}\nHP: ${pokemon.hp}\nATAQUE:${pokemon.ataque}\nDEFENSA:${pokemon.defensa}\n4")
}

fun generarPokemon(opcion: Int): Pokemon = when(opcion){
    Pokemones.BULBASAUR.id -> Pokemon("Bulbasaur", 45, 49, 49, arrayOf(Ataque("vine whip", 45), Ataque("tackle", 40)))
    Pokemones.CHARMANDER.id -> Pokemon("Charmander", 39, 52, 43, arrayOf(Ataque("scratch", 40),Ataque("ember", 40)))
    Pokemones.SQUIRTLE.id -> Pokemon("Squirtle", 44, 48, 65, arrayOf(Ataque("watergun", 40),Ataque("tackle", 40)))
    Pokemones.PIKACHU.id -> Pokemon("Pikachu", 35, 55, 40, arrayOf(Ataque("thunder shock", 40),Ataque("quick attack", 46)))
    else -> Pokemon("Missigno", 33, 136, 0, arrayOf(Ataque("pay day", 20),Ataque("blind", 15)))
}
