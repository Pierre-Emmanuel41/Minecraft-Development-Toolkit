package fr.pederobien.minecraftdevelopmenttoolkit.impl.messagecode;

import fr.pederobien.minecraftdevelopmenttoolkit.impl.AbstractHelper;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeHelper;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeParentEdition;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public abstract class AbstractMessageCodeHelper<U, V extends IMessageCodeParentEdition<U, V, W>, W extends IMessageCodeMapEdition<U, V, W>>
		extends AbstractHelper<IMinecraftMessageCode, U, V, W> implements IMessageCodeHelper<U, V, W> {

	public AbstractMessageCodeHelper(IMinecraftMessageCode explanation) {
		super(explanation);
	}
}
