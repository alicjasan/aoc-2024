package com.adventofcode

import com.adventofcode.util.readText

fun main() {
    val corruptedMemory = readText("day03")
    val mulRegex = Regex("""mul\s*\(\s*(\d+)\s*,\s*(\d+)\s*\)""")
    val doRegex = Regex("""\b\w*do\w*""") // do/words containing do
    val dontRegex = Regex("""\b\w*don't\w*""") // don't and words containing don't

    println(findFunctionsAndMultiply(mulRegex, doRegex, dontRegex, corruptedMemory))
}

private fun findFunctionsAndMultiply(
    mulRegex: Regex,
    doRegex: Regex,
    dontRegex: Regex,
    corruptedMemory: String
): Int {
    var isProcessingEnabled = true
    var currentIndex = 0
    val extractedProducts = mutableListOf<Int>()

    while (currentIndex < corruptedMemory.length) {
        val mulMatch = mulRegex.find(corruptedMemory, currentIndex)

        if (mulMatch != null) {
            val textBeforeMatch = corruptedMemory.substring(0, mulMatch.range.first)

            val lastDoIndex = doRegex.findAll(textBeforeMatch).lastOrNull()?.range?.last ?: -1
            val lastDontIndex = dontRegex.findAll(textBeforeMatch).lastOrNull()?.range?.last ?: -1

            isProcessingEnabled = when {
                lastDoIndex > lastDontIndex -> true
                lastDontIndex > lastDoIndex -> false
                else -> isProcessingEnabled
            }

            if (isProcessingEnabled) {
                val (num1, num2) = mulMatch.destructured
                extractedProducts.add(num1.toInt() * num2.toInt())
            }

            currentIndex = mulMatch.range.last + 1
        } else {
            break
        }
    }

    return extractedProducts.sum()
}