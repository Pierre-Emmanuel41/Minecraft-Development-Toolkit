# How to use this API

All classes in this project inherits <code>AbstractGenericEdition</code>. This class represents an edition which is a part of a command : A label and an explanation. The label correspond to the name of the edition. The explanation is generic in order to let the developer decides the type of explanation he wants to use.

# Generic command tree 

A tree is represented by two classes : <code>AbstractGenericParentEdition</code> and <code>AbstractGenericMapEdition</code>. The parent edition correspond to the root of the command tree and contains a reference to the object to modify whereas the map edition correspond to the node of the tree. The global idea of those classes is that they modify the properties of an object that's why they are more generic than the AbstractGenericEdition class.

# AbstractGenericParentEdition

This class needs four generic parameters :

  * [T] which represents the type of explanation.
  * [U] which represents the type of object the edition modify.
  * [V] which represents the type of underlying object that modify the property of the previous object.
  * [W] which represents the type of object that can be added to this root.
  
Moreover, the constructor of this class needs an <code>IHelper</code> whose implementation is the class <code>AbstractHelper</code>. Indeed, this helper provides a generic way to display in minecraft the explanation associated to the parent edition, to each children map edition or to a specific map edition.

# AbstractGenericMapEdition

In the same way as the AbstractGenericParentEdition, this class needs also four generic parameters :

  * [T] which represents the type of explanation.
  * [U] which represents the type of object the edition modify.
  * [V] which represents the type of underlying object that modify the property of the previous object.
  * [W] which represents the type of object that can be added to this node.
  
In order to use those classes, you need to write your own ParentEdition that inherits the AbstractGenericParentEdition and your own MapEdition that inherits the AbstractGenericMapEdition.

However, it also possible to use a command tree without object to modify. In that case, you don't need to use the two previous class but just the class <code>AbstractGenericSimpleMapEdition</code>. It is only dependent on the type of the explanation.

As you can see, those classes are very sophisticated and may be difficult to understand and to manipulate, that's why there is already a small implementation of those classes in package <code>fr.pederobien.minecraftdevelopmenttoolkit.impl.messagecode</code>. Indeed, this project depends on project minecraft-dictionary which propose a lot features for mutli-language applications.

# Message code command tree

The package provides four classes :

  * AbstractMessageCodeHelper
  * AbstractMessageCodeMapEdition
  * AbstractMessageCodeParentEdition
  * AbstractMessageCodeSimpleMapEdition
  
They are no real implementation because they are still abstract but they fix the type of the explanation : <code>IMinecraftMessageCode</code>. You still need to extend those classes in order to use them.

Here is an example of the implementation of those classes but keeping the explanation type and the type of object to modify generic.

# MyMapEdition, MyParentEdition, MyHelper

```java
public class MyParentEdition<T, U> extends AbstractGenericParentEdition<T, U, MyParentEdition<T, U>, MyMapEdition<T, U>> {
	private U elt;

	public MyParentEdition(String label, T explanation, IHelper<T, U, MyParentEdition<T, U>, MyMapEdition<T, U>> helper) {
		super(label, explanation, helper);
	}

	@Override
	public U get() {
		return elt;
	}

	@Override
	public void set(U element) {
		this.elt = element;
	}

	@Override
	public MyParentEdition<T, U> addEdition(MyMapEdition<T, U> elt) {
		elt.setParent(this);
		internalAdd(elt);
		return this;
	}

	@Override
	public MyParentEdition<T, U> removeEdition(MyMapEdition<T, U> elt) {
		elt.setParent(null);
		internalRemove(elt);
		return this;
	}

	@Override
	public MyParentEdition<T, U> setAvailable(boolean available) {
		internalSetAvailable(available);
		return this;
	}

	@Override
	public MyParentEdition<T, U> setModifiable(boolean modifiable) {
		internalSetModifiable(modifiable);
		return this;
	}
}
```

```java
public class MyHelper<T, U> extends AbstractHelper<T, U, MyParentEdition<T, U>, MyMapEdition<T, U>> {

	public MyHelper(T explanation) {
		super(explanation);
	}

	@Override
	protected void sendMessage(Player player, List<IGenericEdition<T>> editions) {
		StringJoiner joiner = new StringJoiner("\n");
		for (IGenericEdition<T> edition : editions)
			joiner.add(edition.getLabel() + " - " + edition.getExplanation());
		sendMessage(player, joiner.toString());
	}
}
```

```java
public class MyMapEdition<T, U> extends AbstractGenericMapEdition<T, U, MyParentEdition<T, U>, MyMapEdition<T, U>> {

	public MyMapEdition(String label, T explanation) {
		super(label, explanation);
	}

	@Override
	public MyMapEdition<T, U> addEdition(MyMapEdition<T, U> elt) {
		internalAdd(elt);
		return this;
	}

	@Override
	public MyMapEdition<T, U> removeEdition(MyMapEdition<T, U> elt) {
		internalRemove(elt);
		return this;
	}

	@Override
	public MyMapEdition<T, U> setAvailable(boolean available) {
		internalSetAvailable(available);
		return this;
	}

	@Override
	public MyMapEdition<T, U> setModifiable(boolean modifiable) {
		internalSetModifiable(modifiable);
		return this;
	}
}
```

Please see this [Example](https://github.com/Pierre-Emmanuel41/minecraft-development-toolkit/blob/master/GenericExample.md) in order to understand how to use the previous classes.