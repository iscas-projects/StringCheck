import soot.SootMethod
import soot.Unit
import soot.Value
import soot.jimple.GotoStmt
import soot.jimple.IfStmt
import soot.jimple.Stmt
import soot.jimple.internal.JAssignStmt
import soot.toolkits.graph.Block
import soot.toolkits.graph.BlockGraph
import javax.swing.plaf.nimbus.State

/// note here the representation of a path is List<Block>
/// with the tail appear at path[0]

fun <T> listWrapper(items: Collection<T>): Set<List<T>> {
    return items.map { listOf(it) }.toSet()
}

fun <T> Set<List<T>>.updateThoseEndWith(
    name: T,
    isKeepOriginalPath: Boolean,
    policy: (List<T>) -> List<List<T>>
): Set<List<T>> {
    val bipart: Pair<List<List<T>>, List<List<T>>> =
        this.partition { it.isEmpty() || it[0] == name }
    val updated: List<List<T>> = bipart.first.map { policy(it) }
        .flatten()
    //updated += bipart.second;
    return (updated + if (!isKeepOriginalPath) bipart.second else this).toSet();
}

fun <T> Set<List<T>>.updateByEachIndependentInsertion(
    name: T,
    items: List<T>,
    isKeepOriginalPath: Boolean
): Set<List<T>> {
    return this.updateThoseEndWith(
        name,
        isKeepOriginalPath
    ) { l: List<T> -> // copy list For Each item And Insert the item At Head
        items.map { // (l.asReversed() + it).asReversed()
            val temp = l.toMutableList();
            temp.add(0, it);
            temp
        }
    }
}

fun <T> Set<List<T>>.getAllVariablesShownInChains(): Set<T> {
    return this.flatten()
        .toSet()
}

fun <T> Set<List<T>>.getItemsAppearingInEachPathsNoMoreThan(items: Set<T>, times: Int): List<T> {
    return items.filter { item ->
        this.all { lst -> lst.count { it == item } < times }
    }
}

fun constructPath(cfg: BlockGraph): Set<List<Block>> {
    var items = cfg.heads.toSet()
    if (items.isEmpty()) return emptySet()
    var growingPaths = listWrapper(items)
    val finalPaths = emptySet<List<Block>>().toMutableSet()
    while (items.any { !cfg.tails.contains(it) }) {
        items = growingPaths.mapNotNull { it.firstOrNull() }.toSet()
        items = growingPaths.getItemsAppearingInEachPathsNoMoreThan(items, 2).toSet()
        items.forEach { block ->
            growingPaths = growingPaths.updateByEachIndependentInsertion(block, cfg.getSuccsOf(block), false)
        }
        finalPaths += growingPaths.filter { cfg.tails.contains(it.first()) }
    }
    return finalPaths
}

fun <T> Set<List<T>>.filterOutNotContainAny(items: Collection<T>): Set<List<T>> {
    return this.filter { path: List<T> -> path.any { items.contains(it) } }.toSet()
}

class Slicer(val programPath: List<Block>) {
    private val stmts = programPath.asReversed().map { it.toList() }.flatten()
    private var constraintChain: List<Condition>? = null;

    private fun getStringRelatedVars(): List<Value> {
        return stmts.filter { unit ->
            (unit as Stmt).containsInvokeExpr() &&
                    unit.invokeExpr.method.let {
                        it.name.contains("toString") ||
                                it.signature.contains("java.lang.String")
                    }
        }.flatMap { it.defBoxes }
            .map { it.value }
    }

    fun getStringRelatedSlice(): List<Unit> { //TODO: some refactor as this may have an idiom in kotlin
        var concernedVars = getStringRelatedVars().toSet()
        var updatedVars: Set<Value> = emptySet()
        val valueEquivGroups = stmts.map { getParameterAndReceiver(it as Stmt) }
        while (concernedVars != updatedVars) {
            concernedVars = concernedVars.union(updatedVars)
            updatedVars =
                concernedVars.flatMap { variable -> valueEquivGroups.filter { group -> group.contains(variable) } }
                    .flatten()
                    .toSet()
        }
        return stmts.zip(valueEquivGroups)
            .filter { (_, group) ->
                group.intersect(concernedVars).isNotEmpty()
            }.map { it.first }
    }

    fun getPathConstraints(): List<Condition> {
        if (constraintChain == null)
            constraintChain = programPath.asReversed().zipWithNext().map { (prev, next) ->
                prev.tail.let { jumpStatement ->
                    if (jumpStatement is IfStmt && jumpStatement.fallsThrough())
                        if (jumpStatement.target == next.head)
                            Single(jumpStatement.condition)
                        else Negate(Single(jumpStatement.condition))
                    else if (jumpStatement is GotoStmt)
                        Nop()
                    else Nop("DEBUG: $jumpStatement")
                }
            }
        return constraintChain as List<Condition>
    }

    fun getPath(): List<PathItem> {
        val exceptLastOne = programPath.asReversed().dropLast(1)
        return exceptLastOne.zip(getPathConstraints()).map { (block, cond) ->
            block.map { Statement(it as Stmt) } + cond
        }.flatten() +
                (programPath.getOrNull(0)?.map { Statement(it as Stmt) }?: emptyList())
    }

    private fun getApiTypes(): Map<SootMethod, Int> {
        return stmts.mapNotNull { unit ->
            if ((unit as Stmt).containsInvokeExpr())
                unit.invokeExpr.method
            else null
        }.filter { it.name.contains("toString") ||
                it.signature.contains("java.lang.String") ||
                it.signature.contains("CharSequence")
        }.groupBy { it }
            .mapValues { it.value.count() }
    }

    fun getStatistics() = "involved APIs: ${getApiTypes().entries.joinToString("\n")}\n" +
            "longest API chain: \n"
}

fun getParameterAndReceiver(stmt: Stmt): List<Value> {
    val result: MutableList<Value> = mutableListOf();
    if (stmt is JAssignStmt)
        result.add(stmt.leftOpBox.value)
    if (stmt.containsInvokeExpr())
        result.addAll(stmt.invokeExpr.args)
    return result;
}

fun hasStringOps(bl: Block): Boolean {
    return bl.any { unit ->
        (unit as Stmt).containsInvokeExpr() &&
                unit.invokeExpr.method.let { it.name.contains("toString") || it.signature.contains("java.lang.String") }
    }
//        if (u.getUseAndDefBoxes().stream().anyMatch { box: ValueBox ->
//                box.value.type.toString().contains("String")
}

fun main() {

    var a = listOf(listOf(1, 2), listOf(3, 4, 5), listOf(1, 3))
    print(111)
}