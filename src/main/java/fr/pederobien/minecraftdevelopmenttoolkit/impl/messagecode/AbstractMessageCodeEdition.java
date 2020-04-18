package fr.pederobien.minecraftdevelopmenttoolkit.impl.messagecode;

import fr.pederobien.minecraftdevelopmenttoolkit.impl.AbstractGenericEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeEdition;
import fr.pederobien.minecraftdictionary.interfaces.IMessageCode;

public abstract class AbstractMessageCodeEdition extends AbstractGenericEdition<IMessageCode> implements IMessageCodeEdition {

	public AbstractMessageCodeEdition(String label, IMessageCode explanation) {
		super(label, explanation);
	}
}
