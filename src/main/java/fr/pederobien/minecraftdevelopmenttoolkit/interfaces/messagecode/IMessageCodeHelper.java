package fr.pederobien.minecraftdevelopmenttoolkit.interfaces.messagecode;

import fr.pederobien.minecraftdevelopmenttoolkit.interfaces.IHelper;
import fr.pederobien.minecraftdictionary.interfaces.IMessageCode;

public interface IMessageCodeHelper<U, V extends IMessageCodeParentEdition<U, V, W>, W extends IMessageCodeMapEdition<U, V, W>> extends IHelper<IMessageCode, U, V, W> {

}
