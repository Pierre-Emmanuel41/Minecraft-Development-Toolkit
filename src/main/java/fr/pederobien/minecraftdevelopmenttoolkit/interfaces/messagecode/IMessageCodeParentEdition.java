package fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode;

import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IGenericParentEdition;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public interface IMessageCodeParentEdition<U, V extends IMessageCodeParentEdition<U, V, W>, W extends IMessageCodeMapEdition<U, V, W>>
		extends IGenericParentEdition<IMinecraftMessageCode, U, V, W> {

}
