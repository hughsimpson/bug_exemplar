import java.io.{File, PrintWriter}
import scala.io.Source

val jsoniterScalaVersion = "2.35.3"
val tapirVersion = "1.11.28"

val deps = Seq(
  "com.softwaremill.sttp.tapir" %% "tapir-jsoniter-scala" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-pekko-http-server" % tapirVersion,
  "com.beachape" %% "enumeratum" % "1.7.6",
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterScalaVersion,
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterScalaVersion % "compile-internal"
)
val commonSettings: Seq[Setting[?]] = Seq(
  scalaVersion := "2.13.16",
  libraryDependencies ++= deps,
  openapiJsonSerdeLib := "jsoniter",
  openapiSwaggerFile := file("swagger.yaml"),
  openapiStreamingImplementation := "pekko",
  openapiGenerateEndpointTypes := true
)

lazy val root = project
  .in(file("."))
  .aggregate(
    common,
    modA,
    modB,
    modC,
    modD,
    modE,
    modF,
    modG
  )

lazy val common = project
  .in(file("common"))
  .settings(libraryDependencies ++= deps, scalaVersion := "2.13.16")

lazy val modA = project
  .in(file("modA"))
  .settings(openapiPackage := "sttp.tapir.generated.testsA")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modB = project
  .in(file("modB"))
  .settings(openapiPackage := "sttp.tapir.generated.testsB")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modC = project
  .in(file("modC"))
  .settings(openapiPackage := "sttp.tapir.generated.testsC")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modD = project
  .in(file("modD"))
  .settings(openapiPackage := "sttp.tapir.generated.testsD")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modE = project
  .in(file("modE"))
  .settings(openapiPackage := "sttp.tapir.generated.testsE")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modF = project
  .in(file("modF"))
  .settings(openapiPackage := "sttp.tapir.generated.testsF")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modG = project
  .in(file("modG"))
  .settings(openapiPackage := "sttp.tapir.generated.testsG")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)
