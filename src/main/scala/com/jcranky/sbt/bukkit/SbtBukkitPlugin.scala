package com.jcranky.sbt.bukkit

import org.bukkit.configuration.file.YamlConfiguration
import sbt._
import Keys._
import sbtassembly.Plugin._
import sbtassembly.Plugin.AssemblyKeys._

object SbtBukkitPlugin extends Plugin {
  val bukkitFolder = settingKey[File]("folder to use a the bukkit base")
  val startBukkit = taskKey[Unit]("start the bukkit server")
  val downloadBukkit = taskKey[File]("download the bukkit server jar")
  val stopBukkit = taskKey[Unit]("stop the bukkit server")

  val createPluginYml = taskKey[File]("create the plugin.yml file if it doesn't exist")
  val deployBukkitPlugin = taskKey[Unit]("deploy the bukkit plugin to the local bukkit server")

  private var bukkitCmd: Option[Process] = None

  lazy val baseBukkitSettings: Seq[sbt.Def.Setting[_]] = Seq(
    bukkitFolder := new File(".bukkit"),
    downloadBukkit := {
      val log = streams.value.log

      IO.createDirectory(bukkitFolder.value)

      val bukkitJar = new File(bukkitFolder.value, "craftbukkit-beta.jar")
      if (!bukkitJar.exists) {
        log.info("Downloading local bukkit server jar")
        IO.download(new URL("http://dl.bukkit.org/latest-beta/craftbukkit-beta.jar"), bukkitJar)
      }

      bukkitJar
    },
    startBukkit := {
      val log = streams.value.log

      val bukkitJar = downloadBukkit.value

      if (bukkitCmd.isDefined) log.info("bukkit already running")
      else {
        bukkitCmd = Some(Fork.java.fork(
          ForkOptions(workingDirectory = Some(bukkitFolder.value)),
          Seq("-classpath", bukkitJar.getAbsolutePath, "org.bukkit.craftbukkit.Main", "--noconsole")))
      }
    },
    stopBukkit := {
      if (bukkitCmd.isDefined) {
        bukkitCmd.get.destroy
        bukkitCmd = None
      }
    },
    createPluginYml := {
      val resourceDir = (resourceDirectory in Compile).value
      val pluginYml = new File(resourceDir, "plugin.yml")

      if (!pluginYml.exists) {
        val ymlConfig = new YamlConfiguration()
        ymlConfig.set("name", name.value)
        ymlConfig.set("version", version.value)
        ymlConfig.set("author", organization.value)
        ymlConfig.save(pluginYml)
      }

      pluginYml
    },
    deployBukkitPlugin := {
      val jar = assembly.value
      IO.copyFile(jar, new File(bukkitFolder.value, "plugins/" + jar.getName), true)
    })

  override lazy val projectSettings = baseBukkitSettings ++ assemblySettings
}
