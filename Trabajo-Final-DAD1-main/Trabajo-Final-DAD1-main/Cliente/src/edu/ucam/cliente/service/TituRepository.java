package edu.ucam.cliente.service;

import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.domain.Titulacion;

public class TituRepository extends BaseRepository<Titulacion> {

    public TituRepository(IComunicationServer comunication,
                          IChannelData channelData) {

        super(
            comunication,
            channelData,
            "ADDTIT",
            "REMOVETIT",
            "GETTIT",
            "LISTTIT",
            "COUNTTIT",
            "UPDATETIT"
        );
    }
}
