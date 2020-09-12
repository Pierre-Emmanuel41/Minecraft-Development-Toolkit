package fr.pederobien.minecraftdevelopmenttoolkit.impl;

import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericSimpleMapEdition;

public abstract class AbstractGenericSimpleMapEdition<T> extends AbstractGenericCommonMapEdition<T, IGenericSimpleMapEdition<T>> implements IGenericSimpleMapEdition<T> {
	private IGenericSimpleMapEdition<T> parent;

	public AbstractGenericSimpleMapEdition(String label, T explanation) {
		super(label, explanation);
	}

	@Override
	public IGenericSimpleMapEdition<T> addEdition(IGenericSimpleMapEdition<T> elt) {
		internalAdd(elt);
		return this;
	}

	@Override
	public IGenericSimpleMapEdition<T> removeEdition(IGenericSimpleMapEdition<T> elt) {
		internalRemove(elt);
		return this;
	}

	@Override
	public IGenericSimpleMapEdition<T> setAvailable(boolean available) {
		internalSetAvailable(available);
		return this;
	}

	@Override
	public IGenericSimpleMapEdition<T> setModifiable(boolean modifiable) {
		internalSetModifiable(modifiable);
		return this;
	}

	@Override
	public IGenericSimpleMapEdition<T> getParent() {
		return parent == null ? this : parent.getParent();
	}

	@Override
	public void setParent(IGenericSimpleMapEdition<T> parent) {
		for (IGenericSimpleMapEdition<T> elt : getChildren().values())
			elt.setParent(parent);
	}
}
