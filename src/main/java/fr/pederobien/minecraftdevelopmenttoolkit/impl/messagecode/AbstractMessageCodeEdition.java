package fr.pederobien.minecraftdevelopmenttoolkit.impl.messagecode;

import fr.pederobien.minecraftdevelopmenttoolkit.impl.AbstractGenericEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeEdition;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public abstract class AbstractMessageCodeEdition extends AbstractGenericEdition<IMinecraftMessageCode> implements IMessageCodeEdition {

	public AbstractMessageCodeEdition(String label, IMinecraftMessageCode explanation) {
		super(label, explanation);
	}
}
