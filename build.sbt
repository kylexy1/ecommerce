name := "E-commerce"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.apache.commons" % "commons-email" % "1.4"
)

play.Project.playJavaSettings

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.27"


libraryDependencies += "javax.mail" % "mail" % "1.4.7"

// https://mvnrepository.com/artifact/joda-time/joda-time
libraryDependencies += "joda-time" % "joda-time" % "2.9.9"
