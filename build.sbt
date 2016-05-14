name := """de.idnow.example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "javax.el" % "javax.el-api" % "2.2.4",
  "org.glassfish.web" % "javax.el" % "2.2.4",
  "org.mockito" % "mockito-all" % "1.10.19" % "test",
  "org.hamcrest" % "hamcrest-all" % "1.3" % "test"
)
// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
