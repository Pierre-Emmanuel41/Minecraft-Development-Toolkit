package fr.pederobien.minecraftdevelopmenttoolkit.impl.messagecode;

import fr.pederobien.minecraftdevelopmenttoolkit.impl.AbstractGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeMapEdition;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public abstract class AbstractMessageCodeMapEdition<U, V extends IManagedEdition<U>, W extends IMessageCodeMapEdition<U, V, W>>
		extends AbstractGenericMapEdition<IMinecraftMessageCode, U, V, W> implements IMessageCodeMapEdition<U, V, W> {

	public AbstractMessageCodeMapEdition(String label, IMinecraftMessageCode explanation) {
		super(label, explanation);
	}
}
