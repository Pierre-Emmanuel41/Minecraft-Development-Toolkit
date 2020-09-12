package fr.pederobien.minecraftdevelopmenttoolkit.impl.messagecode;

import fr.pederobien.minecraftdevelopmenttoolkit.impl.AbstractGenericSimpleMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode.IMessageCodeSimpleMapEdition;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public abstract class AbstractMessageCodeSimpleMapEdition extends AbstractGenericSimpleMapEdition<IMinecraftMessageCode> implements IMessageCodeSimpleMapEdition {

	public AbstractMessageCodeSimpleMapEdition(String label, IMinecraftMessageCode explanation) {
		super(label, explanation);
	}
}
