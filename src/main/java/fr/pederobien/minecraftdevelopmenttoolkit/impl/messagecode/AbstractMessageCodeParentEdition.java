package fr.pederobien.minecraftdevelopmenttoolkit.impl.messagecode;

import fr.pederobien.minecraftdevelopmenttoolkit.impl.AbstractGenericParentEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeHelper;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeParentEdition;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public abstract class AbstractMessageCodeParentEdition<U, V extends IMessageCodeParentEdition<U, V, W>, W extends IMessageCodeMapEdition<U, V, W>>
		extends AbstractGenericParentEdition<IMinecraftMessageCode, U, V, W> implements IMessageCodeParentEdition<U, V, W> {

	public AbstractMessageCodeParentEdition(String label, IMinecraftMessageCode explanation, IMessageCodeHelper<U, V, W> helper) {
		super(label, explanation, helper);
	}
}
