import java.io.{File, PrintWriter}
import scala.io.Source

val jsoniterScalaVersion = "2.36.0"
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
  openapiGenerateEndpointTypes := true,
  (Compile / sourceGenerators) := {
    (Compile / sourceGenerators).value.map { _.map { mungeTapirOutput2 } }
  }
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
    modG,
    modH,
    modI,
    modJ,
    modK,
    modL,
    modM,
    modN,
    modO,
    modP,
    modQ,
    modR,
    modS,
    modT,
    modU,
    modV,
    modW,
    modX,
    modY,
    modZ
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


def mungeTapirOutput3(x: Seq[File]): Seq[File] = {
  x.map { f =>
    if (f.getParentFile.getName == "sbt-openapi-codegen") {
      val f1Name = f.getPath
      val f1 = new File(f1Name)
      val f2 = new File(s"$f.tmp")
      val w = new PrintWriter(f2)
      scala.util.Using(Source.fromFile(f1Name)) { f =>
        val allLines = f.getLines().toSeq
        allLines
          .map { line =>
            line
              .replace("implicit lazy val", "implicit def")
              .replace(
                "make(",
                "make2("
              )
          }
          .foreach(w.println)
      }
      w.close()
      f2.renameTo(f1)
      f1
    } else f
  }
}
def mungeTapirOutput2(x: Seq[File]): Seq[File] = {
  x.map { f =>
    if (f.getParentFile.getName == "sbt-openapi-codegen") {
      val f1Name = f.getPath
      val f1 = new File(f1Name)
      val f2 = new File(s"$f.tmp")
      val w = new PrintWriter(f2)
      scala.util.Using(Source.fromFile(f1Name)) { f =>
        val allLines = f.getLines().toSeq
        allLines
          .map { line =>
            line
              .replace("implicit lazy val", "implicit val")
              .replace(
                "com.github.plokhotnyuk.jsoniter_scala.macros.CodecMakerConfig.withAllowRecursiveTypes(true).withTransientEmpty(false).withTransientDefault(false).withRequireCollectionFields(true)",
                "com.github.plokhotnyuk.jsoniter_scala.macros.CodecMakerConfig.openapiConf"
              )
              .replace(
                "common.Util.conf.withDiscriminatorFieldName(scala.None).withDiscriminatorFieldName(scala.None)",
                "com.github.plokhotnyuk.jsoniter_scala.macros.CodecMakerConfig.openapEnumConf"
              )
              .replace(
                "make(com.github.plokhotnyuk.jsoniter_scala.macros.CodecMakerConfig.openapiConf)",
                "makeOpenapiLike"
              )
              .replace(
                "make(com.github.plokhotnyuk.jsoniter_scala.macros.CodecMakerConfig.openapEnumConf)",
                "makeOpenapiLikeWithoutDiscriminator"
              ).replace(
                "make(com.github.plokhotnyuk.jsoniter_scala.macros.CodecMakerConfig.openapiConf.withDiscriminatorFieldName(scala.None).withDiscriminatorFieldName(scala.None))",
                "makeOpenapiLikeWithoutDiscriminator"
              ).replaceAll(
                """make\(com\.github\.plokhotnyuk\.jsoniter_scala\.macros\.CodecMakerConfig\.openapiConf\.withRequireDiscriminatorFirst\(false\)\.withDiscriminatorFieldName\(Some\(\"(\w+)\"\)\)\.withAdtLeafClassNameMapper\(x => com\.github\.plokhotnyuk\.jsoniter_scala\.macros\.JsonCodecMaker\.simpleClassName\(x\) match \{""",
                """makeOpenapiLike("$1", {"""
              ).replaceAll(
                """make\(com\.github\.plokhotnyuk\.jsoniter_scala\.macros\.CodecMakerConfig\.openapiConf\.withRequireDiscriminatorFirst\(false\)\.withDiscriminatorFieldName\(Some\(\"(\w+)\"\)\)\)""",
                """makeOpenapiLike("$1")"""
              )
              .replaceAll("(^\\s+case[^\n]+)\\}\\)\\)", "$1})")
//              .replace("implicit lazy val", "implicit def")
//              .replace(
//                "make(",
//                "make2("
//              )
          }
          .foreach(w.println)
      }
      w.close()
      f2.renameTo(f1)
      f1
    } else f
  }
}

lazy val modH = project
  .in(file("modH"))
  .settings(openapiPackage := "sttp.tapir.generated.testsH")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modI = project
  .in(file("modI"))
  .settings(openapiPackage := "sttp.tapir.generated.testsI")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modJ = project
  .in(file("modJ"))
  .settings(openapiPackage := "sttp.tapir.generated.testsJ")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modK = project
  .in(file("modK"))
  .settings(openapiPackage := "sttp.tapir.generated.testsK")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modL = project
  .in(file("modL"))
  .settings(openapiPackage := "sttp.tapir.generated.testsL")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modM = project
  .in(file("modM"))
  .settings(openapiPackage := "sttp.tapir.generated.testsM")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modN = project
  .in(file("modN"))
  .settings(openapiPackage := "sttp.tapir.generated.testsN")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modO = project
  .in(file("modO"))
  .settings(openapiPackage := "sttp.tapir.generated.testsO")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modP = project
  .in(file("modP"))
  .settings(openapiPackage := "sttp.tapir.generated.testsP")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modQ = project
  .in(file("modQ"))
  .settings(openapiPackage := "sttp.tapir.generated.testsQ")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modR = project
  .in(file("modR"))
  .settings(openapiPackage := "sttp.tapir.generated.testsR")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modS = project
  .in(file("modS"))
  .settings(openapiPackage := "sttp.tapir.generated.testsS")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modT = project
  .in(file("modT"))
  .settings(openapiPackage := "sttp.tapir.generated.testsT")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modU = project
  .in(file("modU"))
  .settings(openapiPackage := "sttp.tapir.generated.testsU")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modV = project
  .in(file("modV"))
  .settings(openapiPackage := "sttp.tapir.generated.testsV")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modW = project
  .in(file("modW"))
  .settings(openapiPackage := "sttp.tapir.generated.testsW")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modX = project
  .in(file("modX"))
  .settings(openapiPackage := "sttp.tapir.generated.testsX")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modY = project
  .in(file("modY"))
  .settings(openapiPackage := "sttp.tapir.generated.testsY")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)

lazy val modZ = project
  .in(file("modZ"))
  .settings(openapiPackage := "sttp.tapir.generated.testsZ")
  .settings(commonSettings: _*)
  .enablePlugins(OpenapiCodegenPlugin)
  .dependsOn(common)
