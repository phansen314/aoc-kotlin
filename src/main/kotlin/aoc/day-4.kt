package aoc

object Day4 {
  data class Section(val min: Int, val max: Int) {
    private val range = min .. max
    operator fun contains(other: Section): Boolean = other.min >= min && other.max <= max

    operator fun contains(x: Int): Boolean = x in range
  }

  private val regex = """(\d*)-(\d*),(\d*)-(\d*)""".toRegex()

  fun parser(line: String): Pair<Section, Section> =
    regex.find(line)!!.destructured.let { (minA, maxA, minB, maxB) ->
      Section(minA.toInt(), maxA.toInt()) to Section(minB.toInt(), maxB.toInt())
    }

  inline fun <R> lines(filename: String = "/aoc/day-4.input", block: Day4.(Sequence<String>) -> R): R =
    this::class.java.getResourceAsStream(filename)!!.bufferedReader().useLines { Day4.block(it) }
}

fun main(args: Array<String>) = Day4.lines { lines ->
  var (part1, part2) = 0 to 0
  for (line in lines) {
    val (sectionA, sectionB) = parser(line)
    if (sectionA in sectionB || sectionB in sectionA) {
      part1++
      part2++
      continue
    }

    if (sectionA.min in sectionB || sectionA.max in sectionB) part2++
  }

  println("Part1: $part1\tPart2: $part2");
}