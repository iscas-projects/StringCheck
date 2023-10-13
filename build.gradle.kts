import org.jetbrains.kotlin.incremental.createDirectory
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"
val dependencyDirectory = "dependencies"
val datasetsDirectory = "datasets"

repositories {
    mavenCentral()
    //maven { url = uri("https://jitpack.io") }
}

// make sure git repo is pulled correctly
buildscript {
    configurations.classpath {
        resolutionStrategy {
            force("org.eclipse.jgit:org.eclipse.jgit:6.7.0")
        }
    }
}

dependencies {
    // TODO: temporarily not used, but hope to work someday
    //implementation("com.github.iscas-zac:SPF:gradle-build-SNAPSHOT")
//    implementation("gov.nasa", "SPF") {
//        version {
//            branch = "gradle-build"
//        }
//    }
    implementation("org.soot-oss:soot:4.4.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

sourceSets.main {
    java {
        srcDirs("src/main/java_src_snippets")
        exclude("src/main/kotlin")
    }
    kotlin {
        srcDirs("src/main/kotlin")
    }
}

tasks.register("generateJpfConfig") {
    dependsOn(tasks.compileJava)
    dependsOn(tasks.compileKotlin)

    doLast {
        fileTree("src/main/java_src_snippets").forEach {
            val baseName = it.name.removeSuffix(".java")
            val srcPath = "src/main/java_src_snippets"
            val dstPath = "$datasetsDirectory/$baseName/"
            val dstFolder = file(dstPath)
            dstFolder.createDirectory()
            copy { from(it); into(dstFolder) }
            copy { from("$buildDir/classes/java/main/$baseName.class"); into(dstFolder) }
            // TODO: move the kotlin code to `buildSrc`
            javaexec {
                args(
                    dstFolder
                )
                logger.lifecycle(dstFolder.name)
                mainClass = "MainKt"
                classpath = sourceSets.main.get().runtimeClasspath
            }


            val config = file("$dstPath$baseName.jpf")
            config.createNewFile()
            config.writeText("""
                target=$baseName
                classpath=${dstFolder.absolutePath.replace("\\", "/")};
                sourcepath=$srcPath
                symbolic.strings = true
    
                symbolic.dp=z3
                symbolic.string_dp=z3str3
                symbolic.string_dp_timeout_ms=3000
                symbolic.debug=true
                verify=gov.nasa.jpf.vm.Verify
    
                symbolic.method=$baseName.test(${getSymbolicSignature(it.absolutePath)})
                search.depth_limit = 10
                #listener = gov.nasa.jpf.symbc.sequences.SymbolicSequenceListener
            """.trimIndent())
        }
    }
}

tasks.compileJava {
    val javaHome = System.getProperty("java.home")
    classpath += files(file("$javaHome/lib/rt.jar"),
        file("$javaHome/lib/rce.jar"),
        file("$javaHome/lib/jsse.jar"))
    options.compilerArgs.add("-g")
    options.compilerArgs.plusAssign("-XDignore.symbol.file")
}

// TODO: here is a 'deprecated' problem
tasks.register<Test>("testJar") {
    testClassesDirs = files(tasks.jar.get().archiveFile)
}

tasks.processResources {
    exclude("*")
}

//tasks.jar {
//    dependsOn(tasks.compileJava)
//    exclude("build/kotlin/main/**")
//    from(tasks.compileJava.get().destinationDirectory)
//    archiveBaseName = "aaa"
//    destinationDirectory = file("${datasetsDirectory}/Src000")
//    logger.lifecycle(tasks.compileJava.get().destinationDirectory.toString())
//}

// multiple tasks to generate jar in batch
tasks.compileJava.get().destinationDirectory.asFile.get().listFiles()?.forEach {
    val baseClassName = it.name.removeSuffix(".class").substringBefore('$')
    tasks.register<Jar>("jarBuilder${it.name.removeSuffix(".class")}") {
        group = "batch_jar_generation"
        dependsOn(tasks.compileJava)
        archiveBaseName = baseClassName
        from(it.absolutePath)
        destinationDirectory = file("$datasetsDirectory/${baseClassName}")
    }
}

// execute multiple jar tasks
tasks.register("separateJar") {
    dependsOn(tasks.compileJava)
    dependsOn(tasks["generateJpfConfig"])
    tasks.compileJava.get().destinationDirectory.asFile.get().listFiles()?.forEach {
        dependsOn(tasks["jarBuilder${it.name.removeSuffix(".class")}"])
    }
    doLast {
        print("jars generated successfully")
    }
}

tasks.register("analyzeDummyInLoop") {
    dependsOn(tasks.compileJava)
    doLast {
        file(datasetsDirectory).listFiles()?.forEach {
            val workingDir = file("${it.absolutePath}/DummyOutput")
            workingDir.mkdir()
            val results = file("${it.absolutePath}/DummyOutput/results.txt")
            results.writeText("No use. Dummy output.")
        }
    }
}

tasks.register("analyzeJustinInLoop") {
    dependsOn(tasks["separateJar"])
    doLast {
        file(datasetsDirectory).listFiles()?.forEach {
            javaexec {
                workingDir = file("${it.absolutePath}/JustinOutput")
                workingDir.mkdir()
                classpath = files("D:\\learning\\JustinStr-New\\out\\artifacts\\JustinStr_jar\\JustinStr.jar")

                logger.lifecycle(it.name)
                args(
                    "--jre",
                    "C:/Program Files/Eclipse Adoptium/jdk-8.0.345.1-hotspot/jre",
                    "--input",
                    "${it.absolutePath}/${it.name}-1.0-SNAPSHOT.jar",
                    "--output",
                    "${it.absolutePath}/JustinOutput"
                )
            }
        }
    }
}

tasks.register("analyzeJpfInLoop") {
//    dependsOn(tasks.compileJava)
//    dependsOn(tasks["generateJpfConfig"])
    doLast {
        //file(datasetsDirectory).listFiles()?.forEach {
        file(datasetsDirectory).listFiles()?.get(8)?.let {
    /*            javaexec {
    //                classpath = files("$dependencyDirectory/jpf-core/build/RunJPF.jar")
    //                jvmArgs = listOf(
    //                    "-Xmx1024m",
    //                    "-ea"
    //                )
    //
    //                workingDir = File(dependencyDirectory)
    //
    //                args(
    //                    "${it.absolutePath}/${it.name}.jpf"
    //                )
    //                standardOutput = FileOutputStream("${it.absolutePath}/${it.name}.jpf.output")
    //            }
     */
            // temporarily use an external project structure
            javaexec {
                classpath = files("D:/IdeaProjects/SPF/jpf-tryout/build/classes/java/main/",
                    fileTree("D:/IdeaProjects/SPF/jpf-core/build/libs/"),
                    fileTree("D:/IdeaProjects/SPF/jpf-core/build/"),
                    fileTree("D:/IdeaProjects/SPF/jpf-symbc/lib/"),
                    fileTree("D:/IdeaProjects/SPF/jpf-symbc/build/libs/"))
                mainClass = "org.example.Main"
                jvmArgs = listOf(
                    "-Xmx1024m",
                    "-ea",
                )
                environment("PATH", "D:\\IdeaProjects\\SPF\\jpf-symbc\\lib\\;"
                        + System.getenv("PATH"))

                println(systemProperties["java.library.path"])

                workingDir = File("D:\\IdeaProjects\\SPF")
                args(
                    "${it.absolutePath}/${it.name}.jpf",
                    "${it.absolutePath}/${it.name}.jpf.output"
                )
            }
        }
    }
}
