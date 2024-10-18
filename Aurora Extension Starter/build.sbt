import scala.sys.process._
lazy val installDependencies = Def.task[Unit] {
  val base = (ThisProject / baseDirectory).value
  val log = (ThisProject / streams).value.log

  // Define the path where npm is installed
  val npmPath = "C:/Program Files/nodejs" // On macOS/Linux (update as necessary)
  // For Windows, it might be something like: "C:/Program Files/nodejs"

  if (!(base / "node_modules").exists) {
    log.info("Installing npm dependencies...")

    // Create a new ProcessBuilder with custom environment
    val pb = new java.lang.ProcessBuilder("npm", "install")
      .directory(base)
      .redirectErrorStream(true)

    // Modify the environment to include npm in PATH
    val env = pb.environment()
    env.put("PATH", npmPath + ";" + env.get("PATH")) // On macOS/Linux
    // For Windows use ";" instead of ":"
    // env.put("PATH", npmPath + ";" + env.get("PATH"))

    // Execute the npm install command and log the output
    val result = pb ! log

    if (result != 0) {
      sys.error("Failed to install npm dependencies.")
    }
  } else {
    log.info("npm dependencies already installed.")
  }
}

lazy val open = taskKey[Unit]("open vscode")
def openVSCodeTask: Def.Initialize[Task[Unit]] = Def.task[Unit] {
  val base = (ThisProject / baseDirectory).value
  val log = (ThisProject / streams).value.log

  val path = base.getCanonicalPath
  val openProcess = Process(s"code --extensionDevelopmentPath=$path")
  log.info(s"Opening VSCode with extension development path: $path")
  
  val result = openProcess ! log
  if (result != 0) {
    sys.error("Failed to open VSCode.")
  }
}.dependsOn(installDependencies)

lazy val root = project
  .in(file("."))
  .settings(
    scalaVersion := "3.5.0",
    moduleName := "aurora-extension-starter",
    Compile / fastOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    Compile / fullOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    open := openVSCodeTask.dependsOn(Compile / fastOptJS).value,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "utest" % "0.8.2" % Test
    ),
    Compile / npmDependencies ++= Seq("@types/vscode" -> "1.84.1"),
    testFrameworks += new TestFramework("utest.runner.Framework")
  )
  .enablePlugins(
    ScalaJSPlugin,
    ScalablyTypedConverterExternalNpmPlugin,
    ScalablyTypedConverterPlugin
  )
