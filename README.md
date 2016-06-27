sbt-bukkit
==========

Sbt plugin to help with the creation of [bukkit](bukkit.org) plugins.

Current supported version is 1.7.9-R0.2 (latest beta at this point in time), but we will be more flexible regarding that in the future.

## Instalation

To use _sbt-bukkit_, simply add it to the plugins.sbt of your project:

(this will be valid only after this plugin is published, still pending!)

```
addSbtPlugin("com.jcranky" % "sbt-bukkit" % "0.1-SNAPSHOT")
```

And then add _bukkit_ itself to your project dependencies (in _build.sbt_ for example):

```
resolvers += "bukkit-repo" at "http://repo.bukkit.org/content/groups/public"

libraryDependencies ++= Seq(
  "org.bukkit" % "bukkit" % "1.7.9-R0.2"
)
```

Also, if you are versioning your project, it is probably a good ideia to add the _.bukkit_
(or the folder your are using if you changed the default) folder to the ignore list.
