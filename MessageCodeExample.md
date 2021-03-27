# Message code example

For those of you who don't know how the project minecraft-dictionary works I highly recommend to read this [tutorial](https://github.com/Pierre-Emmanuel41/minecraft-dictionary/blob/master/Tutorial.md). I will not explain here how to use the features provided by the project but I will directly use them.

# Message code dictionaries

In this part we will create the dictionary files that contains the association of a code and its translation. We will use two different languages : English and French.

* English dictionary file :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<dictionary>
	<name>English</name>
	<version>1.0</version>
	<locales>
		<locale>en</locale>
		<locale>en-EN</locale>
		<locale>en-GB</locale>
		<locale>en-UK</locale>
		<locale>en-US</locale>
		<locale>en-CA</locale>
	</locales>
	<messages>
		<message>
			<code>CAR_NAME_EXPLANATION</code>
			<value>To change the name of the car</value>
		</message>
		<message>
			<code>CAR_NAME_TAB_COMPLETE</code>
			<value>&lt;name&gt;</value>
		</message>
		<message>
			<code>CAR_NAME_NAME_IS_MISSING</code>
			<value>Cannot change the name of the car, the name is missing</value>
		</message>
		<message>
			<code>CAR_NAME_NAME_NAME_CHANGED</code>
			<value>The name of the car is now : %s</value>
		</message>
		<message>
			<code>CAR_BRAND_EXPLANATION</code>
			<value>To change the brand of the car</value>
		</message>
		<message>
			<code>CAR_BRAND_TAB_COMPLETE</code>
			<value>&lt;brand&gt;</value>
		</message>
		<message>
			<code>CAR_BRAND_BRAND_IS_MISSING</code>
			<value>Cannot change the brand of the car, the brand is missing</value>
		</message>
		<message>
			<code>CAR_BRAND_BRAND_CHANGED</code>
			<value>The brand of the car is now : %s</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_EXPLANATION</code>
			<value>To change the horse power of the car</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_TAB_COMPLETE</code>
			<value>&lt;horsePower&gt;</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_HORSE_POWER_IS_MISSING</code>
			<value>Cannot change the horse power of the car, the horse power is missing</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_BAD_FORMAT</code>
			<value>Cannot change the horse power of the car, the horse power must be an integer</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_HORSE_POWER_CHANGED</code>
			<value>The horse power of the car is now : %s</value>
		</message>
		<message>
			<code>CAR_MODIFY_EXPLANATION</code>
			<value>To modify the properties of a car</value>
		</message>
		<message>
			<code>CAR_DISPLAY_EXPLANATION</code>
			<value>To display the properties of the car</value>
		</message>
		<message>
			<code>CAR_EXPLANATION</code>
			<value>To manipulate a car</value>
		</message>
		<message>
			<code>CAR_HELP_EXPLANATION</code>
			<value>To display the explanation of each car commands</value>
		</message>
	</messages>
</dictionary>
```

* French dictionary file :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<dictionary>
	<name>English</name>
	<version>1.0</version>
	<locales>
		<locales>
			<locale>fr</locale>
			<locale>fr-FR</locale>
			<locale>fr-CA</locale>
		</locales>
	</locales>
	<messages>
		<message>
			<code>CAR_NAME_EXPLANATION</code>
			<value>Pour changer le nom de la voiture</value>
		</message>
		<message>
			<code>CAR_NAME_TAB_COMPLETE</code>
			<value>&lt;nom&gt;</value>
		</message>
		<message>
			<code>CAR_NAME_NAME_IS_MISSING</code>
			<value>Impossible de changer le nom de la voiture, il manque le nom</value>
		</message>
		<message>
			<code>CAR_NAME_NAME_NAME_CHANGED</code>
			<value>Le nom de la voiture est maintenant : %s</value>
		</message>
		<message>
			<code>CAR_BRAND_EXPLANATION</code>
			<value>Pour change la marque de la voiture</value>
		</message>
		<message>
			<code>CAR_BRAND_TAB_COMPLETE</code>
			<value>&lt;marque&gt;</value>
		</message>
		<message>
			<code>CAR_BRAND_BRAND_IS_MISSING</code>
			<value>Impossible de changer la marque de la voiture, il manque la marque</value>
		</message>
		<message>
			<code>CAR_BRAND_BRAND_CHANGED</code>
			<value>La marque de la voiture est maintenant : %s</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_EXPLANATION</code>
			<value>Pour changer la puissance de la voiture</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_TAB_COMPLETE</code>
			<value>&lt;puissance&gt;</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_HORSE_POWER_IS_MISSING</code>
			<value>Impossible de changer la puissance de la voiture, il manque la puissance</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_BAD_FORMAT</code>
			<value>Impossible de changer la puissance de la voiture, la puissance doit être un entier</value>
		</message>
		<message>
			<code>CAR_HORSE_POWER_HORSE_POWER_CHANGED</code>
			<value>La puissance de la voiture est maintenant : %s</value>
		</message>
		<message>
			<code>CAR_MODIFY_EXPLANATION</code>
			<value>Pour modifier les proprietes d'une voiture</value>
		</message>
		<message>
			<code>CAR_DISPLAY_EXPLANATION</code>
			<value>Pour afficher les proprietes d'une voiture</value>
		</message>
		<message>
			<code>CAR_EXPLANATION</code>
			<value>Pour manipuler une voiture</value>
		</message>
		<message>
			<code>CAR_HELP_EXPLANATION</code>
			<value>Pour afficher l'explication de chaque command</value>
		</message>
	</messages>
</dictionary>
```

