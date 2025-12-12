lazy val commonSettings = Seq(
  scalaVersion := "2.13.16",
  scalacOptions ++= Seq(
    "-unchecked", 
    "-deprecation", 
    "-g:source",
    "-g:vars",
    "-g:line",
    "-Yrangepos"
  ),
  libraryDependencies ++= Seq(
    "org.chipsalliance" % "chisel_2.13" % "6.7.0",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"
  ),
  updateOptions := updateOptions.value.withLatestSnapshots(false),
  addCompilerPlugin("org.chipsalliance" % "chisel-plugin" % "6.7.0" cross CrossVersion.full)
  //libraryDependencies += "edu.berkeley.cs" % "rocketchip"       %  "latest.integration"
)

lazy val example = (project in file("."))
  .settings(commonSettings)
