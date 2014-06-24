
name := "sbt-bukkit"

organization := "com.jcranky"

version := "0.3"

scalaVersion := "2.10.4"

sbtPlugin := true

resolvers += "bukkit-repo" at "http://repo.bukkit.org/content/groups/public"

libraryDependencies ++= Seq(
  "org.bukkit" % "bukkit" % "1.7.9-R0.2"
)

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")
