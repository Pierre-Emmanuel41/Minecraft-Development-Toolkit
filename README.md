# Minecraft-development-toolkit

This project propose for developers a generic view of a minecraft command tree. Indeed, there is the main command using "/commandName" and then several secondary commands can be added. But those secondary command can also have secondary commands and so on. Moreover, there is a built-in mechanism of helper when the user does not know what does a command do.

# Register as maven dependency

It is easy to register this project as dependency for your own project. To do so, you need to download this project.

The easiest way to do so is to use the following git command line :

```git
git clone https://github.com/Pierre-Emmanuel41/minecraft-development-toolkit.git --recursive
```
Indeed, this project depends on differents projects : [minecraft-dictionary](https://github.com/Pierre-Emmanuel41/minecraft-dictionary) and [minecraft-scoreboards](https://github.com/Pierre-Emmanuel41/minecraft-scoreboards) need to be downloaded on your machine to avoid compilation errors.

Then, you only need to run the file deploy.bat. This file will create the maven projects in your .m2 folder. Finally, in the pom.xml of your project, you have to add the following lines :

```xml
<dependency>
	<groupId>fr.pederobien</groupId>
	<artifactId>minecraft-development-toolkit</artifactId>
	<version>2.0_MC_1.13.2-SNAPSHOT</version>
</dependency>
```
You can now use features provided by this API in you project.

To see how you can use thoses features, please have a look to [This tutorial](https://github.com/Pierre-Emmanuel41/minecraft-development-toolkit/blob/master/Tutorial.md)
