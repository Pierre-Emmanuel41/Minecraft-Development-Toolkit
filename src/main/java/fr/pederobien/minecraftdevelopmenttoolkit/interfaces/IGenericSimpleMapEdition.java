package fr.pederobien.minecraftdevelopmenttoolkit.interfaces;

public interface IGenericSimpleMapEdition<T> extends IGenericCommonMapEdition<T, IGenericSimpleMapEdition<T>> {

	IGenericSimpleMapEdition<T> getParent();

	void setParent(IGenericSimpleMapEdition<T> parent);
}
