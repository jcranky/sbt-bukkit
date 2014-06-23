
name := "sbt-bukkit"

organization := "com.jcranky"

version := "0.1"

scalaVersion := "2.10.4"

sbtPlugin := true

resolvers += "bukkit-repo" at "http://repo.bukkit.org/content/groups/public"

libraryDependencies += "org.bukkit" % "bukkit" % "1.7.9-R0.2"
