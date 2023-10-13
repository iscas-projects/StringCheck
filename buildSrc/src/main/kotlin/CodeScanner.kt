import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.body.CompactConstructorDeclaration
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.body.RecordDeclaration
import com.github.javaparser.ast.expr.PatternExpr
import com.github.javaparser.ast.stmt.LocalRecordDeclarationStmt
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import java.nio.file.Files
import java.nio.file.Paths

class MethodArgCounter(var count: Int = 0, val name: String = "test"): VoidVisitorAdapter<Void>() {
    override fun visit(n: MethodDeclaration?, arg: Void?) {
        super.visit(n, arg)
        if (n != null && n.nameAsString == name) {
            count = n.parameters.size
        }
    }

    override fun visit(n: LocalRecordDeclarationStmt?, arg: Void?) {
        TODO("Not yet implemented")
    }

    override fun visit(n: RecordDeclaration?, arg: Void?) {
        TODO("Not yet implemented")
    }

    override fun visit(n: CompactConstructorDeclaration?, arg: Void?) {
        TODO("Not yet implemented")
    }

    override fun visit(n: PatternExpr?, arg: Void?) {
        TODO("Not yet implemented")
    }
}


// produce test(sym#sym) from a function test(String a, String b)
// to meet the need of SPF
fun getSymbolicSignature(path: String): String {
    val compUnit = StaticJavaParser.parse(Files.newInputStream(Paths.get(path)))
    val counter = MethodArgCounter()
    counter.visit(compUnit, null)
    return (1..counter.count).joinToString("#") { "sym" }
}

fun main() {
    val compUnit = StaticJavaParser.parse("public class S { int dd() {} int test(int a, int b) {} int ccc(int d) {} }")
    val counter = MethodArgCounter()
    counter.visit(compUnit, null)
    print(counter.count)
}