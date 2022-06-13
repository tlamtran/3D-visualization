name := "3D-visualization"

version := "0.1"

scalaVersion := "2.13.8"

//scalatest
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.2" % "test"

//swing
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"

//scalafx
libraryDependencies += "org.scalafx" %% "scalafx" % "17.0.1-R26"
lazy val osName = System.getProperty("os.name") match {
case n if n.startsWith("Linux") => "linux"
case n if n.startsWith("Mac") => "mac"
case n if n.startsWith("Windows") => "win"
case _ => throw new Exception("Unknown platform!")
}

lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m =>
"org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)
