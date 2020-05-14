package fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode;

import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericMapEdition;
import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IManagedEdition;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public interface IMessageCodeMapEdition<U, V extends IManagedEdition<U>, W extends IMessageCodeMapEdition<U, V, W>>
		extends IGenericMapEdition<IMinecraftMessageCode, U, V, W> {

}
