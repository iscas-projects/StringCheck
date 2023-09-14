import soot.Value
import soot.jimple.Stmt

// TODO: is there something like enum in Rust to wrap this
sealed interface PathItem

class Statement(val stmt: Stmt, private val debugInfo: String = ""): PathItem {
    override fun toString(): String {
        return "Statement: $stmt $debugInfo"
    }
}

sealed interface Condition: PathItem
class Single(val cond: Value, private val debugInfo: String = ""): Condition {
    override fun toString(): String {
        return "Cond: $cond $debugInfo"
    }
}

class Negate(val cond: Condition, private val debugInfo: String = ""): Condition {
    override fun toString(): String {
        return "Negate: $cond $debugInfo"
    }
}

class Union(private val leftCond: Condition, private val rightCond: Condition, private val debugInfo: String = ""):
    Condition {
    override fun toString(): String {
        return "Union: $leftCond and $rightCond $debugInfo"
    }
}

class Intersect(private val leftCond: Condition, private val rightCond: Condition, private val debugInfo: String = ""):
    Condition {
    override fun toString(): String {
        return "Intersect: $leftCond and $rightCond $debugInfo"
    }
}

class Nop(private val debugInfo: String = ""): Condition {
    override fun toString(): String {
        return ""//debugInfo.ifEmpty { "" }
    }
}