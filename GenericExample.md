# Generic example

In this part, we will use the most generic part of the API. 

So, first, we need a type for the explanation. Let's keep it simple and let's say that we wants the explanation is a simple <code>String</code>.
Then we need an object to modify by our commands. Let's say that we wants to modify a <code>car</code>. A car is represented by its name, its brand and its horse power.

# Car

Here is a class that represents a car :

```java
public class Car {
	private String name;
	private String brand;
	private int horsePower;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}
	
	@Override
	public String toString() {
		return "Car={" + name + "," + brand + "," + horsePower + "}";
	}
}
```

Then we use our previous classes <code>MyParentEdition</code>, <code>MyHelper</code> and <code>MyMapEdition</code> by specifying the type of explanation (<code>String</code> here) and the type of object to modify (<code>Car</code> here).

First we need commands that change the name, the brand and the horse power of a car :

# CarNameCommand

```java
public class CarNameMapEdition extends MyMapEdition<String, Car> {

	public CarNameMapEdition() {
		super("name", "To change the name of a car");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return false;

		get().setName(args[0]);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return asList("<name>");
		default:
			return emptyList();
		}
	}
}
```

# CarBrandCommand

```java
public class CarBrandMapEdition extends MyMapEdition<String, Car> {

	public CarBrandMapEdition() {
		super("brand", "To change the brand of the car");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return false;
		get().setBrand(args[0]);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return asList("<brand>");
		default:
			return emptyList();
		}
	}
}
```

# CarHorsePowerCommand

```java
public class CarHorsePowerMapEdition extends MyMapEdition<String, Car> {

	public CarHorsePowerMapEdition() {
		super("horsePower", "To change the horse power of the car");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return false;

		int horsePower;
		try {
			horsePower = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return false;
		}

		get().setHorsePower(horsePower);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return asList("<horsePower>");
		default:
			return emptyList();
		}
	}
}
```

And then a parent that contains those commands :

```java
public class CarParentEdition extends MyParentEdition<String, Car> {

	public CarParentEdition() {
		super("car", "To modify the property of a car", new MyHelper<String, Car>("To display the explanation of each car commands"));
		addEdition(new CarNameMapEdition());
		addEdition(new CarBrandMapEdition());
		addEdition(new CarHorsePowerMapEdition());
	}
}
```

Our command is now complete, but how to use it in out minecraft plugin ? Fist you have to register the command in the plugin.yml file and then register the command "car" as a command executor :

plugin.yml :

```txt
main: <FullPackagePath>.MyPlugin
name: myPlugin
version: <PluginVersion>
api-version: <MinecraftAPIVersion>

description:
commands:
  car:
    description: This command is used to modify a game.
```

And then in the main class of your plugin :

```java
public class MyPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("car").setExecutor(new CarParentEdition());
	}
}
```

# How to had tertiary commands

Here is the architecture of our command for a car :

car<br>
&emsp;name<br>
&emsp;brand<br>
&emsp;horsePower<br>

Now we wants to add a command that display the caracteristics of the car in minecraft, but this command is very different from the previous one. That's why, we need to rearrange our commands. One possibility is displayed juste bellow :

car<br>
&emsp;modify<br>
&emsp;&emsp;name<br>
&emsp;&emsp;brand<br>
&emsp;&emsp;horsePower<br>
&emsp;display<br>

So we added a intermediary command <code>modify</code> that gather our previous commands and added the command display. Let's see what look like our code yet.
There is no modification of our classes <code>CarNameMapEdition</code>, <code>CarBrandMapEdition</code> and <code>CarHorsePowerMapEdition</code> but we added two new commands that represents the command modify and the command display :

# ModifyCarCommand

```java
public class ModifyCarMapEdition extends MyMapEdition<String, Car> {

	public ModifyCarMapEdition() {
		super("modify", "To modify the properties of a car");
		addEdition(new CarNameMapEdition());
		addEdition(new CarBrandMapEdition());
		addEdition(new CarHorsePowerMapEdition());
	}
}
```

# DisplayCarCommand

```java
public class DisplayCarMapEdition extends MyMapEdition<String, Car> {

	public DisplayCarMapEdition() {
		super("display", "To display the properties of the car");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(get().toString());
		return true;
	}
}
```

Now, we only need to update the <code>CarParentEdition</code> to add our two new commands :

```java
public class CarParentEdition extends MyParentEdition<String, Car> {

	public CarParentEdition() {
		super("car", "To manipulate a car", new MyHelper<String, Car>("To display the explanation of each car commands"));
		addEdition(new ModifyCarMapEdition());
		addEdition(new DisplayCarMapEdition());
	}
}
```

This example is a complete example to help developers to understand how this generic API works. Until now, we have seen how to use the most generic part of the API but we don't have seen yet the features provided by the package fr.pederobien.minecraftdevelopmenttoolkit.impl.messagecode. Indeed, this package fix the type of explanation as <code>IMinecraftMessageCode</code>.

In this [Example](https://github.com/Pierre-Emmanuel41/minecraft-development-toolkit/blob/master/Example.md) we will see how to use this type of explanation.