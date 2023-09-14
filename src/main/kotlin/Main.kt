import soot.*
import soot.options.Options;
import soot.toolkits.graph.Block
import soot.toolkits.graph.ExceptionalBlockGraph
import java.io.File
import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    // args: [path]
    println(args[0])
    for (pathsOfFunc in slice(args[0])) {
        val dir = File(args[0] + File.separator + pathsOfFunc.key.replace("<", "《").replace(">", "》"))
            println(dir)
        if (dir.mkdir()) {
            pathsOfFunc.value.forEachIndexed { index, path ->
                File(dir, "$index.path").writeText(path)
            }
        }
    }
}

fun slice(classPath: String): Map<String, List<String>> {
    G.reset()
    Options.v().set_prepend_classpath(true)

    Options.v().set_src_prec(Options.src_prec_class)
    //Options.v().set_src_prec(Options.src_prec_apk);
    //Options.v().set_android_jars(android_jars);
    Options.v().set_process_dir(Collections.singletonList(classPath))
    Options.v().set_allow_phantom_refs(true)
    Scene.v().addBasicClass("java.lang.String", SootClass.BODIES)
    Scene.v().loadNecessaryClasses()
    val pathsOfFunc = HashMap<String, List<String>>()

    PackManager.v().getPack("jtp").add(Transform("jtp.mySlicer", object : BodyTransformer() {
        override fun internalTransform(b: Body?, phaseName: String?, options: MutableMap<String, String>?) {
            val blockCFG = ExceptionalBlockGraph(b)
            var paths = constructPath(blockCFG)
//            val blocksWithStringOps = blockCFG.blocks.filter { hasStringOps(it) }
//            paths = paths.filterOutNotContainAny(blocksWithStringOps)
            val slicers = paths.map { Slicer(it) }
            if (b?.method?.name != null)
                pathsOfFunc[b.method?.name!!] = slicers.map { it.getPath().joinToString("\n") }
        }
    }))
    PackManager.v().runPacks()
    return pathsOfFunc
}