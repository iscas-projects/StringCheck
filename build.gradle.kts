plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"
var dependencyDirectory = "dependencies"

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
    testImplementation(kotlin("test"))

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
}
tasks.register("generateJpfConfig") {
    doLast {
        fileTree("src/main/java_src_snippets").forEach {
            val baseName = it.name.removeSuffix(".java")
            val srcPath = "src/main/java_src_snippets"
            val dstPath = buildDir.toString().replace("\\", "/") + "/classes/java/main/"
            val config = file("$dstPath$baseName.jpf")
            config.createNewFile()
            config.writeText("""
                target=$baseName
                classpath=$dstPath;
                classpath+=${dependencyDirectory};
                sourcepath=$srcPath
    
                symbolic.dp=choco
                symbolic.string_dp=automata
                symbolic.string_dp_timeout_ms=3000
    
                symbolic.method=$baseName.test(sym)
                search.depth_limit = 10
                listener = gov.nasa.jpf.symbc.sequences.SymbolicSequenceListener
            """.trimIndent())
        }
    }
}

tasks.compileJava {
    options.compilerArgs.add("-g")
}

// TODO: here is a 'deprecated' problem
tasks.register<Test>("testJar") {
    testClassesDirs = files(tasks.jar.get().archiveFile)
}

tasks.processResources {
    exclude("*")
}

tasks.jar {
    dependsOn(tasks.compileJava)
    exclude("build/kotlin/main/**")
    from(tasks.compileJava.get().destinationDirectory)
}

tasks.register("analyzeJustinInLoop") {
    dependsOn(tasks.jar)
    dependsOn(tasks["generateJpfConfig"])
    doLast {
        tasks.jar.get().destinationDirectory.asFile.get().listFiles()?.forEach {
            javaexec {
                workingDir = file("JustinOutput")
                workingDir.mkdir()
                classpath = files("$dependencyDirectory/JustinStr.jar")
                logger.info(it.absolutePath)

                args(
                    "C:/Program Files/Eclipse Adoptium/jdk-8.0.345.1-hotspot/jre",
                    it.absolutePath
                )
            }
        }
    }
}

tasks.register("analyzeJpfInLoop") {
    dependsOn(tasks.compileJava)
    doLast {
        tasks.compileJava.get().destinationDirectory.asFile.get().listFiles()?.forEach {
            if (it.name.contains(".jpf")) {
                javaexec {
                    classpath = files("$dependencyDirectory/jpf-core/build/RunJPF.jar")
                    jvmArgs = listOf(
                        "-Xmx1024m",
                        "-ea"
                    )

                    workingDir = File(dependencyDirectory);

                    args(
                        it.absolutePath
                    )
                }
            }
        }
    }
}