Those files are stored in the folder "src/main/resources". This [tutorial](https://github.com/Pierre-Emmanuel41/minecraft-dictionary/blob/master/Tutorial.md) explains how to do read them and to parse them.

Let's now create an enumeration of all message code we need to use :

```java
public enum EMessageCode implements IMinecraftMessageCode {
	CAR_NAME_EXPLANATION, CAR_NAME_TAB_COMPLETE, CAR_NAME_NAME_IS_MISSING, CAR_NAME_NAME_NAME_CHANGED,
	CAR_BRAND_EXPLANATION, CAR_BRAND_TAB_COMPLETE, CAR_BRAND_BRAND_IS_MISSING, CAR_BRAND_BRAND_CHANGED,
	CAR_HORSE_POWER_EXPLANATION, CAR_HORSE_POWER_TAB_COMPLETE, CAR_HORSE_POWER_HORSE_POWER_IS_MISSING, CAR_HORSE_POWER_BAD_FORMAT, CAR_HORSE_POWER_HORSE_POWER_CHANGED,
	CAR_MODIFY_EXPLANATION, CAR_DISPLAY_EXPLANATION, CAR_EXPLANATION, CAR_HELP_EXPLANATION;
	
	private Permission permission;
	
	private EMessageCode() {
		this(Permission.OPERATORS);
	}
	
	private EMessageCode(Permission permission) {
		this.permission = permission;
	}
	
	@Override
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	@Override
	public Permission getPermission() {
		return permission;
	}

	@Override
	public String value() {
		return toString();
	}
}
```

# Message code ParentEdition, MapEdition and Helper

Just like the previous example, we need to extends the following generic classes : <code>AbstractMessageCodeParentEdition</code>, <code>AbstractMessageCodeMapEdition</code> and <code>AbstractMessageCodeHelper</code> to create <code>MyMessageCodeParentEdition</code>, <code>MyMessageCodeMapEdition</code> and <code>MyMessageCodeHelper</code>.

* MyMessageCodeParentEdition : 

```java
public class MyMessageCodeParentEdition extends AbstractMessageCodeParentEdition<Car, MyMessageCodeParentEdition, MyMessageCodeMapEdition> {
	private Car elt;

	public MyMessageCodeParentEdition(String label, IMinecraftMessageCode explanation,
			IMessageCodeHelper<Car, MyMessageCodeParentEdition, MyMessageCodeMapEdition> helper) {
		super(label, explanation, helper);
	}

	@Override
	public Car get() {
		return elt;
	}

	@Override
	public void set(Car element) {
		this.elt = element;
	}

	@Override
	public MyMessageCodeParentEdition addEdition(MyMessageCodeMapEdition elt) {
		elt.setParent(this);
		internalAdd(elt);
		return this;
	}

	@Override
	public MyMessageCodeParentEdition removeEdition(MyMessageCodeMapEdition elt) {
		elt.setParent(null);
		internalRemove(elt);
		return this;
	}

	@Override
	public MyMessageCodeParentEdition setAvailable(boolean available) {
		internalSetAvailable(available);
		return this;
	}

	@Override
	public MyMessageCodeParentEdition setModifiable(boolean modifiable) {
		internalSetModifiable(modifiable);
		return this;
	}
}
```

* MyMessageCodeMapEdition :

```java
public class MyMessageCodeMapEdition extends AbstractMessageCodeMapEdition<Car, MyMessageCodeParentEdition, MyMessageCodeMapEdition> {

	public MyMessageCodeMapEdition(String label, IMinecraftMessageCode explanation) {
		super(label, explanation);
	}

	@Override
	public MyMessageCodeMapEdition addEdition(MyMessageCodeMapEdition elt) {
		internalAdd(elt);
		return this;
	}

	@Override
	public MyMessageCodeMapEdition removeEdition(MyMessageCodeMapEdition elt) {
		internalRemove(elt);
		return this;
	}

	@Override
	public MyMessageCodeMapEdition setAvailable(boolean available) {
		internalSetAvailable(available);
		return this;
	}

	@Override
	public MyMessageCodeMapEdition setModifiable(boolean modifiable) {
		internalSetModifiable(modifiable);
		return this;
	}

	@Override
	public IMinecraftNotificationCenter getNotificationCenter() {
		return NotificationCenter.getInstance();
	}
}
```

* MyMessageCodeHelper :

```java
public class MyMessageCodeHelper extends AbstractMessageCodeHelper<Car, MyMessageCodeParentEdition, MyMessageCodeMapEdition> {

	public MyMessageCodeHelper(IMinecraftMessageCode explanation) {
		super(explanation);
	}

	@Override
	protected void sendMessage(Player player, List<IGenericEdition<IMinecraftMessageCode>> editions) {
		StringJoiner joiner = new StringJoiner("\n");
		for (IGenericEdition<IMinecraftMessageCode> edition : editions)
			joiner.add(translate(player, edition));
		sendMessage(player, joiner.toString());
	}

	private String translate(Player player, IGenericEdition<IMinecraftMessageCode> edition) {
		String explanation = NotificationCenter.getInstance().getDictionaryContext().getMessage(new MinecraftMessageEvent(player, edition.getExplanation()));
		return ChatColor.DARK_RED + edition.getLabel() + " - " + ChatColor.DARK_AQUA + explanation + "\n";
	}
}
```

Based on those classes, we will create the commands in order to modify a car.

# CarNameCommand

```java
public class CarNameMapEdition extends MyMessageCodeMapEdition {

	public CarNameMapEdition() {
		super("name", EMessageCode.CAR_NAME_EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sendSynchro(sender, EMessageCode.CAR_NAME_NAME_IS_MISSING);
			return false;
		}

		get().setName(args[0]);
		sendSynchro(sender, EMessageCode.CAR_NAME_NAME_NAME_CHANGED, get().getName());
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return asList(getMessage(sender, EMessageCode.CAR_NAME_TAB_COMPLETE));
		default:
			return emptyList();
		}
	}
}
```

# CarBrandCommand

```java
public class CarBrandMapEdition extends MyMessageCodeMapEdition {

	public CarBrandMapEdition() {
		super("brand", EMessageCode.CAR_BRAND_EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sendSynchro(sender, EMessageCode.CAR_BRAND_BRAND_IS_MISSING);
			return false;
		}

		get().setBrand(args[0]);
		sendSynchro(sender, EMessageCode.CAR_BRAND_BRAND_CHANGED);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return asList(getMessage(sender, EMessageCode.CAR_BRAND_TAB_COMPLETE));
		default:
			return emptyList();
		}
	}
}
```

# CarHorsePowerCommand

```java
public class CarHorsePowerMapEdition extends MyMessageCodeMapEdition {

	public CarHorsePowerMapEdition() {
		super("horsePower", EMessageCode.CAR_HORSE_POWER_EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sendSynchro(sender, EMessageCode.CAR_HORSE_POWER_HORSE_POWER_IS_MISSING);
			return false;
		}

		int horsePower;
		try {
			horsePower = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			sendSynchro(sender, EMessageCode.CAR_HORSE_POWER_BAD_FORMAT);
			return false;
		}

		get().setHorsePower(horsePower);
		sendSynchro(sender, EMessageCode.CAR_HORSE_POWER_HORSE_POWER_CHANGED, get().getHorsePower());
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

# CarModifyCommand

```java
public class ModifyCarMapEdition extends MyMessageCodeMapEdition {

	public ModifyCarMapEdition() {
		super("modify", EMessageCode.CAR_MODIFY_EXPLANATION);
		addEdition(new CarNameMapEdition());
		addEdition(new CarBrandMapEdition());
		addEdition(new CarHorsePowerMapEdition());
	}
}
```

# CarDisplayCommand

```java
public class DisplayCarMapEdition extends MyMessageCodeMapEdition {

	public DisplayCarMapEdition() {
		super("display", EMessageCode.CAR_DISPLAY_EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(get().toString());
		return true;
	}
}
```

# CarParentEdition

```java
public class CarParentEdition extends MyMessageCodeParentEdition {

	public CarParentEdition() {
		super("car", EMessageCode.CAR_EXPLANATION, new MyMessageCodeHelper(EMessageCode.CAR_HELP_EXPLANATION));
		addEdition(new ModifyCarMapEdition());
		addEdition(new DisplayCarMapEdition());
	}
}
```

In order to register this command to use it for minecraft, have a look at the end of this [Example](https://github.com/Pierre-Emmanuel41/minecraft-development-toolkit/blob/master/GenericExample.md).